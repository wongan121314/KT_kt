# KtViewAliases

[<-- Back](../home.md)

`KtViewAliases` provides Kotlin type aliases and constants for commonly used Android UI components and properties.

It allows you to use shorter, library-specific names instead of repeatedly writing Android class names and constants.

The file contains two main sections:

1. View type aliases
2. UI constants

## 1. View Type Aliases

A type alias gives an existing Kotlin or Java type another name.

For example:

```kotlin
typealias KtTextView = android.widget.TextView
```

This allows you to use `KtTextView` instead of `android.widget.TextView`.

```kotlin
val text = KtTextView(this)

text.text = "Hello World"
text.textSize = 20f
```

`KtTextView` is not a new class. It is simply another name for Android's native `TextView`.

### Available Type Aliases

#### Buttons and Selection Widgets

| Alias            | Android class  |
| ---------------- | -------------- |
| `KtButton`       | `Button`       |
| `KtImageButton`  | `ImageButton`  |
| `KtCheckBox`     | `CheckBox`     |
| `KtRadioButton`  | `RadioButton`  |
| `KtRadioGroup`   | `RadioGroup`   |
| `KtSwitch`       | `Switch`       |
| `KtToggleButton` | `ToggleButton` |

#### Layouts

| Alias                    | Android class          |
| ------------------------ | ---------------------- |
| `KtLinearLayout`         | `LinearLayout`         |
| `KtColumnLayout`         | `LinearLayout`         |
| `KtFrameLayout`          | `FrameLayout`          |
| `KtRelativeLayout`       | `RelativeLayout`       |
| `KtAbsoluteLayout`       | `AbsoluteLayout`       |
| `KtGridLayout`           | `GridLayout`           |
| `KtTableLayout`          | `TableLayout`          |
| `KtTableRow`             | `TableRow`             |
| `KtScrollView`           | `ScrollView`           |
| `KtHorizontalScrollView` | `HorizontalScrollView` |

**Note:** `KtColumnLayout` and `KtLinearLayout` refer to the same Android class. The alias itself does not automatically set the layout orientation to vertical.

#### Collection Widgets

| Alias                  | Android class        |
| ---------------------- | -------------------- |
| `KtListView`           | `ListView`           |
| `KtGridView`           | `GridView`           |
| `KtExpandableListView` | `ExpandableListView` |

#### Text Widgets

| Alias                         | Android class               |
| ----------------------------- | --------------------------- |
| `KtTextView`                  | `TextView`                  |
| `KtCheckedTextView`           | `CheckedTextView`           |
| `KtEditText`                  | `EditText`                  |
| `KtAutoCompleteTextView`      | `AutoCompleteTextView`      |
| `KtMultiAutoCompleteTextView` | `MultiAutoCompleteTextView` |
| `KtChronometer`               | `Chronometer`               |
| `KtTextClock`                 | `TextClock`                 |

#### Image and Media Widgets

| Alias         | Android class |
| ------------- | ------------- |
| `KtImageView` | `ImageView`   |
| `KtVideoView` | `VideoView`   |
| `KtWebView`   | `WebView`     |

#### Progress and Rating Widgets

| Alias           | Android class |
| --------------- | ------------- |
| `KtProgressBar` | `ProgressBar` |
| `KtSeekBar`     | `SeekBar`     |
| `KtRatingBar`   | `RatingBar`   |

#### Other Widgets

| Alias            | Android class  |
| ---------------- | -------------- |
| `KtCalendarView` | `CalendarView` |
| `KtDatePicker`   | `DatePicker`   |
| `KtNumberPicker` | `NumberPicker` |
| `KtAnalogClock`  | `AnalogClock`  |
| `KtSearchView`   | `SearchView`   |

#### Dialogs

| Alias                | Android class      |
| -------------------- | ------------------ |
| `KtAlertDialog`      | `AlertDialog`      |
| `KtDatePickerDialog` | `DatePickerDialog` |
| `KtTimePickerDialog` | `TimePickerDialog` |

## 2. UI Constants

The file also defines shorter names for Android's commonly used UI constants.

These constants can be used when configuring views and layouts without repeatedly referencing their original Android classes.

### View Visibility

