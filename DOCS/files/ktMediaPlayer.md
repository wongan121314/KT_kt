# KtMediaPlayer

[<-- Back](../home.md)

`KtMediaPlayer` is an audio playback class built on Android's native `MediaPlayer`. It simplifies setting an audio source and playing it asynchronously.

## Usage

Create an instance using the `ktMediaPlayer()` extension function:

```kotlin
val player = ktMediaPlayer()
```

You can also configure it using the initialization block:

```kotlin
val player = ktMediaPlayer {
    setFile("/storage/emulated/0/Music/song.mp3")
    play()
}
```

## Structure

```kotlin
class KtMediaPlayer(
    private val context: Context
) : MediaPlayer()
```

`KtMediaPlayer` inherits from Android's `MediaPlayer`. This means it also provides the native playback methods and properties, such as:

- `start()` – starts playback.
- `pause()` – pauses playback.
- `stop()` – stops playback.
- `isPlaying` – checks whether audio is playing.
- `release()` – releases the player's resources.

## Methods

| Method                  | Description                                                        |
| ----------------------- | ------------------------------------------------------------------ |
| `setFile(path: String)` | Sets an audio source using a file path.                            |
| `setFile(uri: Uri)`     | Sets an audio source using a URI.                                  |
| `play()`                | Prepares the player asynchronously and starts playback when ready. |

## Setting an Audio Source

You can set an audio source using either a file path or a `Uri`.

### Using a file path

```kotlin
player.setFile(
    "/storage/emulated/0/Music/song.mp3"
)
```

### Using a URI

```kotlin
player.setFile(musicUri)
```

Both methods call `reset()` before setting the new data source. This resets the player to its uninitialized state before configuring the new source.

## Playing Audio

After setting the audio source, call `play()`:

```kotlin
player.setFile("/storage/emulated/0/Music/song.mp3")

player.play()
```

The `play()` method uses `prepareAsync()` rather than `prepare()`.

This means the player prepares the audio in the background. Once preparation is complete, the `OnPreparedListener` calls `start()` automatically.

## Example: Playing a Selected Audio File

```kotlin
class MainActivity : KtActivity() {

    lateinit var player: KtMediaPlayer

    override fun onCreate() {
        super.onCreate()

        player = ktMediaPlayer()

        // Set the selected audio file
        player.setFile(musicUri)

        // Start playback
        player.play()
    }
}
```

## Controlling Playback

Since `KtMediaPlayer` extends Android's `MediaPlayer`, you can use its inherited methods directly.

```kotlin
// Pause playback
player.pause()

// Resume playback
player.start()

// Stop playback
player.stop()

// Check playback status
if (player.isPlaying) {
    println("Audio is playing")
}
```

## Releasing Resources

When you no longer need the player, call `release()`:

```kotlin
player.release()
```

This releases the resources used by the native media player. Make sure to release it when the owning component no longer needs it.

## Notes

- Call `setFile()` before `play()`.
- `setFile()` resets the player before assigning a new source.
- `play()` uses asynchronous preparation, so playback starts only after preparation finishes.
- The class does not implement its own pause, stop, or release methods; these are inherited from `MediaPlayer`.
- The player must be in a valid state when calling native playback methods. For example, calling `play()` without first setting a source can cause an `IllegalStateException`.
- The class does not implement its own error listener or playback-completion listener.
- When using a URI, the application must have permission to access the referenced resource.
