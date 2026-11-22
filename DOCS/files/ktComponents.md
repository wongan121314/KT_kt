[<-- Back](../home.md)

# KtExtensions

`KtComponents` provides general-purpose Kotlin extension functions and utility functions for Android development.

It simplifies common tasks such as converting dimensions, creating dialogs, managing view visibility, setting margins and padding, accessing views, running background tasks, and handling mutable state.

Unlike classes such as `KtAdapter` or `KtMediaPlayer`, this file contains several independent utilities that can be used throughout an Android application.

## 1. Unit Conversion

Android uses different units for displaying dimensions and text:

- **dp:** Density-independent pixels, commonly used for view dimensions.
- **sp:** Scale-independent pixels, commonly used for text sizes.
- **px:** Physical screen pixels.

This file provides three extension properties on `Number`.

| Property | Description                                  |
| -------- | -------------------------------------------- |
| `dp`     | Converts a number from dp to pixels.         |
| `sp`     | Converts a number from sp to scaled pixels.  |
| `px`     | Converts a number to an integer pixel value. |

### Examples

```kotlin
val width = 100.dp
val textSize = 18.sp
val height = 50.px
```

You can use them when configuring views:

```kotlin
ktTextView("Hello") {
    textSize = 18.sp
    setPadding(10.dp, 10.dp, 10.dp, 10.dp)
}
```

**Important:** `dp` and `sp` return pixel values, not the original units. The `px` property simply converts the number to an integer.

## 2. URI Conversion

| Function         | Description                            |
| ---------------- | -------------------------------------- |
| `String.toUri()` | Converts a String into an Android Uri. |

### Example

```kotlin
val uri = "https://example.com".toUri()
```

This is equivalent to:

```kotlin
val uri = Uri.parse("https://example.com")
```

The function only parses the string; it does not verify that the URI points to an existing resource.

## 3. ViewGroup Extensions

These functions simplify adding multiple views and refreshing a ListView.

| Function                            | Description                                                   |
| ----------------------------------- | ------------------------------------------------------------- |
| `ViewGroup.addViews()`              | Adds multiple child views to a ViewGroup.                     |
| `ListView.refresh()`                | Notifies a compatible adapter that its data may have changed. |
| `ListView.setOnItemClickListener()` | Provides simplified item-click callbacks.                     |

### Adding Multiple Views

Instead of calling `addView()` repeatedly, use `addViews()`:

```kotlin
val layout = ktColumnLayout()

layout.addViews(
    ktTextView("First"),
    ktTextView("Second"),
    ktButton("Submit")
)
```

### Refreshing a ListView

```kotlin
listView.refresh()
```

This calls `notifyDataSetChanged()` when the ListView's adapter is a `BaseAdapter`.

The function returns the same ListView, allowing method chaining.

### Listening for Item Clicks

There are two overloads.

**Receive the position and item ID:**

```kotlin
listView.setOnItemClickListener { position, id ->
    // Handle selected item
}
```

**Receive only the position:**

```kotlin
listView.setOnItemClickListener { position ->
    // Handle selected item
}
```

Both overloads simplify Android's native `AdapterView.OnItemClickListener`.

## 4. Toasts and Dialogs

This section provides functions for creating common Android messages and dialogs.

| Function                        | Description                                      |
| ------------------------------- | ------------------------------------------------ |
| `ktToast()`                     | Creates a Toast message.                         |
| `ktDialog(block)`               | Creates a standard Dialog.                       |
| `ktDialog(title, message, ...)` | Creates an AlertDialog.                          |
| `ktDateDialog()`                | Creates a DatePickerDialog.                      |
| `ktTimeDialog()`                | Creates a TimePickerDialog.                      |
| `ktProgressDialog()`            | Creates an AlertDialog containing a ProgressBar. |

### Toast

```kotlin
ktToast("Hello World").show()
```

You can specify the duration:

```kotlin
ktToast(
    message = "Operation completed",
    duration = Toast.LENGTH_LONG
).show()
```

The function returns a Toast object. It does not call `show()` automatically.

### Standard Dialog

```kotlin
val dialog = ktDialog {
    setTitle("My Dialog")
    setContentView(
        ktTextView("Dialog content")
    )
}

dialog.show()
```

The configuration block provides access to the native Android `Dialog`.

