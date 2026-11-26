# ktTheme()

[<-- Back](../home.md)

`ktTheme()` creates a theme manager that allows you to define and manage your application's colors for Day and Night modes.

It provides a simple way to define colors once and access them throughout your application. When the theme changes, the Activity is recreated automatically so that the views can use the newly selected colors.

## Methods

### `ktTheme()`

Creates a new theme configuration.

### `setStyle[name](day, night)`

Defines a color style for both Day and Night modes.

- `name`: The name used to identify the style.
- `day`: The color used in Day mode.
- `night`: The color used in Night mode.

### `setTheme.day()`

Changes the application theme to Day mode and recreates the Activity.

### `setTheme.night()`

Changes the application theme to Night mode and recreates the Activity.

### `setTheme.change()`

Switches between Day and Night modes and recreates the Activity.

### `setTheme.system()`

Enables system theme detection. The application follows the Android system's Day/Night setting.

When the system theme changes, Android recreates the Activity and KT_kt updates its theme accordingly.

### `theme.primary`

Returns the currently active primary color.

### `theme.secondary`

Returns the currently active secondary color.

### `theme.background`

Returns the currently active background color.

### `theme.text`

Returns the currently active text color.

### `theme[name]`

Returns a theme color using its registered name.

## Examples

### Creating a Theme

```kotlin
val (theme, setTheme) = ktTheme().apply {

    setStyle["primary"](Color.BLUE, Color.CYAN)

    setStyle["secondary"](Color.GRAY, Color.DKGRAY)

    setStyle["background"](Color.WHITE, Color.BLACK)

    setStyle["text"](Color.BLACK, Color.WHITE)

}
```

### Using Theme Colors

```kotlin
ktButton("Click Me").apply {
    setTextColor(theme.text)
    setBackgroundColor(theme.secondary)
}
```

### Accessing Custom Styles

```kotlin
val (theme, setTheme) = ktTheme().apply {

    setStyle["button"](Color.BLUE, Color.DKGRAY)

    setStyle["card"](Color.WHITE, Color.BLACK)

}

val buttonColor = theme["button"]
val cardColor = theme["card"]
```

### Changing Theme

```kotlin
setTheme.day()

setTheme.night()

setTheme.change()
```

### Following Android System Theme

```kotlin
setTheme.system()
```

The application automatically follows the Android system's Day/Night setting.

### Complete Example

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        val (theme, setTheme) = ktTheme().apply {

            setStyle["primary"](Color.BLUE, Color.CYAN)

            setStyle["background"](Color.WHITE, Color.BLACK)

            setStyle["text"](Color.BLACK, Color.WHITE)

        }

        ktButton("Change Theme").apply {

            setTextColor(theme.text)

            setBackgroundColor(theme.primary)

            setOnClickListener {
                setTheme.change()
            }

        }
    }
}
```

## Notes

- Theme colors are shared through the theme manager.
- Unregistered styles use their default colors.
- The selected theme mode and registered styles are retained during Activity recreation.
- `setTheme.system()` restores automatic Android system theme detection.
- Views must be created again inside `onCreate()` after Activity recreation to receive the new colors.