Controls whether a view is displayed.

| Constant    | Value            |
| ----------- | ---------------- |
| `VISIBLE`   | `View.VISIBLE`   |
| `INVISIBLE` | `View.INVISIBLE` |
| `GONE`      | `View.GONE`      |

Example:

```kotlin
text.visibility = GONE
text.visibility = VISIBLE
```

- `VISIBLE`: The view is displayed.
- `INVISIBLE`: The view is hidden but still occupies layout space.
- `GONE`: The view is hidden and does not occupy layout space.

### Layout Dimensions

Defines how a view should occupy available layout space.

| Constant       | Value                                 |
| -------------- | ------------------------------------- |
| `MATCH_PARENT` | `ViewGroup.LayoutParams.MATCH_PARENT` |
| `WRAP_CONTENT` | `ViewGroup.LayoutParams.WRAP_CONTENT` |
| `FULL`         | `MATCH_PARENT`                        |
| `AUTO`         | `WRAP_CONTENT`                        |

Example:

```kotlin
val params = LinearLayout.LayoutParams(
    MATCH_PARENT,
    WRAP_CONTENT
)
```

`FULL` and `AUTO` are alternative names for `MATCH_PARENT` and `WRAP_CONTENT`, respectively.

### Orientation

Controls the direction in which children are arranged inside a `LinearLayout`.

| Constant     | Value                     |
| ------------ | ------------------------- |
| `HORIZONTAL` | `LinearLayout.HORIZONTAL` |
| `VERTICAL`   | `LinearLayout.VERTICAL`   |

Example:

```kotlin
val layout = KtLinearLayout(this)

layout.orientation = VERTICAL
```

### Gravity

Controls how content or child views are positioned within a view or layout.

| Constant            | Value                       |
| ------------------- | --------------------------- |
| `NO_GRAVITY`        | `Gravity.NO_GRAVITY`        |
| `CENTER`            | `Gravity.CENTER`            |
| `CENTER_HORIZONTAL` | `Gravity.CENTER_HORIZONTAL` |
| `CENTER_VERTICAL`   | `Gravity.CENTER_VERTICAL`   |
| `TOP`               | `Gravity.TOP`               |
| `BOTTOM`            | `Gravity.BOTTOM`            |
| `LEFT`              | `Gravity.LEFT`              |
| `RIGHT`             | `Gravity.RIGHT`             |
| `START`             | `Gravity.START`             |
| `END`               | `Gravity.END`               |
| `FILL`              | `Gravity.FILL`              |
| `FILL_HORIZONTAL`   | `Gravity.FILL_HORIZONTAL`   |
| `FILL_VERTICAL`     | `Gravity.FILL_VERTICAL`     |

Example:

```kotlin
text.gravity = CENTER
```

### Text Style

Defines the style of text displayed by a widget.

| Constant      | Value                  |
| ------------- | ---------------------- |
| `NORMAL`      | `Typeface.NORMAL`      |
| `BOLD`        | `Typeface.BOLD`        |
| `ITALIC`      | `Typeface.ITALIC`      |
| `BOLD_ITALIC` | `Typeface.BOLD_ITALIC` |

Example:

```kotlin
text.setTypeface(null, BOLD)
```

### Text Alignment

Controls how text is aligned within a view.

| Constant       | Value                            |
| -------------- | -------------------------------- |
| `TEXT_START`   | `View.TEXT_ALIGNMENT_VIEW_START` |
| `TEXT_CENTER`  | `View.TEXT_ALIGNMENT_CENTER`     |
| `TEXT_END`     | `View.TEXT_ALIGNMENT_VIEW_END`   |
| `TEXT_GRAVITY` | `View.TEXT_ALIGNMENT_GRAVITY`    |

Example:

```kotlin
text.textAlignment = TEXT_CENTER
```

### Layout Direction

Controls the direction in which a view's layout is arranged.

| Constant                   | Value                           |
| -------------------------- | ------------------------------- |
| `LAYOUT_DIRECTION_LTR`     | `View.LAYOUT_DIRECTION_LTR`     |
| `LAYOUT_DIRECTION_RTL`     | `View.LAYOUT_DIRECTION_RTL`     |
| `LAYOUT_DIRECTION_INHERIT` | `View.LAYOUT_DIRECTION_INHERIT` |
| `LAYOUT_DIRECTION_LOCALE`  | `View.LAYOUT_DIRECTION_LOCALE`  |