### AlertDialog

The second `ktDialog()` overload creates an AlertDialog using an `AlertDialog.Builder`.

```kotlin
val dialog = ktDialog(
    title = "Confirmation",
    message = "Do you want to continue?",
    cancelable = true
) {
    setPositiveButton("Yes") { _, _ ->
        // Confirm action
    }

    setNegativeButton("No") { _, _ ->
        // Cancel action
    }
}

dialog.show()
```

The title and message are only assigned when they are not empty.

### Date Dialog

```kotlin
val dialog = ktDateDialog(
    year = 2026,
    month = 10,
    day = 22
)

dialog.show()
```

The function accepts a callback intended to receive the selected year, month, and day. However, the current implementation does not invoke that callback when a date is selected. See the implementation notes below.

### Time Dialog

```kotlin
val dialog = ktTimeDialog(
    hour = 10,
    minute = 30,
    is24Hour = true
) { hour, minute ->
    // Handle selected time
}

dialog.show()
```

If the hour and minute are omitted, the function uses the current time.

### Progress Dialog

`ktProgressDialog()` creates an AlertDialog containing a ProgressBar.

```kotlin
val dialog = ktProgressDialog(
    title = "Loading",
    message = "Please wait...",
    horizontal = false,
    cancelable = false
)

dialog.show()
```

You can configure the ProgressBar directly:

```kotlin
val dialog = ktProgressDialog(
    horizontal = true
) {
    progress = 40
    max = 100
}
```

When `horizontal` is true, the ProgressBar uses the horizontal progress style and its maximum is initially set to 100.

The dialog is returned without being displayed automatically.

## 5. Drawable Utilities

Drawables are Android objects used to define visual backgrounds and other graphical elements.

This file provides three drawable factory functions.

| Function               | Description                                                                     |
| ---------------------- | ------------------------------------------------------------------------------- |
| `ktShapeDrawable()`    | Creates a ShapeDrawable using a rectangular shape.                              |
| `ktGradientDrawable()` | Creates a GradientDrawable with configurable shape, color, corners, and stroke. |
| `ktRippleDrawable()`   | Creates a RippleDrawable with a specified ripple color.                         |

### ShapeDrawable

```kotlin
val shape = ktShapeDrawable(
    color = Color.BLUE,
    style = Paint.Style.FILL,
    strokeWidth = 2f
)
```

The function creates a rectangular ShapeDrawable and configures its paint properties.

### GradientDrawable

```kotlin
val background = ktGradientDrawable(
    color = Color.BLUE,
    radius = 16f,
    strokeWidth = 2,
    strokeColor = Color.BLACK
)
```

You can also specify a different drawable shape:

```kotlin
val oval = ktGradientDrawable(
    color = Color.RED,
    shape = GradientDrawable.OVAL
)
```

The `radius` and `strokeWidth` values are passed directly to Android as pixel dimensions. They are not converted from dp.

### RippleDrawable

```kotlin
val ripple = ktRippleDrawable(
    rippleColor = Color.LTGRAY
)
```

You can optionally provide a content drawable and a mask drawable:

```kotlin
val ripple = ktRippleDrawable(
    rippleColor = Color.GRAY,
    content = background,
    mask = background
)
```

A RippleDrawable can be assigned as a view background:

```kotlin
ktButton("Click Me") {
    background = ripple
}
```

## 6. System Bar Configuration

These functions allow an Activity to configure the Android navigation bar and status bar.

| Function                 | Description                                                                  |
| ------------------------ | ---------------------------------------------------------------------------- |
| `Activity.ktNavBar()`    | Configures navigation bar visibility, icon appearance, and background color. |
| `Activity.KtStatusBar()` | Configures status bar visibility, icon appearance, and background color.     |

Both functions accept similar parameters:

| Parameter     | Description                                                   |
| ------------- | ------------------------------------------------------------- |
| `mode`        | `"show"` or `"hide"` controls bar visibility.                 |
| `color`       | Sets the bar background color when not transparent.           |
| `icons`       | `"light"` or `"dark"` controls the requested icon appearance. |
| `transparent` | Makes the system bar background transparent when true.        |

### Navigation Bar

```kotlin
ktNavBar(
    mode = "show",
    color = Color.BLACK,
    icons = "light",
    transparent = false
)
```

