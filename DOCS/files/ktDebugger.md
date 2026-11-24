ktDebugger()
<-- Back
`ktDebugger()` creates a debugger for the current `Activity`. It displays a floating KT button that opens an in-app log panel.
The debugger can collect application log messages, display them by category, and let you inspect logs without keeping Android Studio's Logcat window open.
Usage
Call `ktDebugger()` from an `Activity` or a class that extends `Activity`, such as `KtActivity`.
```kotlin
val debug = ktDebugger()
    .all()
    .show()
```
Because `ktDebugger()` is an extension function on `Activity`, you do not need to pass `this`.
Structure
```kotlin
val debugger = ktDebugger()
    .info()
    .warning()
    .error()
    .show()
```
Method	Description
`ktDebugger()`	Creates a debugger for the current Activity
`info()`	Enables information messages
`warning()`	Enables warning messages
`error()`	Enables error messages
`all()`	Enables all message categories
`show()`	Displays the floating debugger
`hide()`	Hides the debugger without stopping log collection
`clear()`	Clears the messages currently stored
`stop()`	Stops log collection and removes the debugger
Examples
1. Showing all messages
```kotlin
class MainActivity : KtActivity() {

    private lateinit var debug: KtDebugger

    override fun onCreate() {
        super.onCreate()

        debug = ktDebugger()
            .all()
            .show()
    }

    override fun onDestroy() {
        debug.stop()
        super.onDestroy()
    }
}
```
2. Selecting message categories
You can select one or more categories before showing the debugger.
```kotlin
debug = ktDebugger()
    .info()
    .warning()
    .show()
```
To show every category, use `all()`:
```kotlin
debug = ktDebugger()
    .all()
    .show()
```
3. Writing log messages
The debugger includes helper methods for writing messages to Android's logging system.
```kotlin
debug.logInfo("Application started")
debug.logWarning("Connection is slow")
debug.logError("Unable to load data")
```
You can also use Android's `Log` methods directly:
```kotlin
Log.i("TEST", "Application started")
Log.w("TEST", "Warning message")
Log.e("TEST", "Error message")
```
4. Hiding and showing the debugger
`hide()` hides the debugger panel but does not stop its background log collection.
```kotlin
debug.hide()

// Show it again later
debug.show()
```
5. Clearing messages
```kotlin
debug.clear()
```
This removes the messages currently displayed. New messages can still be collected.
6. Stopping the debugger
Call `stop()` when the Activity is being destroyed.
```kotlin
override fun onDestroy() {
    debug.stop()
    super.onDestroy()
}
```
Related methods
Method	Description
`logInfo(message)`	Writes an information message
`logWarning(message)`	Writes a warning message
`logError(message)`	Writes an error message
`getMessages()`	Returns the stored messages that match the selected filters
Notes
`ktDebugger()` is an extension function on `Activity`; call it without passing `this`.
The floating debugger is displayed inside the current Activity.
`hide()` does not stop background log collection.
`stop()` permanently stops that debugger instance; create a new instance if you need another one.
The debugger attempts to read the current application's Logcat messages using its process ID.
Android may restrict Logcat access for ordinary applications. Therefore, automatic capture of direct `Log` calls depends on the Android version and device permissions.
Stop the debugger in `onDestroy()` to release its resources.