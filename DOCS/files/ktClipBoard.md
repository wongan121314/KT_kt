# KtClipboard

[<-- Back](../home.md)

`KtClipboard` provides extension functions for accessing and managing Android's system clipboard.

It simplifies clipboard operations by allowing you to copy text, retrieve copied text, and clear the clipboard using a few simple methods.

## Getting the Clipboard Manager

Use `ktClipboard()` inside a `KtActivity` or another `Context` to obtain Android's `ClipboardManager`.

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        val clipboard = ktClipboard()
    }
}
```

The function returns Android's native `ClipboardManager`.

## Methods

| Method          | Description                                       |
| --------------- | ------------------------------------------------- |
| `ktClipboard()` | Gets the system clipboard manager from a Context. |
| `copy(text)`    | Copies text to the clipboard.                     |
| `paste()`       | Retrieves text from the clipboard.                |
| `clear()`       | Clears the primary clipboard content.             |

## Copying Text

Use `copy()` to place text on the system clipboard.

```kotlin
val clipboard = ktClipboard()

clipboard.copy("Hello from KT_kt")
```

The text is stored as a plain-text clipboard item using Android's `ClipData.newPlainText()`.

## Pasting Text

Use `paste()` to retrieve the text from the clipboard.

```kotlin
val clipboard = ktClipboard()

val copiedText = clipboard.paste()

println(copiedText)
```

The method returns `CharSequence?`.

It returns `null` if there is no primary clip or if the first clipboard item does not contain text.

## Clearing the Clipboard

Use `clear()` to clear the current primary clipboard content.

```kotlin
val clipboard = ktClipboard()

clipboard.clear()
```

This calls Android's `clearPrimaryClip()` method.

## Complete Example

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        val clipboard = ktClipboard()

        // Copy text
        clipboard.copy("Hello from KT_kt")

        // Retrieve copied text
        val copiedText = clipboard.paste()

        // Display copied text
        println(copiedText)

        // Clear clipboard
        clipboard.clear()
    }
}
```

## Important Notes

- `ktClipboard()` returns Android's native `ClipboardManager`.
- `copy()` stores text as plain text.
- `paste()` reads only the first item of the primary clip.
- `paste()` returns a nullable `CharSequence`, so check for `null` before using the result.
- `clear()` clears the primary clipboard content.
- Clipboard access is subject to Android's clipboard privacy restrictions. In particular, reading clipboard content may be restricted or accompanied by privacy notifications depending on the Android version and application state.
- This implementation handles text only. It does not provide methods for copying images, files, or multiple clipboard items.

