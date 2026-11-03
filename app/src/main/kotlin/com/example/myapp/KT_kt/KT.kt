@file:Suppress("DEPRECATION")

package com.KT_kt

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.SoundPool
import android.media.MediaPlayer
import android.os.*
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.view.WindowManager
import android.widget.*
import android.net.Uri
import android.content.Intent
import android.widget.ListView


open class ktActivity : Activity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()
        onCreate()

    }
    open fun onCreate() {

        // Override this in subclasses.
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        KtFilePickerManager.onActivityResult(
            this,
            requestCode,
            resultCode,
            data
        )
    }

}

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

// -------------------------
// ADD VIEWS
// -------------------------

fun ViewGroup.KtAddViews(vararg views: View) {
    views.forEach { addView(it) }
}

fun Context.KtAddViews(
    block: LinearLayout.() -> Unit
): LinearLayout {
    return LinearLayout(this).apply(block)
}

//---

fun Context.dp(value: Number): Int {
    return (value.toFloat() *
        resources.displayMetrics.density).toInt()
}

fun Context.sp(value: Number): Float {
    return value.toFloat() *
    resources.displayMetrics.scaledDensity
}

fun Context.px(value: Number): Int {
    return value.toInt()
}

//---

object KtColor :Kt_Color()

fun Context.KtVibrator(duration: Int) =
Kt_Vibrator(this, duration)

fun Context.KtVibrator(pattern: LongArray) =
Kt_Vibrator(this, pattern)

//---

fun Context.KtButton(
    block: Button.() -> Unit = {}
): Button {
    return Button(this).apply(block)
}

fun Context.KtButton(
    text: String,
    block: Button.() -> Unit = {}
): Button {
    return Button(this).apply {
        this.text = text
        block()
    }
}



fun Context.KtLinearLayout(
    block: LinearLayout.() -> Unit = {}
): LinearLayout {

    return LinearLayout(this).apply(block)
}

fun Context.KtCardLayout(
    bgColor: Int = Color.WHITE,
    corner: Number = 18,
    padding: Number = 18,
    block: LinearLayout.() -> Unit = {}
): LinearLayout {
    return LinearLayout(this).apply {
        orientation = 1
        setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
        background = KtShape {
            setColor(bgColor)
            setCornerRadius(dp(corner).toFloat())
        }
        elevation = dp(2).toFloat()
        block()
    }
}

fun Context.KtRowLayout(block: LinearLayout.() -> Unit = {}) = KtLinearLayout {
    orientation = 0
    block()
}

fun Context.KtColumnLayout(block: LinearLayout.() -> Unit = {}) = KtLinearLayout {
    orientation = 1
    block()
}

fun Context.KtGap(h: Number = 12) = KtSpace { minimumHeight = dp(h) }

fun Context.KtLabelText(text: String, color: Int, size: Number = 11) = KtTextView {
    this.text = text
    textSize = size.toFloat()
    setTextColor(color)
}

fun Context.KtDivider(c: Int = KtColor.rgb(230, 230, 230)) = KtView { setBackgroundColor(c); layoutParams = LinearLayout.LayoutParams(-1, dp(1)) }

fun Context.KtFrameLayout(
    block: FrameLayout.() -> Unit = {}
): FrameLayout {
    return FrameLayout(this).apply(block)
}

fun Context.KtRelativeLayout(
    block: RelativeLayout.() -> Unit = {}
): RelativeLayout {
    return RelativeLayout(this).apply(block)
}

fun Context.KtAbsoluteLayout(
    block: AbsoluteLayout.() -> Unit = {}
): AbsoluteLayout {
    return AbsoluteLayout(this).apply(block)
}

fun Context.KtTableLayout(
    block: TableLayout.() -> Unit = {}
): TableLayout {
    return TableLayout(this).apply(block)
}

fun Context.KtTableRow(
    block: TableRow.() -> Unit = {}
): TableRow {
    return TableRow(this).apply(block)
}

fun Context.KtGridLayout(
    block: GridLayout.() -> Unit = {}
): GridLayout {
    return GridLayout(this).apply(block)
}

fun Context.KtScrollView(
    block: ScrollView.() -> Unit = {}
): ScrollView {
    return ScrollView(this).apply(block)
}

