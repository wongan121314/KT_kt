
@file:Suppress("ALL")

package com.KT_kt

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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

object KtColor :Kt_Color()

fun Context.KtVibrator(duration: Int) =
Kt_Vibrator(this, duration)

fun Context.KtVibrator(pattern: LongArray) =
Kt_Vibrator(this, pattern)

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

//---
class KtShape(
    block: KtShape.() -> Unit = {}
) : GradientDrawable() {

    init {
        apply(block)
    }
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

fun Activity.ktFilePicker(
    type: String = "*/*",
    multiple:Boolean = false,
    onResult : (Uri?) -> Unit = {}
) {
    KtFilePickerManager.launch(this, type, multiple) {
        onResult(it.firstOrNull())
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

inline fun <reified T : View> ViewGroup.getId(id: Int): T? {
    return findViewById(id)
}

/**
* val txt = mainLay.getViewbyTag<TextView>("message")
*/
inline fun <reified T : View> ViewGroup.getTag(name: String): T? {
    return findViewWithTag(name)
}

//---
inline fun <reified T : View> Activity.getViewById(id: Int): T? {
    return findViewById(id)
}

inline fun <reified T : View> Activity.getViewByTag(name: String): T? {
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