To hide the navigation bar:

```kotlin
ktNavBar(mode = "hide")
```

### Status Bar

```kotlin
KtStatusBar(
    mode = "show",
    color = Color.WHITE,
    icons = "dark"
)
```

To hide the status bar:

```kotlin
KtStatusBar(mode = "hide")
```

These are Activity extension functions, so call them from an Activity context.

**Note:** The implementation uses Android's older `systemUiVisibility` flags. These APIs are deprecated in modern Android development, and newer window inset APIs are generally preferred.

## 7. SoundPool

`ktSoundPool()` creates an Android SoundPool instance for playing short sound effects.

| Function                | Description                                                                     |
| ----------------------- | ------------------------------------------------------------------------------- |
| `Context.ktSoundPool()` | Creates a SoundPool with a configurable maximum number of simultaneous streams. |

### Example

```kotlin
val soundPool = ktSoundPool(
    maxStreams = 5
)
```

You can then use the native SoundPool API to load and play sound resources.

```kotlin
val soundId = soundPool.load(
    this,
    R.raw.click,
    1
)
```

SoundPool loads sound resources asynchronously. You should wait for the load-completion callback before attempting to play a newly loaded sound.

Remember to release the SoundPool when it is no longer needed:

```kotlin
soundPool.release()
```

## 8. Background Thread Execution

`ktThread()` executes a task on a background thread and returns its result on the main thread.

| Function     | Description                                                                                |
| ------------ | ------------------------------------------------------------------------------------------ |
| `ktThread()` | Runs a task in a background thread and delivers its Result through a main-thread callback. |

### Example

```kotlin
ktThread(
    work = {
        // Background work
        100 + 200
    },
    onDone = { result ->

        result.onSuccess { value ->
            ktToast("Result: $value").show()
        }

        result.onFailure { error ->
            ktToast(error.message ?: "An error occurred").show()
        }
    }
)
```

The function has two parameters:

- `work`: The task to execute in the background.
- `onDone`: A callback that receives a Kotlin `Result<T>` after the task completes.

### How It Works

1. The task is submitted to a shared thread pool containing four worker threads.
2. `runCatching` executes the task and captures either its result or its exception.
3. The result is posted to the Android main thread using a Handler.
4. The `onDone` callback is executed on the main thread.

The function returns a `Future`, which represents the submitted background task.

```kotlin
val task = ktThread(
    work = {
        // Perform work
        "Completed"
    },
    onDone = { result ->
        // Handle result
    }
)
```

You can use the returned Future to inspect or cancel the submitted task:

```kotlin
task.cancel(true)
```

The thread pool is shared internally. The source does not provide a function for shutting it down.

## 9. View IDs, Tags, and Lookup

Android views can be identified using either an integer ID or a tag.

This file provides extensions for assigning and retrieving views.

| Function                    | Description                                          |
| --------------------------- | ---------------------------------------------------- |
| `View.setId()`              | Assigns an integer ID to a view. Deprecated.         |
| `View.setTag()`             | Assigns a String tag to a view. Deprecated.          |
| `ViewGroup.getId()`         | Finds a child view using its integer ID. Deprecated. |
| `ViewGroup.findViewByTag()` | Finds a child view using its tag. Deprecated.        |
| `Activity.getViewByTag()`   | Finds a view by tag from the Activity content root.  |
| `Activity.getViewById()`    | Finds a view by integer ID from the Activity.        |

### Assigning IDs

Use the native Android property:

```kotlin
val button = ktButton("Click") {
    id = 100
}
```

The older extension also works:

```kotlin
val button = ktButton("Click").setId(100)
```

However, `setId()` is deprecated in this library because Android already provides the `id` property.

### Assigning Tags

```kotlin
val text = ktTextView("Hello") {
    tag = "message"
}
```

The deprecated extension is:

```kotlin
val text = ktTextView("Hello").setTag("message")
```

### Finding Views by ID

From an Activity:

```kotlin
val button = getViewById<Button>(100)
```

This returns the matching view or `null` if no matching view is found.

### Finding Views by Tag

```kotlin
val text = getViewByTag<TextView>("message")
```

This searches from the Activity's content root.

The generic type parameter specifies the expected view type.

## 10. URI File Name

