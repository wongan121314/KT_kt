[<-- Back](../home.md)

# KtIntent

`KtIntent` is a custom class that extends Android's native `Intent`.

It provides convenient methods for performing common Android operations, including:

- Opening websites and applications.
- Sending emails and SMS messages.
- Making phone calls.
- Sharing text, images and files.
- Opening maps and locations.
- Accessing Android settings.
- Selecting and creating files.
- Capturing images and videos.
- Starting Activities and Services.
- Receiving Activity results.

## Creating a KtIntent

You can create a `KtIntent` using the `ktIntent()` extension function on `Context`.

### Structure

```kotlin
fun Context.ktIntent(
    block: KtIntent.() -> Unit = {}
): KtIntent
```

Alternatively, specify an Activity class:

```kotlin
fun Context.ktIntent(
    target: Class<*>,
    block: KtIntent.() -> Unit = {}
): KtIntent
```

### Examples

```kotlin
val intent = ktIntent {
    openWebsite("https://example.com")
}
```

Or:

```kotlin
val intent = ktIntent(SecondActivity::class.java)
```

## Opening websites and URIs

These methods configure an Intent to open a URI using an appropriate application.

| Method             | Description                    |
| ------------------ | ------------------------------ |
| `openUri(uri)`     | Opens a supplied URI           |
| `openWebsite(url)` | Opens a website using its URL  |
| `openBrowser(url)` | Alternative to `openWebsite()` |

### Examples

```kotlin
ktIntent {
    openWebsite("https://example.com")
}.start()
```

Opening a URI:

```kotlin
ktIntent {
    openUri(Uri.parse("https://example.com"))
}.start()
```

## Communication

Methods for opening communication applications and performing communication-related actions.

| Method                            | Description                                                |
| --------------------------------- | ---------------------------------------------------------- |
| `openEmail(email)`                | Opens an email application addressed to the supplied email |
| `sendEmail(email, subject, body)` | Prepares an email with a recipient, subject and message    |
| `dial(phone)`                     | Opens the phone dialer with a phone number                 |
| `makeCall(phone)`                 | Initiates a phone call                                     |
| `sendSms(phone, message)`         | Opens an SMS application with a recipient and message      |

### Examples

**Opening an email application**

```kotlin
ktIntent {
    openEmail("example@gmail.com")
}.start()
```

**Preparing an email**

```kotlin
ktIntent {
    sendEmail(
        email = "example@gmail.com",
        subject = "Hello",
        body = "How are you?"
    )
}.start()
```

**Opening the dialer**

```kotlin
ktIntent {
    dial("0999123456")
}.start()
```

**Making a phone call**

```kotlin
ktIntent {
    makeCall("0999123456")
}.start()
```

**Sending an SMS**

```kotlin
ktIntent {
    sendSms(
        phone = "0999123456",
        message = "Hello!"
    )
}.start()
```

## Sharing

Methods for sharing content with other applications.

| Method                          | Description           |
| ------------------------------- | --------------------- |
| `shareText(message)`            | Shares text           |
| `shareFile(uri, mimeType)`      | Shares a file         |
| `shareImage(uri)`               | Shares an image       |
| `shareMultiple(uris, mimeType)` | Shares multiple files |

### Examples

**Sharing text**

```kotlin
ktIntent {
    shareText("Hello from KT_kt!")
}.start()
```

**Sharing an image**

```kotlin
ktIntent {
    shareImage(imageUri)
}.start()
```

**Sharing a file**

```kotlin
ktIntent {
    shareFile(
        uri = fileUri,
        mimeType = "application/pdf"
    )
}.start()
```

**Sharing multiple files**

```kotlin
ktIntent {
    shareMultiple(
        uris = listOf(uri1, uri2),
        mimeType = "image/*"
    )
}.start()
```

## Maps and locations

Methods for opening maps and navigating to locations.

