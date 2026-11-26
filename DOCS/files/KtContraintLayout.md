# ktConstraintLayout()

[<-- Back](../home.md)

`ktConstraintLayout()` creates a ConstraintLayout that allows you to position and arrange views relative to other views or the parent layout.

It provides convenient properties for setting constraints, margins, dimensions, and alignment without manually creating `ConstraintLayout.LayoutParams`.

## Properties and Methods

### `ktConstraintLayout(width, height)`

Creates a new ConstraintLayout.

- `width`: Defines the layout width. Default is `MATCH_PARENT`.
- `height`: Defines the layout height. Default is `MATCH_PARENT`.

### `PARENT_ID`

Represents the ID of the parent ConstraintLayout.

It is used when positioning a view relative to its parent.

### `topToTop`

Connects the top of a view to the top of another view or parent.

### `topToBottom`

Connects the top of a view to the bottom of another view.

### `bottomToTop`

Connects the bottom of a view to the top of another view.

### `bottomToBottom`

Connects the bottom of a view to the bottom of another view or parent.

### `startToStart`

Connects the start of a view to the start of another view or parent.

### `startToEnd`

Connects the start of a view to the end of another view.

### `endToStart`

Connects the end of a view to the start of another view.

### `endToEnd`

Connects the end of a view to the end of another view or parent.

### `matchConstraintPercentWidth`

Sets the width of a view as a percentage of the available parent width.

The value ranges from `0.0f` to `1.0f`.

### `matchConstraintPercentHeight`

Sets the height of a view as a percentage of the available parent height.

The value ranges from `0.0f` to `1.0f`.

### `horizontalBias`

Controls the horizontal position of a view between its connected constraints.

- `0.0f`: Aligns towards the start.
- `0.5f`: Centers the view.
- `1.0f`: Aligns towards the end.

### `verticalBias`

Controls the vertical position of a view between its connected constraints.

- `0.0f`: Aligns towards the top.
- `0.5f`: Centers the view.
- `1.0f`: Aligns towards the bottom.

### `dimensionRatio`

Sets the width-to-height ratio of a view.

For example, `"16:9"` creates a 16:9 dimension ratio.

### `topMargin`

Sets the top margin of a view.

### `bottomMargin`

Sets the bottom margin of a view.

### `startMargin`

Sets the start margin of a view.

### `endMargin`

Sets the end margin of a view.

## Examples

### Creating a ConstraintLayout

```kotlin
ktConstraintLayout {
    
}
```

### Setting Layout Dimensions

```kotlin
ktConstraintLayout(
    width = MATCH_PARENT,
    height = MATCH_PARENT
) {
    
}
```

### Positioning a View at the Center

```kotlin
ktConstraintLayout {

    val text = ktTextView("Hello")

    addView(text)

    text.apply {
        topToTop = PARENT_ID
        bottomToBottom = PARENT_ID

        startToStart = PARENT_ID
        endToEnd = PARENT_ID
    }
}
```

### Positioning Views Relative to Each Other

```kotlin
ktConstraintLayout {

    val title = ktTextView("Welcome")

    val button = ktButton("Continue")

    addView(title)
    addView(button)

    title.apply {
        topToTop = PARENT_ID
        startToStart = PARENT_ID
    }

    button.apply {
        topToBottom = title.id
        startToStart = PARENT_ID
    }
}
```

### Using Percentage Width and Height

```kotlin
ktConstraintLayout {

    val box = ktTextView("50% Size")

    addView(box)

    box.apply {
        matchConstraintPercentWidth = 0.5f
        matchConstraintPercentHeight = 0.5f

        topToTop = PARENT_ID
        startToStart = PARENT_ID
    }
}
```

### Using Bias

```kotlin
ktConstraintLayout {

    val button = ktButton("Continue")

    addView(button)

    button.apply {
        topToTop = PARENT_ID
        bottomToBottom = PARENT_ID

        startToStart = PARENT_ID
        endToEnd = PARENT_ID

        horizontalBias = 0.8f
        verticalBias = 0.5f
    }
}
```

### Using Dimension Ratio

```kotlin
ktConstraintLayout {

    val box = ktTextView("16:9")

    addView(box)

    box.apply {
        dimensionRatio = "16:9"

        topToTop = PARENT_ID
        startToStart = PARENT_ID
    }
}
```

### Setting Margins

```kotlin
ktConstraintLayout {

    val text = ktTextView("Hello")

    addView(text)

    text.apply {
        topToTop = PARENT_ID
        startToStart = PARENT_ID

        topMargin = 20
        startMargin = 16
    }
}
```

## Notes

- All constraint properties are extensions of Android's `View`.
- Constraints are applied through `ConstraintLayout.LayoutParams`.
- `PARENT_ID` refers to the parent ConstraintLayout.
- Views must have valid IDs when used as constraint targets.
- Percentage dimensions require the corresponding dimension to be set to `0` (MATCH_CONSTRAINT), which is handled automatically by the percentage properties.
- Margin values are in pixels, not density-independent pixels