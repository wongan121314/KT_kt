@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.*

// Basic widgets
typealias KtTextView = android.widget.TextView
typealias KtEditText = android.widget.EditText
typealias KtButton = android.widget.Button
typealias KtImageButton = android.widget.ImageButton
typealias KtImageView = android.widget.ImageView

// Selection widgets
typealias KtCheckBox = android.widget.CheckBox
typealias KtRadioButton = android.widget.RadioButton
typealias KtRadioGroup = android.widget.RadioGroup
typealias KtSwitch = android.widget.Switch
typealias KtToggleButton = android.widget.ToggleButton
typealias KtSpinner = android.widget.Spinner
typealias KtAutoCompleteTextView = android.widget.AutoCompleteTextView
typealias KtMultiAutoCompleteTextView = android.widget.MultiAutoCompleteTextView

// Layouts
typealias KtLinearLayout = android.widget.LinearLayout
typealias KtFrameLayout = android.widget.FrameLayout
typealias KtRelativeLayout = android.widget.RelativeLayout
typealias KtTableLayout = android.widget.TableLayout
typealias KtGridLayout = android.widget.GridLayout
typealias KtScrollView = android.widget.ScrollView
typealias KtHorizontalScrollView = android.widget.HorizontalScrollView

// Lists and grids
typealias KtListView = android.widget.ListView
typealias KtGridView = android.widget.GridView
typealias KtExpandableListView = android.widget.ExpandableListView

// Progress and rating
typealias KtProgressBar = android.widget.ProgressBar
typealias KtSeekBar = android.widget.SeekBar
typealias KtRatingBar = android.widget.RatingBar

// Date and time
typealias KtDatePicker = android.widget.DatePicker
typealias KtTimePicker = android.widget.TimePicker
typealias KtCalendarView = android.widget.CalendarView

// Other widgets
typealias KtWebView = android.webkit.WebView
typealias KtVideoView = android.widget.VideoView
typealias KtSearchView = android.widget.SearchView

//---

fun Context.ktButton(
    block: Button.() -> Unit = {}
): Button {
    return Button(this).apply(block)
}

fun Context.ktButton(
    text: String,
    block: Button.() -> Unit = {}
): Button {
    return Button(this).apply {
        this.text = text
        block()
    }
}

fun Context.ktLinearLayout(
    block: LinearLayout.() -> Unit = {}
): LinearLayout {

    return LinearLayout(this).apply(block)
}

fun Context.ktCardLayout(
    bgColor: Int = Color.WHITE,
    corner: Number = 18,
    padding: Number = 18,
    block: LinearLayout.() -> Unit = {}
): LinearLayout {
    return LinearLayout(this).apply {
        orientation = 1
        setPadding(padding.dp, padding.dp, padding.dp, padding.dp)
        background = KtShape {
            setColor(bgColor)
            setCornerRadius(corner.dp.toFloat())
        }
        elevation = 2.dp.toFloat()
        block()
    }
}

fun Context.ktRowLayout(block: LinearLayout.() -> Unit = {}) = ktLinearLayout {
    orientation = 0
    block()
}

fun Context.ktColumnLayout(block: LinearLayout.() -> Unit = {}) = ktLinearLayout {
    orientation = 1
    block()
}

fun Context.ktGap(h: Number = 12) = ktSpace { minimumHeight = h.dp }

fun Context.ktLabelText(text: String, color: Int, size: Number = 11) = ktTextView {
    this.text = text
    textSize = size.toFloat()
    setTextColor(color)
}

fun Context.ktDivider(c: Int = KtColor.rgb(230, 230, 230)) = ktView { setBackgroundColor(c); layoutParams = LinearLayout.LayoutParams(-1, 1.dp) }

fun Context.KtFrameLayout(
    block: FrameLayout.() -> Unit = {}
): FrameLayout {
    return FrameLayout(this).apply(block)
}

fun Context.ktRelativeLayout(
    block: RelativeLayout.() -> Unit = {}
): RelativeLayout {
    return RelativeLayout(this).apply(block)
}

fun Context.ktAbsoluteLayout(
    block: AbsoluteLayout.() -> Unit = {}
): AbsoluteLayout {
    return AbsoluteLayout(this).apply(block)
}