| Method                              | Description                                       |
| ----------------------------------- | ------------------------------------------------- |
| `openMaps(location)`                | Searches for a location using its name or address |
| `showLocation(latitude, longitude)` | Displays a location using coordinates             |
| `navigateTo(latitude, longitude)`   | Opens navigation to the specified coordinates     |

### Examples

```kotlin
ktIntent {
    openMaps("Blantyre, Malawi")
}.start()
```

```kotlin
ktIntent {
    showLocation(-15.7861, 35.0058)
}.start()
```

```kotlin
ktIntent {
    navigateTo(-15.7861, 35.0058)
}.start()
```

## Android settings

Methods for opening Android system settings.

| Method                    | Description                                |
| ------------------------- | ------------------------------------------ |
| `openSettings()`          | Opens general settings                     |
| `openAppSettings()`       | Opens settings for the current application |
| `openWifiSettings()`      | Opens Wi-Fi settings                       |
| `openBluetoothSettings()` | Opens Bluetooth settings                   |
| `openLocationSettings()`  | Opens location settings                    |
| `openDisplaySettings()`   | Opens display settings                     |
| `openDateSettings()`      | Opens date and time settings               |

### Examples

```kotlin
ktIntent {
    openSettings()
}.start()
```

Opening the current application's settings:

```kotlin
ktIntent {
    openAppSettings()
}.start()
```

Opening Wi-Fi settings:

```kotlin
ktIntent {
    openWifiSettings()
}.start()
```

## Opening applications

| Method                 | Description                                           |
| ---------------------- | ----------------------------------------------------- |
| `openApp(packageName)` | Opens an installed application using its package name |

### Example

```kotlin
ktIntent {
    openApp("com.android.settings")
}.start()
```

If the application has no launchable Activity, an exception is thrown.

## Alarm and clock

Methods for interacting with Android's clock application.

| Method                             | Description                   |
| ---------------------------------- | ----------------------------- |
| `openAlarm()`                      | Opens the alarm screen        |
| `setAlarm(hour, minutes, message)` | Requests creation of an alarm |
| `openTimer()`                      | Opens the timer functionality |
| `openClock()`                      | Opens the alarm screen        |

### Examples

```kotlin
ktIntent {
    openAlarm()
}.start()
```

Setting an alarm:

```kotlin
ktIntent {
    setAlarm(
        hour = 7,
        minutes = 30,
        message = "Wake up"
    )
}.start()
```

Opening the timer:

```kotlin
ktIntent {
    openTimer()
}.start()
```

## Calendar

Methods for accessing the Android calendar application.

| Method                                                        | Description                           |
| ------------------------------------------------------------- | ------------------------------------- |
| `openCalendar()`                                              | Opens the calendar                    |
| `createCalendarEvent(title, description, beginTime, endTime)` | Requests creation of a calendar event |

### Examples

```kotlin
ktIntent {
    openCalendar()
}.start()
```

Creating an event:

```kotlin
ktIntent {
    createCalendarEvent(
        title = "Meeting",
        description = "Project discussion",
        beginTime = startTimeMillis,
        endTime = endTimeMillis
    )
}.start()
```

The event times are supplied as milliseconds since the Unix epoch.

## Other system applications

| Method             | Description                      |
| ------------------ | -------------------------------- |
| `openCalculator()` | Opens the calculator application |
| `openContacts()`   | Opens contacts                   |
| `openPhone()`      | Opens the phone dialer           |

### Examples

```kotlin
ktIntent {
    openCalculator()
}.start()
```

```kotlin
ktIntent {
    openContacts()
}.start()
```

```kotlin
ktIntent {
    openPhone()
}.start()
```

## Activity navigation

Methods for opening Activities.

| Method                              | Description                                                 |
| ----------------------------------- | ----------------------------------------------------------- |
| `openActivity(target)`              | Sets the target Activity                                    |
| `openActivityAndClearStack(target)` | Sets the target Activity and clears the existing task stack |

### Examples

**Opening an Activity**

```kotlin
ktIntent {
    openActivity(SecondActivity::class.java)
}.start()
```

