@file:Suppress("ALL", "kt.unusedParameter", "package.mismatch")

package com.KT_kt

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.*

//--- all buttons viewes

fun Context.ktButton(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: Button.() -> Unit = {}
): Button {

    return Button(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)

        layoutParams = ViewGroup.LayoutParams(width, height)

        block()
    }
}

fun Context.ktToggleButton(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: ToggleButton.() -> Unit = {}
): ToggleButton {

    return ToggleButton(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}
fun Context.ktRadioButton(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: RadioButton.() -> Unit = {}
): RadioButton {

    return RadioButton(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktImageButton(
    imageRes: Int = 0,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: ImageButton.() -> Unit = {}
): ImageButton {

    return ImageButton(this).apply {
        if (imageRes != 0) setImageResource(imageRes)

        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)

        block()
    }
}

/**
 * all layouts views
 */

fun Context.ktLinearLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: LinearLayout.() -> Unit = {}
): LinearLayout {

    return LinearLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktCardLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.WHITE,
    cornerRadius: Float = 16f,
    elevation: Float = 4f,
    strokeColor: Int = Color.TRANSPARENT,
    strokeWidth: Int = 0,
    block: FrameLayout.() -> Unit = {}
): FrameLayout {

    return FrameLayout(this).apply {

        background = GradientDrawable().apply {
            setColor(bgColor)
            setCornerRadius(cornerRadius * resources.displayMetrics.density)

            if (strokeWidth > 0) {
                setStroke(
                    (strokeWidth * resources.displayMetrics.density).toInt(),
                    strokeColor
                )
            }
        }

        clipToOutline = true

        this.elevation = elevation *
        resources.displayMetrics.density

        layoutParams = ViewGroup.LayoutParams(width, height)

        block()
    }
}

fun Context.ktRowLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: LinearLayout.() -> Unit = {}
): LinearLayout = ktLinearLayout(
    bgColor, width, height
) {
    orientation = LinearLayout.HORIZONTAL
    block()
}

fun Context.ktColumnLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: LinearLayout.() -> Unit = {}
): LinearLayout = ktLinearLayout(
    bgColor, width, height
) {
    orientation = LinearLayout.VERTICAL
    block()
}

