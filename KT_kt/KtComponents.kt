
@file:Suppress("ALL", "package.mismatch", "kt.unusedParameter", "kt.usePropertyAccess")

package com.KT_kt

import android.R.drawable
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.icu.util.Calendar
import android.media.SoundPool
import android.net.Uri
import android.os.*
import android.provider.OpenableColumns
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import android.widget.ListView
import java.util.concurrent.Executors
import java.util.concurrent.Future
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
// -------------------------
// DP, SP AND PX
// -------------------------

val Number.dp: Int
get() = (toFloat() * Resources.getSystem()
    .displayMetrics.density).toInt()

val Number.sp: Float
get() = toFloat() * Resources.getSystem()
.displayMetrics.scaledDensity

val Number.px: Int
get() = toInt()

//---
fun String.toUri(): Uri {
    return Uri.parse(this)
}
// -------------------------
// ADD VIEWS
// -------------------------

fun ViewGroup.addViews(vararg views: View) {
    views.forEach { addView(it) }
}

//---

fun ListView.refresh() {
    (adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
}

//---

fun ListView.setOnItemClickListener(
    action: (position: Int, id: Long) -> Unit
) {
    setOnItemClickListener { _, _, position, id ->
        action(position, id)
    }
}

fun ListView.setOnItemClickListener(
    action: (position: Int) -> Unit
) {
    setOnItemClickListener { _, _, position, id -> action(position)
    }
}

//---

fun Context.ktToast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
): Toast {
    return Toast.makeText(this, message, duration)
}

fun Context.ktDialog(
    block: Dialog.() -> Unit = {}
): Dialog {
    return Dialog(this).apply(block)
}

fun Context.ktDialog(
    title: String = "",
    message: String = "",
    cancelable: Boolean = true,
    block: AlertDialog.Builder.() -> Unit = {}
): AlertDialog {

    return AlertDialog.Builder(this).apply {
        if (title.isNotEmpty()) setTitle(title)
        if (message.isNotEmpty()) setMessage(message)

        setCancelable(cancelable)

        block()
    }.create()
}

fun Context.ktDateDialog(
    year: Int,
    month: Int,
    day: Int,
    block: DatePickerDialog.(
        Int, Int, Int
    ) -> Unit = { _, _, _ -> }
): DatePickerDialog {

    return DatePickerDialog(
        this,
        { _, y, m, d ->
            // Selection callback
        },
        year,
        month,
        day
    )
}

@SuppressLint("NewApi")
fun Context.ktTimeDialog(
    hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    is24Hour: Boolean = true,
    block: (hour: Int, minute: Int) -> Unit
): TimePickerDialog {

    return TimePickerDialog(
        this,
        { _, h, m ->
            block(h, m)
        },
        hour,
        minute,
        is24Hour
    )
}

fun Context.ktProgressDialog(
    title: String = "Loading",
    message: String = "Please wait...",
    horizontal: Boolean = false,
    cancelable: Boolean = false,
    block: ProgressBar.() -> Unit = {}
): AlertDialog {

    val progress = ProgressBar(
        this,
        null,
        if (horizontal) {
            android.R.attr.progressBarStyleHorizontal
        } else {
            android.R.attr.progressBarStyle
        }
    ).apply {
        if (horizontal) {
            max = 100
        }
        block()
    }

    val content = ktColumnLayout {
        setPadding(32, 20, 32, 20)

        if (message.isNotEmpty()) {
            addView(ktTextView(message))
        }

        addView(progress)
    }

    return AlertDialog.Builder(this)
    .setTitle(title)
    .setView(content)
    .setCancelable(cancelable)
    .create()
}

//---
fun ktShapeDrawable(
    color: Int = Color.TRANSPARENT,
    style: Paint.Style = Paint.Style.FILL,
    strokeWidth: Float = 0f
): ShapeDrawable {

    return ShapeDrawable(RectShape()).apply {
        paint.color = color
        paint.style = style
        paint.strokeWidth = strokeWidth
    }
}

fun ktGradientDrawable(
    color: Int = Color.TRANSPARENT,
    radius: Float = 0f,
    strokeWidth: Int = 0,
    strokeColor: Int = Color.TRANSPARENT,
    shape: Int = GradientDrawable.RECTANGLE
): GradientDrawable {

    return GradientDrawable().apply {
        this.shape = shape
        setColor(color)

        cornerRadius = radius

        if (strokeWidth > 0) {
            setStroke(strokeWidth, strokeColor)
        }
    }
}

fun ktRippleDrawable(
    rippleColor: Int,
    content: Drawable? = null,
    mask: Drawable? = null
): RippleDrawable {

    return RippleDrawable(
        ColorStateList.valueOf(rippleColor),
        content,
        mask
    )
}