**Opening an Activity and clearing the stack**

```kotlin
ktIntent {
    openActivityAndClearStack(
        MainActivity::class.java
    )
}.start()
```

You can also specify the target when creating the Intent:

```kotlin
ktIntent(SecondActivity::class.java).start()
```

## File selection and document management

Methods for selecting, opening and creating files.

| Method                               | Description                                              |
| ------------------------------------ | -------------------------------------------------------- |
| `pickFile(mimeType)`                 | Opens a file selection interface                         |
| `pickFile(type, multiple)`           | Opens a document picker with optional multiple selection |
| `openDocument(mimeType)`             | Opens a document using Android's document picker         |
| `createDocument(mimeType, fileName)` | Requests creation of a document                          |
| `pickContact()`                      | Opens the contact picker                                 |

### Examples

**Selecting a file**

```kotlin
ktIntent {
    pickFile("application/pdf")
}.start()
```

**Selecting multiple files**

```kotlin
ktIntent {
    pickFile(
        type = "*/*",
        multiple = true
    )
}.start()
```

**Opening a document**

```kotlin
ktIntent {
    openDocument("image/*")
}.start()
```

**Creating a document**

```kotlin
ktIntent {
    createDocument(
        mimeType = "text/plain",
        fileName = "notes.txt"
    )
}.start()
```

**Picking a contact**

```kotlin
ktIntent {
    pickContact()
}.start()
```

## Capturing images and videos

Methods for opening the device camera.

| Method                    | Description                                        |
| ------------------------- | -------------------------------------------------- |
| `captureImage(outputUri)` | Requests image capture and specifies an output URI |
| `captureVideo(outputUri)` | Requests video capture and specifies an output URI |

### Examples

**Capturing an image**

```kotlin
ktIntent {
    captureImage(imageUri)
}.start()
```

**Capturing a video**

```kotlin
ktIntent {
    captureVideo(videoUri)
}.start()
```

The output URI specifies where the captured media should be stored.

## Starting and stopping components

Methods for starting Activities and Services.

| Method                     | Description                    |
| -------------------------- | ------------------------------ |
| `start()`                  | Starts the configured Intent   |
| `startActivity()`          | Starts the configured Activity |
| `startService()`           | Starts a Service               |
| `startForegroundService()` | Starts a foreground Service    |
| `stopService()`            | Stops a Service                |

### Examples

**Starting an Activity**

```kotlin
ktIntent(SecondActivity::class.java).startActivity()
```

**Starting a Service**

```kotlin
ktIntent(MyService::class.java).startService()
```

**Starting a foreground Service**

```kotlin
ktIntent(MyService::class.java).startForegroundService()
```

**Stopping a Service**

```kotlin
ktIntent(MyService::class.java).stopService()
```

`startForegroundService()` uses Android's foreground-service API on Android 8.0 and newer. On older versions, it uses `startService()`.

A Service started this way must implement its own foreground-service behaviour when required.

## Receiving Activity results

`KtIntent` supports receiving results through `KtActivityResultManager`.

Instead of manually implementing Android's Activity result handling, you can register a callback using `setOnResultListener()`.

### Methods
Agreed. We'll keep the **Methods** section simple: list method names and their brief descriptions in a table. Detailed explanations and examples will appear in their respective functional sections.

For example, in `KtIntent.md`:

## **Methods**

