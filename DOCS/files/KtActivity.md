[<-- Back](../home.md)

# KtActivity

`KtActivity` is a custom Activity class that extends Android's native `Activity`.

It simplifies Activity creation by providing an alternative `onCreate()` method that does not require a `Bundle` parameter.

It also automatically hides the ActionBar and handles activity results through `KtActivityResultManager`.

## Usage

Instead of extending Android's native `Activity`, extend `KtActivity`.

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {

        // Your activity code here

    }
}
```

## Structure

```kotlin
open class KtActivity : Activity()
```

### Methods

**1. `onCreate()`**

```kotlin
open fun onCreate()
```

This method is called when the Activity is created.

Override it in your Activity subclass to initialize your application interface and perform other setup operations.

Unlike Android's native `onCreate()`, this method does not require a `Bundle` parameter.

Example:

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        addView(
            ktTextView("Hello World")
        )
    }
}
```

**2. `onActivityResult()`**

```kotlin
override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
)
```

Receives results returned from activities launched to perform operations such as selecting files or images.

`KtActivity` forwards the received result to `KtActivityResultManager` for processing.

You normally do not need to override this method when using the library's built-in activity result functionality.

## Automatic behaviour

When an instance of `KtActivity` is created:

- The native Activity `onCreate()` lifecycle method is called.
- The ActionBar is hidden.
- The parameterless `onCreate()` method is called.
- Activity results are forwarded to `KtActivityResultManager`.

## Notes

- `KtActivity` inherits the functionality of Android's native `Activity`.
- The parameterless `onCreate()` is the method you normally override.
- The native `onCreate(Bundle?)` method is final in this class and cannot be overridden by subclasses.
