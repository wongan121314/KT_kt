# KT_kt Examples

[<-- Back](home.md)

This page contains practical Kotlin code snippets demonstrating how to use the `KT_kt` library.

The examples are extracted from real test applications and demonstrate how different library components can be combined to build Android applications.

Some examples are independent snippets, while others demonstrate parts of a larger application.

---

## 1. Creating an Activity

Instead of extending Android's native `Activity`, you can extend `KtActivity`.

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        // Build your application here
    }
}
```

`KtActivity` provides a simplified Activity lifecycle by exposing a parameterless `onCreate()` method.

## 2. Creating Layouts

You can build Android interfaces programmatically using the layout functions provided by `KT_kt`.

### Vertical Layout

Use `ktColumnLayout()` to create a vertical layout.

```kotlin
val layout = ktColumnLayout {

    addViews(
        ktTextView("Hello World"),
        ktButton("Click Me"),
        ktEditText()
    )
}
```

### Horizontal Layout

Use `ktRowLayout()` to arrange views horizontally.

```kotlin
val layout = ktRowLayout {

    gravity = Gravity.CENTER_VERTICAL

    addViews(
        ktTextView("Music"),
        ktButton("Play"),
        ktButton("Stop")
    )
}
```

### Combining Layouts

Layouts can be nested to create more complex interfaces.

```kotlin
val screen = ktColumnLayout {

    addView(
        ktRowLayout {
            addViews(
                ktTextView("My Music"),
                ktButton("Settings")
            )
        }
    )

    addView(
        ktCardLayout {
            addView(
                ktTextView("Now Playing")
            )
        }
    )
}
```

## 3. Styling Views

You can configure views using Android's native properties inside `apply {}`.

```kotlin
val title = ktTextView(
    "My Music",
    25f,
    Color.WHITE
).apply {

    setTypeface(null, Typeface.BOLD)
    gravity = Gravity.CENTER
}
```

You can also configure background colors, padding, and other properties.

```kotlin
val card = ktCardLayout(
    bgColor = Color.rgb(32, 40, 58),
    cornerRadius = 24f,
    elevation = 5f
) {

    setPadding(20, 20, 20, 20)

    addView(
        ktTextView("Music Player")
    )
}
```

## 4. Creating a Scrollable Screen

Use `ktScrollView()` when your interface may contain more content than the available screen space.

```kotlin
setContentView(
    ktScrollView(
        bgColor = Color.rgb(16, 19, 28)
    ) {

        addView(
            ktColumnLayout {

                addViews(
                    ktTextView("My Application"),
                    ktButton("First"),
                    ktButton("Second"),
                    ktButton("Third")
                )
            }
        )
    }
)
```

A `ScrollView` normally contains one direct child. Use a layout such as `ktColumnLayout()` to hold multiple views.

## 5. Finding Views Using Tags

You can assign tags to views and retrieve them later.

```kotlin
ktTextView("No song playing").apply {
    tag = "song_title"
}
```

Retrieve the view from an Activity:

```kotlin
val songTitle = getViewByTag<TextView>("song_title")
```

You can then update its properties:

```kotlin
songTitle?.text = "Now Playing"
songTitle?.setTextColor(Color.GREEN)
```

This is useful when you want to update views after creating the interface.

## 6. Handling Button Clicks

Use `setOnClickListener` to respond to user interactions.

```kotlin
ktButton("SELECT AUDIO FILES").apply {

    setOnClickListener {

        // Perform an action
        println("Button clicked")
    }
}
```

You can combine button listeners with other library components to perform tasks such as opening file pickers or starting services.

## 7. Working with Media Playback

`KT_kt` provides `KtMediaPlayer`, which extends Android's native `MediaPlayer`.

### Creating a Media Player

```kotlin
val mp = ktMediaPlayer()
```

### Playing a Selected Audio File

```kotlin
mp.setFile(musicUri)
mp.play()
```

`setFile()` accepts a `Uri`, while `play()` prepares the media asynchronously and starts playback when preparation completes.

### Releasing the Player

When playback is no longer needed, release the player.

```kotlin
mp.stop()
mp.release()
```

The player should be released when its owning component is destroyed.

## 8. Creating a Background Service

You can extend `KtService` to create a service for work that should continue independently of an Activity's UI.

```kotlin
class TestService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        when (intent?.action) {

            "PLAY" -> {
                println("Start playback")
            }

            "STOP" -> {
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }
}
```

The service receives its start request through `onStartCommand()`.

## 9. Passing Data to a Service

Use `ktIntent()` to construct an Intent and pass data to another component.

```kotlin
ktIntent(TestService::class.java) {

    action = "PLAY"

    putParcelableArrayListExtra(
        "FILES",
        ArrayList(musicfiles)
    )

}.startService()
```

Inside the service, retrieve the supplied data:

```kotlin
override fun onStartCommand(intent: Intent?): Int {

    when (intent?.action) {

        "PLAY" -> {

            val files =
                intent.getParcelableArrayListExtra<Uri>("FILES")

            if (!files.isNullOrEmpty()) {
                musicfiles.clear()
                musicfiles.addAll(files)
            }
        }
    }

    return START_NOT_STICKY
}
```

The Intent action identifies the requested operation, while the extras carry the associated data.

## 10. Playing Music from a Service

The following example combines `KtService` and `KtMediaPlayer` to play audio files from a service.

```kotlin
class TestService : KtService() {

    private lateinit var mp: KtMediaPlayer

    private val musicfiles = mutableListOf<Uri>()

    override fun onCreate() {
        super.onCreate()

        mp = ktMediaPlayer()
    }

    override fun onStartCommand(intent: Intent?): Int {

        when (intent?.action) {

            "PLAY" -> {

                val files =
                    intent.getParcelableArrayListExtra<Uri>("FILES")

                if (!files.isNullOrEmpty()) {

                    musicfiles.clear()
                    musicfiles.addAll(files)

                    play()
                }
            }

            "STOP" -> {
                mp.stop()
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    private fun play() {

        if (musicfiles.isNotEmpty()) {

            val file = musicfiles.random()

            mp.setFile(file)
            mp.play()
        }
    }

    override fun onDestroy() {

        if (::mp.isInitialized) {
            mp.release()
        }

        super.onDestroy()
    }
}
```

This example demonstrates:

- Creating a media player inside a service.
- Receiving audio file URIs through an Intent.
- Selecting a random audio file.
- Starting playback.
- Releasing the media player when the service is destroyed.

**Note:** This is a simplified service example, not a complete production background-music implementation. A real background playback service may need to run as a foreground service with a notification and the appropriate manifest declarations and permissions.

---

## Notes

These examples are intended to demonstrate the library's usage, not to replace the detailed documentation for individual components.

For more information about a particular class or function, refer to its documentation under `docs/files/`.