Example:

```kotlin
layout.layoutDirection = LAYOUT_DIRECTION_LTR
```

### Grid Layout

Provides constants for configuring `GridLayout`.

| Constant          | Value                      |
| ----------------- | -------------------------- |
| `GRID_HORIZONTAL` | `GridLayout.HORIZONTAL`    |
| `GRID_VERTICAL`   | `GridLayout.VERTICAL`      |
| `ALIGN_BOUNDS`    | `GridLayout.ALIGN_BOUNDS`  |
| `ALIGN_MARGINS`   | `GridLayout.ALIGN_MARGINS` |

### Overscroll

Controls how a view behaves when a user scrolls beyond its content boundaries.

| Constant                         | Value                                 |
| -------------------------------- | ------------------------------------- |
| `OVER_SCROLL_ALWAYS`             | `View.OVER_SCROLL_ALWAYS`             |
| `OVER_SCROLL_NEVER`              | `View.OVER_SCROLL_NEVER`              |
| `OVER_SCROLL_IF_CONTENT_SCROLLS` | `View.OVER_SCROLL_IF_CONTENT_SCROLLS` |

### Layer Type

Controls the rendering layer used by a view.

| Constant         | Value                      |
| ---------------- | -------------------------- |
| `LAYER_NONE`     | `View.LAYER_TYPE_NONE`     |
| `LAYER_SOFTWARE` | `View.LAYER_TYPE_SOFTWARE` |
| `LAYER_HARDWARE` | `View.LAYER_TYPE_HARDWARE` |

### Scrollbar Style

Controls how scrollbars are positioned relative to the view's content.

| Constant                 | Value                             |
| ------------------------ | --------------------------------- |
| `SCROLL_INSIDE_OVERLAY`  | `View.SCROLLBARS_INSIDE_OVERLAY`  |
| `SCROLL_OUTSIDE_OVERLAY` | `View.SCROLLBARS_OUTSIDE_OVERLAY` |
| `SCROLL_INSIDE_INSET`    | `View.SCROLLBARS_INSIDE_INSET`    |
| `SCROLL_OUTSIDE_INSET`   | `View.SCROLLBARS_OUTSIDE_INSET`   |

### Accessibility

Provides constants for configuring a view's accessibility live-region behaviour.

| Constant                  | Value                                      |
| ------------------------- | ------------------------------------------ |
| `ACCESSIBILITY_AUTO`      | `View.ACCESSIBILITY_LIVE_REGION_NONE`      |
| `ACCESSIBILITY_POLITE`    | `View.ACCESSIBILITY_LIVE_REGION_POLITE`    |
| `ACCESSIBILITY_ASSERTIVE` | `View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE` |

These values control how changes to a view are announced by accessibility services such as screen readers.

### Autofill

Provides constants for configuring the type of data a view can receive through Android autofill.

| Constant          | Value                       |
| ----------------- | --------------------------- |
| `AUTOFILL_AUTO`   | `View.AUTOFILL_TYPE_NONE`   |
| `AUTOFILL_TEXT`   | `View.AUTOFILL_TYPE_TEXT`   |
| `AUTOFILL_TOGGLE` | `View.AUTOFILL_TYPE_TOGGLE` |
| `AUTOFILL_LIST`   | `View.AUTOFILL_TYPE_LIST`   |
| `AUTOFILL_DATE`   | `View.AUTOFILL_TYPE_DATE`   |

## Important Notes

- Type aliases do not create new Android widgets. They refer to existing Android classes.
- All native Android methods, properties, and behaviours remain available through the aliases.
- Constants retain the values defined by Android.
- `KtColumnLayout` is only an alias for `LinearLayout`; it does not set `orientation = VERTICAL` automatically.
- `FULL` and `AUTO` are alternative names for the standard layout dimensions.
- Some aliases refer to deprecated Android classes, such as `AbsoluteLayout`. Their availability does not mean they are recommended for new layouts.

