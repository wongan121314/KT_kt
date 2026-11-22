[<-- Back](../home.md)

# KtNotification

`KtNotification` is a notification builder class that simplifies creating and displaying Android notifications.

It provides methods for configuring notification properties such as title, message, icon, channel, progress and behaviour.

It supports both normal notifications and foreground service notifications.

## Usage

Create a notification using the `ktNotification()` extension function on `Context`.

```kotlin
ktNotification {
    setTitle("Hello")
    setMessage("Welcome to KT_kt")
}.show()
```

## Structure

```kotlin
fun Context.ktNotification(
    block: KtNotification.() -> Unit = {}
): KtNotification
```

The function creates a `KtNotification` instance using the supplied Context and applies the configuration block.

## Properties

The notification properties are configured through setter methods.

| Property         | Default value             | Description                                     |
| ---------------- | ------------------------- | ----------------------------------------------- |
| `title`          | `"Notification"`          | Notification title                              |
| `message`        | `""`                      | Notification message                            |
| `smallIcon`      | `ic_dialog_info`          | Notification small icon                         |
| `notificationId` | `100`                     | Notification identifier                         |
| `channelId`      | `"kt_general"`            | Notification channel ID                         |
| `channelName`    | `"General Notifications"` | Notification channel name                       |
| `autoCancel`     | `true`                    | Whether the notification disappears when tapped |
| `ongoing`        | `false`                   | Whether the notification is marked as ongoing   |
| `progressMax`    | `0`                       | Maximum progress value                          |
| `progressValue`  | `0`                       | Current progress value                          |
| `indeterminate`  | `false`                   | Whether progress is indeterminate               |
| `bigText`        | `null`                    | Expanded notification text                      |

## Methods

| Method                                      | Description                                |
| ------------------------------------------- | ------------------------------------------ |
| `setTitle(value)`                           | Sets the notification title                |
| `setMessage(value)`                         | Sets the notification message              |
| `setIcon(value)`                            | Sets the notification icon                 |
| `setId(value)`                              | Sets the notification ID                   |
| `setChannel(id, name)`                      | Sets the notification channel              |
| `setAutoCancel(value)`                      | Enables or disables automatic cancellation |
| `setOngoing(value)`                         | Marks the notification as ongoing or not   |
| `setProgress(max, progress, indeterminate)` | Configures notification progress           |
| `setBigText(value)`                         | Sets expanded notification text            |
| `build()`                                   | Builds and returns an Android Notification |
| `show()`                                    | Displays a normal notification             |
| `showForeground()`                          | Displays a foreground service notification |
| `cancel()`                                  | Cancels the notification                   |

## Examples

### 1. Basic notification

```kotlin
ktNotification {
    setTitle("KT_kt")
    setMessage("Hello World!")
}.show()
```

### 2. Custom notification icon

```kotlin
ktNotification {
    setTitle("Download")
    setMessage("Your download is ready")
    setIcon(R.drawable.my_icon)
}.show()
```

The icon parameter accepts an Android drawable resource ID.

### 3. Custom notification channel

```kotlin
ktNotification {
    setTitle("Messages")
    setMessage("You have a new message")

    setChannel(
        id = "messages",
        name = "Messages"
    )
}.show()
```

A notification channel is created automatically on Android 8.0 and newer.

### 4. Controlling notification behaviour

**Automatic cancellation**

```kotlin
ktNotification {
    setTitle("Welcome")
    setMessage("You are logged in")
    setAutoCancel(true)
}.show()
```

When enabled, the notification is dismissed when the user taps it.

**Ongoing notification**

```kotlin
ktNotification {
    setTitle("Download")
    setMessage("Downloading file...")
    setOngoing(true)
}.show()
```

Marks the notification as ongoing.

### 5. Notification with progress

```kotlin
ktNotification {
    setTitle("Downloading")
    setMessage("Please wait...")

    setProgress(
        max = 100,
        progress = 50
    )
}.show()
```

Displays a progress indicator at 50%.

**Indeterminate progress**

```kotlin
ktNotification {
    setTitle("Processing")
    setMessage("Please wait...")

    setProgress(
        max = 100,
        progress = 0,
        indeterminate = true
    )
}.show()
```

Displays a progress indicator without a specific completion percentage.

### 6. Expanded notification text

```kotlin
ktNotification {
    setTitle("KT_kt")
    setMessage("New update available")

    setBigText(
        "A new version of KT_kt is available. " +
        "Update your library to access the latest features."
    )
}.show()
```

The expanded text is displayed when the user expands the notification.

### 7. Building a notification

You can create a notification without displaying it immediately by using `build()`.

```kotlin
val notification = ktNotification {
    setTitle("KT_kt")
    setMessage("Service is running")
}.build()
```

This returns an Android `Notification` object.

### 8. Displaying a foreground notification

`showForeground()` displays a notification associated with a running Android Service.

It must be called using a Service context.

Example:

```kotlin
class MyForegroundService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        ktNotification {
            setTitle("KT_kt")
            setMessage("Service is running")

            setChannel(
                "messages",
                "Messages"
            )

            setOngoing(true)

        }.showForeground()

        return START_STICKY
    }
}
```

This calls Android's `startForeground()` method using the configured notification.

### 9. Cancelling a notification

Use `cancel()` to remove a previously displayed notification.

```kotlin
ktNotification {
    setId(100)
}.cancel()
```

The notification ID must match the ID used when displaying the notification.

## Notes

- `KtNotification` uses Android's native Notification API.
- Setter methods return the same `KtNotification` instance, allowing method chaining.
- The default notification ID is `100`.
- Notifications use the default channel ID `kt_general`.
- On Android 8.0 and newer, notification channels are created automatically.
- Reusing an existing channel ID does not recreate its settings; Android retains the existing channel configuration.
- `showForeground()` requires a Service context.
- `show()` displays a normal notification and does not start a foreground service.
- Foreground services must also comply with Android's foreground-service permissions, declarations and applicable execution requirements.
- On Android 13 and newer, normal notifications generally require the `POST_NOTIFICATIONS` runtime permission.