| Method                               | Description                                 |
| ------------------------------------ | ------------------------------------------- |
| `openUri(uri)`                       | Opens a URI                                 |
| `openWebsite(url)`                   | Opens a website                             |
| `openBrowser(url)`                   | Opens a browser                             |
| `openEmail(email)`                   | Opens an email application                  |
| `sendEmail(email, subject, body)`    | Prepares an email                           |
| `dial(phone)`                        | Opens the phone dialer                      |
| `makeCall(phone)`                    | Makes a phone call                          |
| `sendSms(phone, message)`            | Prepares an SMS                             |
| `shareText(message)`                 | Shares text                                 |
| `shareFile(uri, mimeType)`           | Shares a file                               |
| `shareImage(uri)`                    | Shares an image                             |
| `shareMultiple(uris, mimeType)`      | Shares multiple files                       |
| `openMaps(location)`                 | Opens a map search                          |
| `showLocation(latitude, longitude)`  | Displays a location                         |
| `navigateTo(latitude, longitude)`    | Opens navigation                            |
| `openSettings()`                     | Opens system settings                       |
| `openAppSettings()`                  | Opens application settings                  |
| `openApp(packageName)`               | Opens another application                   |
| `openActivity(target)`               | Sets an Activity destination                |
| `openActivityAndClearStack(target)`  | Opens an Activity and clears the task stack |
| `pickFile(...)`                      | Opens a file picker                         |
| `openDocument(mimeType)`             | Opens a document picker                     |
| `createDocument(mimeType, fileName)` | Creates a document                          |
| `pickContact()`                      | Opens the contact picker                    |
| `captureImage(outputUri)`            | Captures an image                           |
| `captureVideo(outputUri)`            | Captures a video                            |
| `openAlarm()`                        | Opens alarms                                |
| `setAlarm(hour, minutes, message)`   | Sets an alarm                               |
| `openTimer()`                        | Opens the timer                             |
| `openClock()`                        | Opens the clock                             |
| `openCalendar()`                     | Opens the calendar                          |
| `createCalendarEvent(...)`           | Creates a calendar event                    |
| `openCalculator()`                   | Opens the calculator                        |
| `openContacts()`                     | Opens contacts                              |
| `openPhone()`                        | Opens the phone dialer                      |
| `openDateSettings()`                 | Opens date settings                         |
| `openWifiSettings()`                 | Opens Wi-Fi settings                        |
| `openBluetoothSettings()`            | Opens Bluetooth settings                    |
| `openLocationSettings()`             | Opens location settings                     |
| `openDisplaySettings()`              | Opens display settings                      |
| `start()`                            | Starts the configured Intent                |
| `startActivity()`                    | Starts an Activity                          |
| `startService()`                     | Starts a Service                            |
| `startForegroundService()`           | Starts a foreground Service                 |
| `stopService()`                      | Stops a Service                             |
| `setOnResultListener(callback)`      | Registers an Activity result listener       |
| `startForResult()`                   | Starts an Intent and receives its result    |
| `closeActivity()`                    | Finishes the current Activity               |
| `closeApp()`                         | Finishes the current Activity               |



### Example

```kotlin
ktIntent {
    pickFile("image/*")

    setOnResultListener { result ->

        // Handle the returned result

    }
}.startForResult()
```

The callback receives a `KtActivityResult` object.

### Requirements

- An Activity context is required.
- Call `setOnResultListener()` before `startForResult()`.
- Calling `startForResult()` without a registered listener throws an `IllegalStateException`.

## Closing an Activity

| Method            | Description                                            |
| ----------------- | ------------------------------------------------------ |
| `closeActivity()` | Finishes the Activity associated with the context      |
| `closeApp()`      | Also finishes the Activity associated with the context |

### Example

```kotlin
ktIntent {
    closeActivity()
}
```

These methods only finish the Activity when the supplied context is an Activity.

## Notes

- `KtIntent` extends Android's native `Intent`, so native Intent properties and methods are also available.
- Most configuration methods return the same `KtIntent` instance, allowing method chaining.
- `start()` can optionally accept a chooser title.
- When starting an Activity from a non-Activity context, `start()` and `startActivity()` automatically add `FLAG_ACTIVITY_NEW_TASK`.
- Some operations depend on other applications being installed and capable of handling the requested Intent.
- Making a direct phone call with `makeCall()` requires the appropriate Android permission.
- Camera capture and file operations may require suitable URI permissions and storage handling.
- Starting foreground services requires compliance with Android foreground-service requirements.
