@file:Suppress("ALL", "package.mismatch")

package com.KT_kt

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.SearchView

typealias KtButton = android.widget.Button
typealias KtImageButton = android.widget.ImageButton
typealias KtCalendarView = android.widget.CalendarView

typealias KtCheckBox = android.widget.CheckBox
typealias KtRadioButton = android.widget.RadioButton
typealias KtRadioGroup = android.widget.RadioGroup
typealias KtSwitch = android.widget.Switch
typealias KtToggleButton = android.widget.ToggleButton
typealias KtLinearLayout = android.widget.LinearLayout
typealias KtColumnLayout = android.widget.LinearLayout
typealias KtFrameLayout = android.widget.FrameLayout
typealias KtRelativeLayout = android.widget.RelativeLayout
typealias KtAbsoluteLayout = android.widget.AbsoluteLayout
typealias KtGridLayout = android.widget.GridLayout
typealias KtTableLayout = android.widget.TableLayout
typealias KtTableRow = android.widget.TableRow
typealias KtScrollView = android.widget.ScrollView
typealias KtHorizontalScrollView = android.widget.HorizontalScrollView
typealias KtListView = android.widget.ListView
typealias KtGridView = android.widget.GridView
typealias KtExpandableListView = android.widget.ExpandableListView
typealias KtProgressBar = android.widget.ProgressBar
typealias KtSeekBar = android.widget.SeekBar
typealias KtRatingBar = android.widget.RatingBar
typealias KtDatePicker = android.widget.DatePicker
typealias KtWebView = android.webkit.WebView
typealias KtVideoView = android.widget.VideoView
typealias KtSearchView = android.widget.SearchView// Text widgets
typealias KtTextView = android.widget.TextView
typealias KtCheckedTextView = android.widget.CheckedTextView
typealias KtEditText = android.widget.EditText
typealias KtAutoCompleteTextView = android.widget.AutoCompleteTextView
typealias KtMultiAutoCompleteTextView = android.widget.MultiAutoCompleteTextView
typealias KtChronometer = android.widget.Chronometer
typealias KtTextClock = android.widget.TextClock

typealias KtNumberPicker = android.widget.NumberPicker
typealias KtAnalogClock = android.widget.AnalogClock
typealias KtImageView = android.widget.ImageView

typealias KtAlertDialog = android.app.AlertDialog
typealias KtDatePickerDialog = android.app.DatePickerDialog
typealias KtTimePickerDialog = android.app.TimePickerDialog
// ==============================
// VIEW VISIBILITY
// ==============================

const val VISIBLE = View.VISIBLE
const val INVISIBLE = View.INVISIBLE
const val GONE = View.GONE

// ==============================
// LAYOUT DIMENSIONS
// ==============================

const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

// ==============================
// ORIENTATION
// ==============================

const val HORIZONTAL = LinearLayout.HORIZONTAL
const val VERTICAL = LinearLayout.VERTICAL

// ==============================
// GRAVITY
// ==============================

const val NO_GRAVITY = Gravity.NO_GRAVITY
const val CENTER = Gravity.CENTER
const val CENTER_HORIZONTAL = Gravity.CENTER_HORIZONTAL
const val CENTER_VERTICAL = Gravity.CENTER_VERTICAL

const val TOP = Gravity.TOP
const val BOTTOM = Gravity.BOTTOM
const val LEFT = Gravity.LEFT
const val RIGHT = Gravity.RIGHT
const val START = Gravity.START
const val END = Gravity.END

const val FILL = Gravity.FILL
const val FILL_HORIZONTAL = Gravity.FILL_HORIZONTAL
const val FILL_VERTICAL = Gravity.FILL_VERTICAL

// ==============================
// TEXT STYLE
// ==============================

const val NORMAL = Typeface.NORMAL
const val BOLD = Typeface.BOLD
const val ITALIC = Typeface.ITALIC
const val BOLD_ITALIC = Typeface.BOLD_ITALIC

// ==============================
// TEXT ALIGNMENT
// ==============================

const val TEXT_START = View.TEXT_ALIGNMENT_VIEW_START
const val TEXT_CENTER = View.TEXT_ALIGNMENT_CENTER
const val TEXT_END = View.TEXT_ALIGNMENT_VIEW_END
const val TEXT_GRAVITY = View.TEXT_ALIGNMENT_GRAVITY

// ==============================
// LAYOUT DIRECTION
// ==============================

const val LAYOUT_DIRECTION_LTR = View.LAYOUT_DIRECTION_LTR
const val LAYOUT_DIRECTION_RTL = View.LAYOUT_DIRECTION_RTL
const val LAYOUT_DIRECTION_INHERIT = View.LAYOUT_DIRECTION_INHERIT
const val LAYOUT_DIRECTION_LOCALE = View.LAYOUT_DIRECTION_LOCALE

// ==============================
// GRID LAYOUT
// ==============================

const val GRID_HORIZONTAL = GridLayout.HORIZONTAL
const val GRID_VERTICAL = GridLayout.VERTICAL

const val ALIGN_BOUNDS = GridLayout.ALIGN_BOUNDS
const val ALIGN_MARGINS = GridLayout.ALIGN_MARGINS

// ==============================
// OVER SCROLL
// ==============================

const val OVER_SCROLL_ALWAYS = View.OVER_SCROLL_ALWAYS
const val OVER_SCROLL_NEVER = View.OVER_SCROLL_NEVER
const val OVER_SCROLL_IF_CONTENT_SCROLLS =
View.OVER_SCROLL_IF_CONTENT_SCROLLS

// ==============================
// LAYER TYPE
// ==============================

const val LAYER_NONE = View.LAYER_TYPE_NONE
const val LAYER_SOFTWARE = View.LAYER_TYPE_SOFTWARE
const val LAYER_HARDWARE = View.LAYER_TYPE_HARDWARE

// ==============================
// SCROLLBAR STYLE
// ==============================

const val SCROLL_INSIDE_OVERLAY = View.SCROLLBARS_INSIDE_OVERLAY
const val SCROLL_OUTSIDE_OVERLAY = View.SCROLLBARS_OUTSIDE_OVERLAY
const val SCROLL_INSIDE_INSET = View.SCROLLBARS_INSIDE_INSET
const val SCROLL_OUTSIDE_INSET = View.SCROLLBARS_OUTSIDE_INSET

// ==============================
// ACCESSIBILITY
// ==============================

const val ACCESSIBILITY_AUTO = View.ACCESSIBILITY_LIVE_REGION_NONE
const val ACCESSIBILITY_POLITE = View.ACCESSIBILITY_LIVE_REGION_POLITE
const val ACCESSIBILITY_ASSERTIVE = View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE

// ==============================
// AUTOFILL
// ==============================

const val AUTOFILL_AUTO = View.AUTOFILL_TYPE_NONE
const val AUTOFILL_TEXT = View.AUTOFILL_TYPE_TEXT
const val AUTOFILL_TOGGLE = View.AUTOFILL_TYPE_TOGGLE
const val AUTOFILL_LIST = View.AUTOFILL_TYPE_LIST
const val AUTOFILL_DATE = View.AUTOFILL_TYPE_DATE