fun Context.KtHorizontalScrollView(
    block: HorizontalScrollView.() -> Unit = {}
): HorizontalScrollView {
    return HorizontalScrollView(this).apply(block)
}

fun Context.KtTextView(
    block: TextView.() -> Unit = {}
): TextView {
    return TextView(this).apply(block)
}

// With text
fun Context.KtTextView(
    text: String,
    block: TextView.() -> Unit = {}
): TextView {
    return TextView(this).apply {
        this.text = text
        block()
    }
}


fun Context.KtEditText(
    block: EditText.() -> Unit = {}
): EditText {
    return EditText(this).apply(block)
}

fun Context.KtImageView(
    block: ImageView.() -> Unit = {}
): ImageView {
    return ImageView(this).apply(block)
}

fun Context.KtCheckBox(
    block: CheckBox.() -> Unit = {}
): CheckBox {
    return CheckBox(this).apply(block)
}

fun Context.KtSwitch(
    block: Switch.() -> Unit = {}
): Switch {
    return Switch(this).apply(block)
}

fun Context.KtProgressBar(
    block: ProgressBar.() -> Unit = {}
): ProgressBar {
    return ProgressBar(this).apply(block)
}

fun Context.KtRatingBar(
    block: RatingBar.() -> Unit = {}
): RatingBar {
    return RatingBar(this).apply(block)
}

fun Context.KtView(
    block: View.() -> Unit = {}
): View {
    return View  (this).apply(block)
}

fun Context.KtVideoView(
    block: VideoView.() -> Unit = {}
): VideoView {
    return VideoView  (this).apply(block)
}

fun Context.KtSpace(
    block: Space.() -> Unit = {}
): Space {
    return Space  (this).apply(block)
}

fun Context.KtRadioGroup(
    block: RadioGroup.() -> Unit = {}
): RadioGroup {
    return RadioGroup  (this).apply(block)
}

fun Context.KtAutoCompleteTextView(
    block: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView {
    return AutoCompleteTextView  (this).apply(block)
}

fun Context.KtViewStub(
    block: ViewStub.() -> Unit = {}
): ViewStub {
    return ViewStub  (this).apply(block)
}

fun Context.KtToggleButton(
    block: ToggleButton.() -> Unit = {}
): ToggleButton {
    return ToggleButton  (this).apply(block)
}

fun Context.KtRadioButton(
    block: RadioButton.() -> Unit = {}
): RadioButton {
    return RadioButton  (this).apply(block)
}

fun Context.KtImageButton(
    block: ImageButton.() -> Unit = {}
): ImageButton {
    return ImageButton  (this).apply(block)
}

fun Context.KtImageButton(
    imageRes: Int,
    block: ImageButton.() -> Unit = {}
): ImageButton {
    return ImageButton(this).apply {
        setImageResource(imageRes)
        block()
    }
}

fun Context.KtMultiAutoCompleteTextView(
    block: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView =
MultiAutoCompleteTextView(this).apply(block)

fun <T> Context.KtListView(
    items: List<T>,
    itemView: Context.(T) -> View
): ListView {
    return ListView(this).apply {
        adapter = object : ArrayAdapter<T>(this@KtListView, 0, items) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!)
            }
        }
    }
}

fun <T> Context.KtRecycleListView(
    items: List<T>,
    itemView: Context.(T, View?) -> View
): ListView {
    return ListView(this).apply {
        adapter = object : ArrayAdapter<T>(this@KtRecycleListView, 0, items) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!, convertView)
            }
        }
    }
}

fun ListView.refresh() {
    (adapter as? ArrayAdapter<*>)?.notifyDataSetChanged()
}

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
    setOnItemClickListener { _, _, position, id ->action(position)
    }
}

fun <T> Context.KtSpinner(
    items: List<T>,
    itemView: Context.(T) -> View
): Spinner {
    return Spinner(this).apply {
        adapter = object : ArrayAdapter<T>(this@KtSpinner, 0, items) {

            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!)
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!)
            }
        }
    }
}

fun <T> Context.KtRecycleSpinner(
    items: List<T>,
    itemView: Context.(T, View?) -> View
): Spinner {
    return Spinner(this).apply {
        adapter = object : ArrayAdapter<T>(this@KtRecycleSpinner, 0, items) {

            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!, convertView)
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!, convertView)
            }
        }
    }
}