fun Context.ktTableLayout(
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

fun Context.ktScrollView(
    block: ScrollView.() -> Unit = {}
): ScrollView {
    return ScrollView(this).apply(block)
}

fun Context.ktHorizontalScrollView(
    block: HorizontalScrollView.() -> Unit = {}
): HorizontalScrollView {
    return HorizontalScrollView(this).apply(block)
}

fun Context.ktTextView(
    block: TextView.() -> Unit = {}
): TextView {
    return TextView(this).apply(block)
}

// With text
fun Context.ktTextView(
    text: String,
    block: TextView.() -> Unit = {}
): TextView {
    return TextView(this).apply {
        this.text = text
        block()
    }
}

fun Context.ktEditText(
    block: EditText.() -> Unit = {}
): EditText {
    return EditText(this).apply(block)
}

fun Context.ktImageView(
    block: ImageView.() -> Unit = {}
): ImageView {
    return ImageView(this).apply(block)
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

fun Context.ktRatingBar(
    block: RatingBar.() -> Unit = {}
): RatingBar {
    return RatingBar(this).apply(block)
}

fun Context.ktView(
    block: View.() -> Unit = {}
): View {
    return View  (this).apply(block)
}

fun Context.ktVideoView(
    block: VideoView.() -> Unit = {}
): VideoView {
    return VideoView  (this).apply(block)
}

fun Context.ktSpace(
    block: Space.() -> Unit = {}
): Space {
    return Space  (this).apply(block)
}

fun Context.ktRadioGroup(
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

fun Context.ktRadioButton(
    block: RadioButton.() -> Unit = {}
): RadioButton {
    return RadioButton  (this).apply(block)
}

fun Context.ktImageButton(
    block: ImageButton.() -> Unit = {}
): ImageButton {
    return ImageButton  (this).apply(block)
}

fun Context.ktImageButton(
    imageRes: Int,
    block: ImageButton.() -> Unit = {}
): ImageButton {
    return ImageButton(this).apply {
        setImageResource(imageRes)
        block()
    }
}

fun Context.ktMultiAutoCompleteTextView(
    block: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView =
MultiAutoCompleteTextView(this).apply(block)

fun <T> Context.ktListView(
    items: List<T>,
    itemView: Context.(T) -> View
): ListView {
    return ListView(this).apply {
        adapter = object : ArrayAdapter<T>(this@ktListView, 0, items) {
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

fun <T> Context.ktSpinner(
    items: List<T>,
    itemView: Context.(T) -> View
): Spinner {
    return Spinner(this).apply {
        adapter = object : ArrayAdapter<T>(this@ktSpinner, 0, items) {

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

fun <T> Context.ktRecycleSpinner(
    items: List<T>,
    itemView: Context.(T, View?) -> View
): Spinner {
    return Spinner(this).apply {
        adapter = object : ArrayAdapter<T>(this@ktRecycleSpinner, 0, items) {

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

fun <T> Context.ktGridView(
    items: List<T>,
    numColumns: Int = 2,
    itemView: Context.(T) -> View
): GridView {
    return GridView(this).apply {
        this.numColumns = numColumns
        adapter = object : ArrayAdapter<T>(this@ktGridView, 0, items) {
            override fun getView(pos: Int, cv: View?, parent: ViewGroup): View {
                return itemView(context, getItem(pos)!!)
            }
        }
    }
}

fun <T> Context.ktRecycleGridView(
    items: List<T>,
    numColumns: Int = 2,
    itemView: Context.(T, View?) -> View
): GridView {
    return GridView(this).apply {
        this.numColumns = numColumns
        adapter = object : ArrayAdapter<T>(this@ktRecycleGridView, 0, items) {
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

fun <G, C> Context.ktExpandableListView(
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
                    return scope.parentView!!.invoke(this@ktExpandableListView, pData[gPos], isExpanded)
                }
                override fun getChildView(gPos: Int, cPos: Int, isLastChild: Boolean, cv: View?, parent: ViewGroup): View {
                    return scope.childView!!.invoke(this@ktExpandableListView, cData[gPos][cPos])
                }
        })
    }
}
//---
