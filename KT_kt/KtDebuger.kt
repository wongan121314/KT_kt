package com.KT_kt

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.os.Process as AndroidProcess
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --------------------------------------------------
// DEBUG TYPES
// --------------------------------------------------

enum class DebugType {
    INFO, WARNING, ERROR
}

data class DebugMessage(
    val type: DebugType,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

// --------------------------------------------------
// DEBUGGER
// --------------------------------------------------

class KtDebugger(
    private val activity: Activity
) {

    private val messages = mutableListOf<DebugMessage>()
    private val selectedTypes = mutableSetOf<DebugType>()
    private val lock = Any()

    private val handler = Handler(Looper.getMainLooper())

    @Volatile
    private var isStopped = false

    @Volatile
    private var logcatProcess: Process? = null

    private var readerThread: Thread? = null
    private var filterConfigured = false

    // Keep memory usage bounded during long debugging sessions.
    private val maxMessages = 1000

    // UI
    private var overlay: FrameLayout? = null
    private var bubble: TextView? = null
    private var panel: LinearLayout? = null
    private var logContainer: LinearLayout? = null
    private var scrollView: ScrollView? = null

    private var infoButton: TextView? = null
    private var warningButton: TextView? = null
    private var errorButton: TextView? = null

    // --------------------------------------------------
    // FILTERS
    // --------------------------------------------------

    fun info(): KtDebugger {
        select(DebugType.INFO)
        return this
    }

    fun warning(): KtDebugger {
        select(DebugType.WARNING)
        return this
    }

    fun error(): KtDebugger {
        select(DebugType.ERROR)
        return this
    }

    fun all(): KtDebugger {
        synchronized(lock) {
            selectedTypes.clear()
            selectedTypes.addAll(DebugType.entries)
            filterConfigured = true
        }
        refresh()
        return this
    }

    private fun select(type: DebugType) {
        synchronized(lock) {
            if (!filterConfigured) {
                selectedTypes.clear()
                filterConfigured = true
            }
            selectedTypes.add(type)
        }
        refresh()
    }

    // --------------------------------------------------
    // OPTIONAL LOGGING HELPERS
    // These write to Android's normal Logcat stream.
    // The background reader picks them up like other app logs.
    // --------------------------------------------------

    fun logInfo(message: String): KtDebugger {
        Log.i("KT_kt", message)
        return this
    }

    fun logWarning(message: String): KtDebugger {
        Log.w("KT_kt", message)
        return this
    }

    fun logError(message: String): KtDebugger {
        Log.e("KT_kt", message)
        return this
    }

    // --------------------------------------------------
    // MESSAGE STORAGE
    // --------------------------------------------------

    private fun addMessage(type: DebugType, message: String) {
        if (isStopped) return

        synchronized(lock) {
            messages.add(DebugMessage(type, message))

            if (messages.size > maxMessages) {
                messages.removeAt(0)
            }
        }

        handler.post { refresh() }
    }

    fun getMessages(): List<DebugMessage> {
        return synchronized(lock) {
            if (!filterConfigured) {
                messages.toList()
            } else {
                messages.filter { it.type in selectedTypes }
            }
        }
    }

    // --------------------------------------------------
    // LOGCAT READER
    // --------------------------------------------------

    private fun startLogcatReader() {
        if (isStopped || readerThread?.isAlive == true) return

        readerThread = Thread {
            var parsedAnyLine = false
            var process: Process? = null

            try {
                val pid = AndroidProcess.myPid()

                val startedProcess = Runtime.getRuntime().exec(
                    arrayOf(
                        "logcat",
                        "--pid=$pid",
                        "-v",
                        "threadtime"
                    )
                )
                process = startedProcess
                logcatProcess = startedProcess

                startedProcess.inputStream.bufferedReader().use { reader ->

                    while (!isStopped) {
                        val line = reader.readLine() ?: break

                        if (parseLogcatLine(line)) {
                            parsedAnyLine = true
                        } else if (
                            line.contains("permission denied", ignoreCase = true) ||
                            line.contains("not allowed", ignoreCase = true) ||
                            line.contains("unknown option", ignoreCase = true)
                        ) {
                            addMessage(
                                DebugType.WARNING,
                                "Logcat reader: $line"
                            )
                        }
                    }
                }

                if (!isStopped && !parsedAnyLine) {
                    addMessage(
                        DebugType.WARNING,
                        "No app logs were received. This Android version or device may restrict Logcat access to this app."
                    )
                }

            } catch (exception: Exception) {
                if (!isStopped) {
                    addMessage(
                        DebugType.ERROR,
                        "Could not start Logcat reader: ${exception.message ?: exception.javaClass.simpleName}"
                    )
                }
            } finally {
                try {
                    process?.destroy()
                } catch (_: Exception) {
                }

                if (logcatProcess === process) {
                    logcatProcess = null
                }
            }
        }.apply {
            name = "KT-Debugger-Logcat"
            isDaemon = true
            start()
        }
    }

    /**
     * Parses one Android Logcat threadtime line.
     * Returns true when a message from this process was stored.
     */
    private fun parseLogcatLine(line: String): Boolean {
        val pattern = Regex(
            """^\s*\d{2}-\d{2}\s+\d{2}:\d{2}:\d{2}\.\d+\s+(\d+)\s+(\d+)\s+([VDIWEF])\s+(.+?):\s?(.*)$"""
        )

        val match = pattern.find(line) ?: return false

        val pid = match.groupValues[1].toIntOrNull() ?: return false
        if (pid != AndroidProcess.myPid()) return false

        val priority = match.groupValues[3]
        val tag = match.groupValues[4].trim()
        val message = match.groupValues[5]

        val type = when (priority) {
            "V", "D", "I" -> DebugType.INFO
            "W" -> DebugType.WARNING
            "E", "F" -> DebugType.ERROR
            else -> return false
        }

        addMessage(type, "$tag: $message")
        return true
    }

    // --------------------------------------------------
    // SHOW
    // --------------------------------------------------

    fun show(): KtDebugger {
        if (isStopped) return this

        handler.post {
            if (activity.isFinishing || activity.isDestroyed) return@post

            if (overlay == null) {
                createOverlay()
            }

            overlay?.visibility = View.VISIBLE
            refresh()
            startLogcatReader()
        }

        return this
    }

    // --------------------------------------------------
    // HIDE
    // Hiding does not stop Logcat collection.
    // --------------------------------------------------

    fun hide(): KtDebugger {
        if (isStopped) return this

        handler.post {
            overlay?.visibility = View.GONE
        }

        return this
    }

    // --------------------------------------------------
    // CLEAR
    // --------------------------------------------------

    fun clear(): KtDebugger {
        synchronized(lock) {
            messages.clear()
        }

        handler.post { refresh() }
        return this
    }

    // --------------------------------------------------
    // STOP
    // --------------------------------------------------

    fun stop() {
        if (isStopped) return

        isStopped = true

        try {
            logcatProcess?.destroy()
        } catch (_: Exception) {
        }
        logcatProcess = null

        readerThread?.interrupt()
        readerThread = null

        synchronized(lock) {
            messages.clear()
        }

        handler.post {
            val parent = overlay?.parent as? ViewGroup
            parent?.removeView(overlay)

            overlay = null
            bubble = null
            panel = null
            logContainer = null
            scrollView = null
            infoButton = null
            warningButton = null
            errorButton = null
        }
    }

    // --------------------------------------------------
    // CREATE OVERLAY
    // --------------------------------------------------

    private fun createOverlay() {
        val decor = activity.window.decorView as? ViewGroup ?: return
        val root = FrameLayout(activity)
        overlay = root

        // Floating bubble
        val floating = TextView(activity).apply {
            text = "KT"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            background = rounded(Color.rgb(30, 100, 230), 50)
            elevation = dp(10).toFloat()
        }

        bubble = floating

        val bubbleParams = FrameLayout.LayoutParams(
            dp(52),
            dp(52),
            Gravity.END or Gravity.CENTER_VERTICAL
        ).apply {
            marginEnd = dp(16)
        }

        root.addView(floating, bubbleParams)

        // Debug panel
        val debugPanel = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(12), dp(12), dp(12))
            background = rounded(Color.rgb(20, 24, 34), 14)
            elevation = dp(12).toFloat()
            visibility = View.GONE
        }

        panel = debugPanel

        val panelParams = FrameLayout.LayoutParams(
            dp(320),
            dp(430),
            Gravity.CENTER
        ).apply {
            setMargins(dp(12), dp(12), dp(12), dp(12))
        }

        root.addView(debugPanel, panelParams)

        // Header
        val header = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = text("KT DEBUGGER", 16f, Color.WHITE).apply {
            typeface = Typeface.DEFAULT_BOLD
        }

        header.addView(
            title,
            LinearLayout.LayoutParams(0, dp(40), 1f)
        )

        val close = text("−", 24f, Color.WHITE).apply {
            gravity = Gravity.CENTER
            setOnClickListener {
                debugPanel.visibility = View.GONE
            }
        }

        header.addView(
            close,
            LinearLayout.LayoutParams(dp(40), dp(40))
        )

        debugPanel.addView(header)

        // Filter buttons
        val filters = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        infoButton = filterButton("INFO", DebugType.INFO)
        warningButton = filterButton("WARN", DebugType.WARNING)
        errorButton = filterButton("ERROR", DebugType.ERROR)

        filters.addView(infoButton, weightedParams())
        filters.addView(warningButton, weightedParams())
        filters.addView(errorButton, weightedParams())

        debugPanel.addView(filters)

        // Clear button
        val clearButton = text("CLEAR LOGS", 11f, Color.LTGRAY).apply {
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            setPadding(0, dp(8), 0, dp(8))
            setOnClickListener { clear() }
        }

        debugPanel.addView(clearButton)

        // Log list
        val logs = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
        }

        logContainer = logs

        val scroll = ScrollView(activity).apply {
            isFillViewport = true
            addView(
                logs,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        scrollView = scroll

        debugPanel.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        // Bubble click and dragging
        var startX = 0f
        var startY = 0f
        var initialX = 0f
        var initialY = 0f
        var moved = false

        floating.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    startY = event.rawY
                    initialX = view.translationX
                    initialY = view.translationY
                    moved = false
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - startX
                    val dy = event.rawY - startY

                    if (kotlin.math.abs(dx) > dp(5) ||
                        kotlin.math.abs(dy) > dp(5)
                    ) {
                        moved = true
                    }

                    if (moved) {
                        val minX = -view.left.toFloat()
                        val maxX = (root.width - view.right).toFloat()
                        val minY = -view.top.toFloat()
                        val maxY = (root.height - view.bottom).toFloat()

                        view.translationX =
                        (initialX + dx).coerceIn(minX, maxX)

                        view.translationY =
                        (initialY + dy).coerceIn(minY, maxY)
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (!moved) {
                        debugPanel.visibility =
                        if (debugPanel.visibility == View.VISIBLE) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }
                    true
                }

                else -> true
            }
        }

        decor.addView(
            root,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        refresh()
    }

    // --------------------------------------------------
    // FILTER BUTTONS
    // --------------------------------------------------

    private fun filterButton(
        label: String,
        type: DebugType
    ): TextView {
        return text(label, 10f, Color.WHITE).apply {
            gravity = Gravity.CENTER
            setPadding(dp(4), dp(9), dp(4), dp(9))

            setOnClickListener {
                synchronized(lock) {
                    if (!filterConfigured) {
                        selectedTypes.clear()
                        selectedTypes.addAll(DebugType.entries)
                        filterConfigured = true
                    }

                    if (type in selectedTypes) {
                        selectedTypes.remove(type)
                    } else {
                        selectedTypes.add(type)
                    }
                }

                refresh()
            }
        }
    }

    // --------------------------------------------------
    // REFRESH LOGS
    // --------------------------------------------------

    private fun refresh() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post { refresh() }
            return
        }

        val container = logContainer ?: return
        val filtered = getMessages()

        container.removeAllViews()

        filtered.forEach { item ->
            val color = when (item.type) {
                DebugType.INFO -> Color.rgb(75, 160, 255)
                DebugType.WARNING -> Color.rgb(255, 190, 65)
                DebugType.ERROR -> Color.rgb(255, 85, 85)
            }

            val time = SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(Date(item.timestamp))

            val log = text(
                "[$time] [${item.type}] ${item.message}",
                12f,
                color
            ).apply {
                setPadding(0, dp(7), 0, dp(7))
            }

            container.addView(log)
        }

        updateFilterButtons()

        scrollView?.post {
            scrollView?.fullScroll(View.FOCUS_DOWN)
        }
    }

    // --------------------------------------------------
    // UPDATE FILTER BUTTON APPEARANCE
    // --------------------------------------------------

    private fun updateFilterButtons() {
        val activeTypes = synchronized(lock) {
            if (!filterConfigured) {
                DebugType.entries.toSet()
            } else {
                selectedTypes.toSet()
            }
        }

        infoButton?.alpha =
        if (DebugType.INFO in activeTypes) 1f else 0.35f

        warningButton?.alpha =
        if (DebugType.WARNING in activeTypes) 1f else 0.35f

        errorButton?.alpha =
        if (DebugType.ERROR in activeTypes) 1f else 0.35f
    }

    // --------------------------------------------------
    // UI HELPERS
    // --------------------------------------------------

    private fun text(
        value: String,
        size: Float,
        color: Int
    ): TextView {
        return TextView(activity).apply {
            text = value
            textSize = size
            setTextColor(color)
        }
    }

    private fun rounded(
        color: Int,
        radius: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    private fun dp(value: Int): Int {
        return (value * activity.resources.displayMetrics.density).toInt()
    }

    private fun weightedParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            0,
            dp(36),
            1f
        ).apply {
            setMargins(dp(3), dp(6), dp(3), dp(6))
        }
    }
}

// --------------------------------------------------
// FACTORY FUNCTION
// --------------------------------------------------

fun Activity.ktDebugger(): KtDebugger {
    return KtDebugger(this)
}