| Function                    | Description                                                           |
| --------------------------- | --------------------------------------------------------------------- |
| `Context.ktUriTOFileName()` | Attempts to retrieve the display name of a file represented by a Uri. |

### Example

```kotlin
val fileName = ktUriTOFileName(uri)

if (fileName != null) {
    ktToast(fileName).show()
}
```

### How It Works

The function:

1. Uses `contentResolver.query()` to query the supplied Uri.
2. Requests the `OpenableColumns.DISPLAY_NAME` column.
3. Moves to the first result.
4. Retrieves the display name if the column exists.
5. Returns `null` if the name cannot be retrieved.

The Cursor is closed automatically using `use`.

The function does not guarantee that every Uri represents a readable file or that a display name is available.

## 11. State Management with useState

`useState` is a generic class for storing a mutable value and notifying registered listeners when that value changes.

| Member   | Description                                       |
| -------- | ------------------------------------------------- |
| `value`  | Stores the current state value.                   |
| `bind()` | Registers a listener that receives state updates. |

### Creating State

```kotlin
val name = useState("John")
```

The class accepts any type:

```kotlin
val age = useState(20)
val isLoggedIn = useState(false)
```

### Reading and Updating State

```kotlin
val name = useState("John")

println(name.value)

name.value = "Mike"

println(name.value)
```

### Listening for Changes

Use `bind()` to register a listener:

```kotlin
val name = useState("John")

name.bind { newName ->
    println("Name changed to: $newName")
}
```

The listener is called immediately when it is registered, receiving the current value.

It is called again whenever the state changes to a different value.

### Example

```kotlin
val counter = useState(0)

counter.bind { value ->
    println("Counter: $value")
}

counter.value = 1
counter.value = 2
```

Output:

```text
Counter: 0
Counter: 1
Counter: 2
```

Assigning the same value does not notify listeners because the setter checks whether the new value equals the existing value.

**Important:** This implementation does not provide automatic UI updates. You must register a listener and update your views inside it. It also does not provide a listener-removal method.

## 12. View Visibility

These extensions simplify changing a view's visibility.

| Function      | Description                                               |
| ------------- | --------------------------------------------------------- |
| `View.show()` | Makes a view visible.                                     |
| `View.hide()` | Makes a view invisible while preserving its layout space. |
| `View.gone()` | Hides a view and removes its layout space.                |

### Examples

```kotlin
val text = ktTextView("Hello")

text.show()
text.hide()
text.gone()
```

The three functions correspond to Android's native visibility constants:

| Function | Android value    |
| -------- | ---------------- |
| `show()` | `View.VISIBLE`   |
| `hide()` | `View.INVISIBLE` |
| `gone()` | `View.GONE`      |

Each function returns the same View, allowing chaining:

```kotlin
text.gone()
    .setPadding(10)
```

## 13. Padding

Padding is the space between a view's content and its own edges.

This file provides three `setPadding()` overloads.

| Function                               | Description                                 |
| -------------------------------------- | ------------------------------------------- |
| `setPadding(all)`                      | Applies the same padding to all four sides. |
| `setPadding(horizontal, vertical)`     | Applies horizontal and vertical padding.    |
| `setPadding(left, top, right, bottom)` | Sets padding independently for each side.   |

### Examples

**Same padding on all sides:**

```kotlin
text.setPadding(10)
```

**Horizontal and vertical padding:**

```kotlin
text.setPadding(16, 8)
```

**Individual sides:**

```kotlin
text.setPadding(
    left = 10,
    top = 20,
    right = 10,
    bottom = 20
)
```

These functions return the same View.

The supplied values are pixels. Use `.dp` when you want density-independent dimensions:

```kotlin
text.setPadding(16.dp, 8.dp)
```

## 14. Margins

Margins control the space outside a view, separating it from neighbouring views.

| Function                              | Description                                |
| ------------------------------------- | ------------------------------------------ |
| `setMargin(all)`                      | Applies the same margin to all four sides. |
| `setMargin(horizontal, vertical)`     | Sets horizontal and vertical margins.      |
| `setMargin(left, top, right, bottom)` | Sets individual margins.                   |

### Examples

```kotlin
val button = ktButton("Submit")

button.setMargin(10)
```

Or:

```kotlin
button.setMargin(
    horizontal = 16,
    vertical = 8
)
```