fun Context.ktFrameLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: FrameLayout.() -> Unit = {}
): FrameLayout {

    return FrameLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktRelativeLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: RelativeLayout.() -> Unit = {}
): RelativeLayout {

    return RelativeLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktAbsoluteLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: AbsoluteLayout.() -> Unit = {}
): AbsoluteLayout {

    return AbsoluteLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktTableLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: TableLayout.() -> Unit = {}
): TableLayout {

    return TableLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktGridLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    block: GridLayout.() -> Unit = {}
): GridLayout {

    return GridLayout(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktHorizontalScrollView(
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: HorizontalScrollView.() -> Unit = {}
): HorizontalScrollView {

    return HorizontalScrollView(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktScrollView(
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: ScrollView.() -> Unit = {}
): ScrollView {

    return ScrollView(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktTableRow(
    bgColor: Int = Color.TRANSPARENT,
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    block: TableRow.() -> Unit = {}
): TableRow {

    return TableRow(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = TableLayout.LayoutParams(width, height)
        block()
    }
}
/**
 * all text views
 * 
 */

fun Context.ktTextView(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    block: TextView.() -> Unit = {}
): TextView {

    return TextView(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)

        layoutParams = ViewGroup.LayoutParams(width, height)

        block()
    }
}

fun Context.ktCheckedTextView(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: CheckedTextView.() -> Unit = {}
): CheckedTextView {

    return CheckedTextView(this).apply {
        this.text = text
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktTextClock(
    size: Float = 18f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: TextClock.() -> Unit = {}
): TextClock {

    return TextClock(this).apply {
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

/**
* all input View
*/

fun Context.ktEditText(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: EditText.() -> Unit = {}
): EditText {

    return EditText(this).apply {
        setText(text)
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktMultiAutoCompleteTextView(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView {

    return MultiAutoCompleteTextView(this).apply {
        setText(text)
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktAutoCompleteTextView(
    text: String = "",
    size: Float = 14f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView {

    return AutoCompleteTextView(this).apply {
        setText(text)
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

/**
 * all picker views 
 */

fun Context.ktNumberPicker(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: NumberPicker.() -> Unit = {}
): NumberPicker {

    return NumberPicker(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktTimePicker(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: TimePicker.() -> Unit = {}
): TimePicker {
    return TimePicker(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktCalendarView(
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    block: CalendarView.() -> Unit = {}
): CalendarView {
    return CalendarView(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktSpinner(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    mode: Int = Spinner.MODE_DROPDOWN,
    block: Spinner.() -> Unit = {}
): Spinner {
    return Spinner(this, mode).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}
/**
* all other views
*/

fun Context.ktChronometer(
    size: Float = 18f,
    color: Int = Color.BLACK,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: Chronometer.() -> Unit = {}
): Chronometer {

    return Chronometer(this).apply {
        textSize = size
        setTextColor(color)
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktRatingBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: RatingBar.() -> Unit = {}
): RatingBar {

    return RatingBar(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktSeekBar(
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    block: SeekBar.() -> Unit = {}
): SeekBar {

    return SeekBar(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktProgressBar(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    style: Int = android.R.attr.progressBarStyleHorizontal,
    block: ProgressBar.() -> Unit = {}
): ProgressBar {

    return ProgressBar(this, null, style).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktImageView(
    imageRes: Int = 0,
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: ImageView.() -> Unit = {}
): ImageView {

    return ImageView(this).apply {
        if (imageRes != 0) {
            setImageResource(imageRes)
        }

        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktCheckBox(
    block: CheckBox.() -> Unit = {}
): CheckBox {
    return CheckBox(this).apply(block)
}

fun Context.ktSwitch(
    block: Switch.() -> Unit = {}
): Switch {
    return Switch(this).apply(block)
}

fun Context.ktProgressBar(
    block: ProgressBar.() -> Unit = {}
): ProgressBar {
    return ProgressBar(this).apply(block)
}

fun Context.ktView(
    bgColor: Int = Color.TRANSPARENT,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: View.() -> Unit = {}
): View {

    return View(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktVideoView(
    bgColor: Int = Color.TRANSPARENT,
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    block: VideoView.() -> Unit = {}
): VideoView {

    return VideoView(this).apply {
        setBackgroundColor(bgColor)
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

fun Context.ktSpace(
    width: Int = 10,
    height: Int = 10,
    block: Space.() -> Unit = {}
): Space {

    return Space(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)
        block()
    }
}

//----------------
fun Context.ktRadioGroup(
    block: RadioGroup.() -> Unit = {}
): RadioGroup {
    return RadioGroup  (this).apply(block)
}

fun Context.KtViewStub(
    block: ViewStub.() -> Unit = {}
): ViewStub {
    return ViewStub  (this).apply(block)
}

/**
* all group views
*/

fun <T> Context.ktListView(
    items: MutableList<T> = mutableListOf(),
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    textSize: Float = 16f,
    textColor: Int = Color.BLACK,
    itemBgColor: Int = Color.TRANSPARENT,
    dividerColor: Int = Color.LTGRAY,
    dividerHeight: Int = 1,
    block: ListView.() -> Unit = {}
): ListView {

    return ListView(this).apply {

        layoutParams = ViewGroup.LayoutParams(width, height)

        setBackgroundColor(bgColor)
        divider = ColorDrawable(dividerColor)
        this.dividerHeight = dividerHeight

        adapter = ktAdapter(items) {
            getView { item, position ->
                ktTextView(
                    text = item.toString(),
                    size = textSize,
                    color = textColor,
                    bgColor = itemBgColor,
                    width = MATCH_PARENT
                )
            }
        }

        block()
    }
}

fun <T> Context.ktRecycleListView(
    items: List<T>,
    itemView: Context.(T, View?) -> View
): ListView {
    return ListView(this).apply {
        adapter = object : ArrayAdapter<T>(this@ktRecycleListView, 0, items) {
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

fun <T> Context.ktGridView(
    items: List<T> = emptyList(),
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    textSize: Float = 16f,
    textColor: Int = Color.BLACK,
    itemBgColor: Int = Color.TRANSPARENT,
    columns: Int = 2,
    horizontalSpacing: Int = 0,
    verticalSpacing: Int = 0,
    block: GridView.() -> Unit = {}
): GridView {

    return GridView(this).apply {

        layoutParams = ViewGroup.LayoutParams(width, height)

        setBackgroundColor(bgColor)

        numColumns = columns
        this.horizontalSpacing = horizontalSpacing
        this.verticalSpacing = verticalSpacing

        adapter = ktAdapter(items) {
            getView { item, position ->
                ktTextView(
                    text = item.toString(),
                    size = textSize,
                    color = textColor,
                    bgColor = itemBgColor,
                    width = MATCH_PARENT
                )
            }
        }

        block()
    }
}

fun <T> Context.ktSpinner(
    items: List<T> = emptyList(),
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    textSize: Float = 16f,
    textColor: Int = Color.BLACK,
    itemBgColor: Int = Color.TRANSPARENT,
    dropWidth: Int = WRAP_CONTENT,
    mode: Int = Spinner.MODE_DROPDOWN,
    block: Spinner.() -> Unit = {}
): Spinner {

    return Spinner(this, mode).apply {

        layoutParams = ViewGroup.LayoutParams(width, height)

        setBackgroundColor(bgColor)

        this.dropDownWidth = dropWidth

        adapter = ktAdapter(items) {
            getView { item, position ->
                ktTextView(
                    text = item.toString(),
                    size = textSize,
                    color = textColor,
                    bgColor = itemBgColor,
                    width = MATCH_PARENT
                )
            }

            getDropDownView { item, position ->
                ktTextView(
                    text = item.toString(),
                    size = textSize,
                    color = textColor,
                    bgColor = itemBgColor,
                    width = MATCH_PARENT
                )
            }
        }

        block()
    }
}

fun <G, C> Context.ktExpandableListView(
    groups: List<G> = emptyList(),
    children: List<List<C>> = emptyList(),
    width: Int = MATCH_PARENT,
    height: Int = WRAP_CONTENT,
    bgColor: Int = Color.TRANSPARENT,
    groupTextSize: Float = 18f,
    groupTextColor: Int = Color.BLACK,
    groupBgColor: Int = Color.LTGRAY,
    childTextSize: Float = 16f,
    childTextColor: Int = Color.BLACK,
    childBgColor: Int = Color.WHITE,
    block: ExpandableListView.() -> Unit = {}
): ExpandableListView {

    return ExpandableListView(this).apply {

        layoutParams = ViewGroup.LayoutParams(width, height)

        setBackgroundColor(bgColor)

        setAdapter(
            ktExpandableAdapter(
                groups = groups,
                children = children
            ) {
                getGroupView { group, position ->
                    ktTextView(
                        text = group.toString(),
                        size = groupTextSize,
                        color = groupTextColor,
                        bgColor = groupBgColor,
                        width = MATCH_PARENT
                    )
                }

                getChildView { group, child, gp, cp ->
                    ktTextView(
                        text = child.toString(),
                        size = childTextSize,
                        color = childTextColor,
                        bgColor = childBgColor,
                        width = MATCH_PARENT
                    )
                }
            }
        )

        block()
    }
}