//---
fun Activity.ktNavBar(
    mode: String = "show",
    color: Int = Color.BLACK,
    icons: String = "light",
    transparent: Boolean = false
) {
    val window = window
    val decor = window.decorView

    var flags = decor.systemUiVisibility

    flags = when (mode.lowercase()) {
        "show" -> flags and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        "hide" -> flags or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        else -> throw IllegalArgumentException("mode must be 'show' or 'hide'")
    }

    flags = when (icons.lowercase()) {
        "dark" -> flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        "light" -> flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        else -> throw IllegalArgumentException("icons must be 'dark' or 'light'")
    }

    decor.systemUiVisibility = flags

    if (transparent) {
        window.navigationBarColor = Color.TRANSPARENT
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    } else {
        window.navigationBarColor = color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
}

fun Activity.KtStatusBar(
    mode: String = "show",
    color: Int = Color.BLACK,
    icons: String = "light",
    transparent: Boolean = false
) {
    val window = window
    val decor = window.decorView

    var flags = decor.systemUiVisibility

    flags = when (mode.lowercase()) {
        "show" -> flags and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
        "hide" -> flags or View.SYSTEM_UI_FLAG_FULLSCREEN
        else -> throw IllegalArgumentException("mode must be 'show' or 'hide'")
    }

    flags = when (icons.lowercase()) {
        "dark" -> flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        "light" -> flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        else -> throw IllegalArgumentException("icons must be 'dark' or 'light'")
    }

    decor.systemUiVisibility = flags

    if (transparent) {
        window.statusBarColor = Color.TRANSPARENT
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    } else {
        window.statusBarColor = color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
}

//---

fun Context.ktSoundPool(
    maxStreams: Int = 5
): SoundPool {
    return SoundPool.Builder()
    .setMaxStreams(maxStreams)
    .build()
}

// fun Activity.ktFilePicker(
//     type: String = "*/*",
//     multiple:Boolean = false,
//     onResult : (Uri?) -> Unit = {}
// ) {
//     KtFilePickerManager.launch(this, type, multiple) {
//         onResult(it.firstOrNull())
//     }
// }

fun Activity.ktFilePicker(
    type: String = "*/*",
    multiple: Boolean = false,
    onResult: (List<Uri>) -> Unit = {}
) {
    KtFilePickerManager.launch(this, type, multiple) {
        onResult(it)
    }
}
//---

private val pool = Executors.newFixedThreadPool(4)
private val mainHandler = Handler(Looper.getMainLooper())

fun <T> ktThread(
    work: () -> T,
    onDone: (Result<T>) -> Unit
): Future<*> {
    return pool.submit(Runnable {
            val result = runCatching {
                work()
            }

            mainHandler.post {
                onDone(result)
            }
    })
}

//---

/**
 ktButton("Play").setId(100)
 */
fun <T : View> T.setId(id: Int): T {
    this.id = id
    return this
}

/**
 ktButton("Play").setTag("btn1")
 */
fun <T : View> T.setTag(name: String): T {
    tag = name
    return this
}

/**
* val txt = mainLay.getViewByid<TextView>(100)
*/

// inline fun <reified T : View> ViewGroup.getId(id: Int): T? {
//     return findViewById(id)
// }

/**
* val txt = mainLay.getViewbyTag<TextView>("message")
*/
inline fun <reified T : View> ViewGroup.findViewByTag(name: String): T? {
    return findViewWithTag(name)
}

//---
// inline fun <reified T : View> Activity.getViewById(id: Int): T? {
//     return findViewById(id)
// }

inline fun <reified T : View> Activity.findViewByTag(name: String): T? {
    val root = findViewById<View>(android.R.id.content)
    return root.findViewWithTag(name)
}

//---

fun Context.ktUriTOFileName(uri: Uri): String? {

    contentResolver.query(
        uri,
        arrayOf(OpenableColumns.DISPLAY_NAME),
        null,
        null,
        null
    )?.use { cursor ->

        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(
                OpenableColumns.DISPLAY_NAME
            )

            if (index != -1) {
                return cursor.getString(index)
            }
        }
    }

    return null
}

/**usageval drawable = ktGradientDrawable(
color = Color.BLUE,
radius = 20f,
strokeWidth = 2,
strokeColor = Color.WHITE
)

button.background = drawable
*
val ripple = ktRippleDrawable(
rippleColor = Color.LTGRAY,
content = ktGradientDrawable(
color = Color.BLUE,
radius = 20f
)
)

button.background = ripple
*/