fun <T> Context.KtGridView(
    items: List<T>,
    numColumns: Int = 2,
    itemView: Context.(T) -> View
): GridView {
    return GridView(this).apply {
        this.numColumns = numColumns
        adapter = object : ArrayAdapter<T>(this@KtGridView, 0, items) {
            override fun getView(pos: Int, cv: View?, parent: ViewGroup): View {
                return itemView(context, getItem(pos)!!)
            }
        }
    }
}

fun <T> Context.KtRecycleGridView(
    items: List<T>,
    numColumns: Int = 2,
    itemView: Context.(T, View?) -> View
): GridView {
    return GridView(this).apply {
        this.numColumns = numColumns
        adapter = object : ArrayAdapter<T>(this@KtRecycleGridView, 0, items) {
            override fun getView(pos: Int, cv: View?, parent: ViewGroup): View {
                return itemView(context, getItem(pos)!!, cv)
            }
        }
    }
}

class KtExpandableScope<G, C> {
    var parentView: (Context.(G, Boolean) -> View)? = null
    var childView: (Context.(C) -> View)? = null

    fun parentView(view: Context.(G, Boolean) -> View) { parentView = view }
    fun childView(view: Context.(C) -> View) { childView = view }
}

fun <G, C> Context.KtExpandableListView(
    pData: List<G>,
    cData: List<List<C>>,
    build: KtExpandableScope<G, C>.() -> Unit
): ExpandableListView {
    val scope = KtExpandableScope<G, C>().apply(build)
    return ExpandableListView(this).apply {
        setAdapter(object : BaseExpandableListAdapter() {
                override fun getGroupCount() = pData.size
                override fun getChildrenCount(gPos: Int) = cData.getOrNull(gPos)?.size ?: 0
                override fun getGroup(gPos: Int) = pData[gPos]
                override fun getChild(gPos: Int, cPos: Int) = cData[gPos][cPos]
                override fun getGroupId(gPos: Int) = gPos.toLong()
                override fun getChildId(gPos: Int, cPos: Int) = cPos.toLong()
                override fun hasStableIds() = false
                override fun isChildSelectable(gPos: Int, cPos: Int) = true

                override fun getGroupView(gPos: Int, isExpanded: Boolean, cv: View?, parent: ViewGroup): View {
                    return scope.parentView!!.invoke(this@KtExpandableListView, pData[gPos], isExpanded)
                }
                override fun getChildView(gPos: Int, cPos: Int, isLastChild: Boolean, cv: View?, parent: ViewGroup): View {
                    return scope.childView!!.invoke(this@KtExpandableListView, cData[gPos][cPos])
                }
        })
    }
}
//---

fun Context.KtToast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
): Toast {
    return Toast.makeText(this, message, duration)
}

fun Context.KtDialog(
    block: Dialog.() -> Unit = {}
): Dialog {
    return Dialog(this).apply(block)
}

class KtShape(
    block: KtShape.() -> Unit = {}
) : GradientDrawable() {

    init {
        apply(block)
    }
}

fun Activity.KtNavBar(
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


//sounds

/**
 * Creates a SoundPool for playing short audio clips.
 *
 * @param maxStreams Maximum number of simultaneous sound streams.
 * @return A configured SoundPool.
 */
fun Context.KtSoundPool(
    maxStreams: Int = 5
): SoundPool {
    return SoundPool.Builder()
    .setMaxStreams(maxStreams)
    .build()
}

/**
 * Creates a [MediaPlayer] using the current [Context].
 *
 * @param block Configuration block applied to the MediaPlayer.
 * @return The configured MediaPlayer.
 *
 * @see MediaPlayer
 */

fun Activity.KtFilePicker(
    type: String = "*/*",
    onResult: (Uri?) -> Unit
) {
    KtFilePickerManager.launch(this, type, false) {
        onResult(it.firstOrNull())
    }
}

fun Activity.KtFilePickerMultiple(
    type: String = "*/*",
    onResult: (List<Uri>) -> Unit
) {
    KtFilePickerManager.launch(this, type, true, onResult)
}

//---