You can also set each side independently:

```kotlin
button.setMargin(
    left = 10,
    top = 20,
    right = 10,
    bottom = 20
)
```

### Important

The margin functions only work when the view's current `layoutParams` is a `ViewGroup.MarginLayoutParams`.

If the layout parameters are not compatible, the function simply returns the view without changing anything.

The supplied values are pixels, not dp.

## 15. Corner Radius

These functions apply rounded corners to a view's background.

| Function                                                      | Description                                                                |
| ------------------------------------------------------------- | -------------------------------------------------------------------------- |
| `setCornerRadius(all)`                                        | Applies the same radius to all corners.                                    |
| `setCornerRadius(top, bottom)`                                | Applies one radius to both top corners and another to both bottom corners. |
| `setCornerRadius(topLeft, topRight, bottomRight, bottomLeft)` | Sets each corner radius independently.                                     |

### Examples

**All corners:**

```kotlin
button.setCornerRadius(16f)
```

**Different top and bottom corners:**

```kotlin
button.setCornerRadius(
    top = 20f,
    bottom = 5f
)
```

**Individual corners:**

```kotlin
button.setCornerRadius(
    topLeft = 20f,
    topRight = 20f,
    bottomRight = 5f,
    bottomLeft = 5f
)
```

The implementation creates a new `GradientDrawable`, sets its color to transparent, and applies the requested corner radii.

The supplied radius values are converted from dp to pixels internally.

**Note:** This replaces the view's existing background. Any previous background color, drawable, or background styling is not preserved.

## 16. Position

The file also provides three `setPosition()` overloads.

| Function                                | Description                              |
| --------------------------------------- | ---------------------------------------- |
| `setPosition(all)`                      | Applies the same value to all margins.   |
| `setPosition(horizontal, vertical)`     | Applies horizontal and vertical margins. |
| `setPosition(left, top, right, bottom)` | Applies individual margins.              |

### Examples

```kotlin
button.setPosition(10)
```

```kotlin
button.setPosition(16, 8)
```

```kotlin
button.setPosition(10, 20, 10, 20)
```

**Important:** Despite its name, `setPosition()` does not set the actual X/Y coordinates of a view. It calls the same internal margin function used by `setMargin()`.

## 17. Implementation Notes

The following are important details and limitations in the current source.

1. **Dialog display:** `ktToast()`, `ktDialog()`, `ktDateDialog()`, `ktTimeDialog()`, and `ktProgressDialog()` return their respective objects. They do not automatically display them. Call `.show()` when needed.

2. **Date dialog callback:** `ktDateDialog()` accepts a callback, but the implementation does not invoke it. The selected date is currently discarded.

3. **Background threading:** `ktThread()` uses a shared fixed thread pool with four worker threads. It does not provide a shutdown method for the pool.

4. **State listeners:** `useState.bind()` immediately invokes the listener with the current value. There is no mechanism to unregister listeners, so long-lived state objects can retain references to objects registered as listeners.

5. **View margins:** `setMargin()` and `setPosition()` do nothing if the view does not currently have `MarginLayoutParams`.

6. **View backgrounds:** `setCornerRadius()` replaces the existing background with a new transparent GradientDrawable. It does not preserve the previous background.

7. **System bars:** `ktNavBar()` and `KtStatusBar()` use older Android system UI flags. Consider migrating to modern window inset APIs.

8. **Dimension units:** Most view padding and margin values are interpreted as pixels. Convert values using `.dp` when appropriate.

9. **SoundPool resources:** The caller is responsible for releasing the SoundPool after use.

10. **Naming:** `ktUriTOFileName()` uses inconsistent capitalization. A name such as `ktUriToFileName()` would be more consistent with Kotlin naming conventions.

## 18. Summary

`KtExtensions` provides reusable shortcuts for common Android development operations.

It includes:

- Unit conversion using `dp`, `sp`, and `px`.
- ViewGroup and ListView helpers.
- Toasts and dialog creation.
- Drawable creation.
- System bar configuration.
- SoundPool creation.
- Background task execution.
- View lookup using IDs and tags.
- URI display-name retrieval.
- Observable mutable state.
- View visibility, padding, margins, corner radius, and positioning helpers.

These extensions reduce repetitive code while retaining access to the underlying Android APIs.