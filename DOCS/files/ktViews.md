[<-- Back](../home.md)

# KtViews

`KtViews` provides a collection of Kotlin extension functions for creating Android views using a `Context`.

Instead of manually constructing Android widgets, you can use functions such as `ktTextView()`, `ktButton()`, `ktColumnLayout()`, and `ktEditText()`.

Each function creates an Android view, applies its default properties, executes an optional configuration block, and returns the created view.

## 1. Basic Usage

Most functions follow this pattern:

```kotlin
val text = ktTextView("Hello World") {
    textSize = 20f
    setTextColor(Color.BLUE)
}
```

The configuration block allows you to access the native Android view properties and methods directly.

Because these are `Context` extension functions, they can be called from an Activity or another Context:

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        setContentView(
            ktTextView("Hello World")
        )
    }
}
```

## 2. Buttons

These functions create Android button widgets.

| Function           | Description                |
| ------------------ | -------------------------- |
| `ktButton()`       | Creates a standard Button. |
| `ktToggleButton()` | Creates a ToggleButton.    |
| `ktRadioButton()`  | Creates a RadioButton.     |
| `ktImageButton()`  | Creates an ImageButton.    |

### Creating a Button

```kotlin
val button = ktButton(
    text = "Click Me",
    size = 18f,
    color = Color.WHITE,
    bgColor = Color.BLUE
) {
    setOnClickListener {
        // Handle click
    }
}
```

### Creating a ToggleButton

```kotlin
val toggle = ktToggleButton("Toggle") {
    setOnCheckedChangeListener { _, isChecked ->
        // Check the toggle state
    }
}
```

### Creating a RadioButton

```kotlin
val radio = ktRadioButton("Male") {
    isChecked = true
}
```

### Creating an ImageButton

```kotlin
val imageButton = ktImageButton(
    imageRes = R.drawable.ic_launcher_foreground
) {
    setOnClickListener {
        // Handle click
    }
}
```

## 3. Layouts

Layout functions create Android `ViewGroup` containers. You can add child views inside their configuration blocks using Android's `addView()` method.

| Function                   | Description                                                    |
| -------------------------- | -------------------------------------------------------------- |
| `ktLinearLayout()`         | Creates a LinearLayout.                                        |
| `ktRowLayout()`            | Creates a horizontal LinearLayout.                             |
| `ktColumnLayout()`         | Creates a vertical LinearLayout.                               |
| `ktCardLayout()`           | Creates a FrameLayout with a rounded background and elevation. |
| `ktFrameLayout()`          | Creates a FrameLayout.                                         |
| `ktRelativeLayout()`       | Creates a RelativeLayout.                                      |
| `ktAbsoluteLayout()`       | Creates an AbsoluteLayout.                                     |
| `ktTableLayout()`          | Creates a TableLayout.                                         |
| `ktTableRow()`             | Creates a TableRow.                                            |
| `ktGridLayout()`           | Creates a GridLayout.                                          |
| `ktScrollView()`           | Creates a vertical ScrollView.                                 |
| `ktHorizontalScrollView()` | Creates a HorizontalScrollView.                                |

### Linear Layout

Use `ktColumnLayout()` to arrange child views vertically.

```kotlin
val column = ktColumnLayout {
    addView(ktTextView("First item"))
    addView(ktTextView("Second item"))
    addView(ktButton("Click Me"))
}
```

Use `ktRowLayout()` to arrange child views horizontally.

```kotlin
val row = ktRowLayout {
    addView(ktButton("Yes"))
    addView(ktButton("No"))
}
```

You can also configure the underlying LinearLayout:

```kotlin
val layout = ktLinearLayout {
    orientation = LinearLayout.VERTICAL
    gravity = Gravity.CENTER
    addView(ktTextView("Welcome"))
}
```

### Card Layout

`ktCardLayout()` creates a FrameLayout with a rounded background, optional border, and elevation.

```kotlin
val card = ktCardLayout(
    bgColor = Color.WHITE,
    cornerRadius = 16f,
    elevation = 6f,
    strokeColor = Color.GRAY,
    strokeWidth = 1
) {
    addView(
        ktTextView("Card content")
    )
}
```

The `cornerRadius`, `elevation`, and `strokeWidth` values are converted from density-independent units (dp) to pixels internally.

### Scroll Views

Use `ktScrollView()` for vertical scrolling and `ktHorizontalScrollView()` for horizontal scrolling.

```kotlin
val scroll = ktScrollView {
    addView(
        ktColumnLayout {
            addView(ktTextView("Item 1"))
            addView(ktTextView("Item 2"))
            addView(ktTextView("Item 3"))
        }
    )
}
```

A ScrollView normally contains one direct child. Use a layout such as LinearLayout to hold multiple views.

## 4. Text Views

These functions create Android text-related widgets.

| Function              | Description                                             |
| --------------------- | ------------------------------------------------------- |
| `ktTextView()`        | Creates a TextView.                                     |
| `ktCheckedTextView()` | Creates a CheckedTextView.                              |
| `ktTextClock()`       | Creates a TextClock.                                    |
| `ktLabelText()`       | Creates a bold TextView with a default text size of 14. |
| `ktDivider()`         | Creates a horizontal divider view                       |