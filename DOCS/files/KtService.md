[<-- Back](../home.md)

# KtService

`KtService` is a custom Service class that extends Android's native `Service`.

It simplifies service implementation by providing a single `onStartCommand()` method that accepts an `Intent` without requiring the additional `flags` and `startId` parameters.

It is designed for creating Android services that perform operations independently of an Activity.

## Usage

Extend `KtService` to create your own service.

```kotlin
class MyService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        // Your service code here

        return START_NOT_STICKY
    }
}
```

## Structure

```kotlin
open class KtService : Service()
```

### Methods

**1. `onStartCommand()`**

```kotlin
open fun onStartCommand(intent: Intent?): Int
```

Called when the service receives a start request.

Override this method to define what the service should do when started.

The method receives the `Intent` used to start the service and returns a service restart behaviour.

Example:

```kotlin
class MyService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        // Perform background operation

        return START_NOT_STICKY
    }
}
```

**Return values**

| Value                    | Description                                                                      |
| ------------------------ | -------------------------------------------------------------------------------- |
| `START_NOT_STICKY`       | The system does not recreate the service automatically after it has been killed. |
| `START_STICKY`           | The system may recreate the service after it has been killed.                    |
| `START_REDELIVER_INTENT` | The system may recreate the service and redeliver the last Intent.               |

**2. `onBind()`**

```kotlin
override fun onBind(intent: Intent?): IBinder? = null
```

Called when another component attempts to bind to the service.

`KtService` returns `null`, meaning it does not provide a binding interface.

This makes it suitable for started services rather than bound services.

## Examples

### 1. Creating a service

```kotlin
class DownloadService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        // Start download operation

        return START_NOT_STICKY
    }
}
```

### 2. Starting a service

From an Activity:

```kotlin
startService(
    Intent(this, DownloadService::class.java)
)
```

### 3. Passing data to a service

```kotlin
class DownloadService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        val url = intent?.getStringExtra("url")

        // Use the received URL

        return START_NOT_STICKY
    }
}
```

Starting the service:

```kotlin
val intent = Intent(this, DownloadService::class.java)

intent.putExtra("url", "https://example.com/file.zip")

startService(intent)
```

## Registering a service

Declare your service inside the application's `AndroidManifest.xml`.

```xml
<service
    android:name=".DownloadService"
    android:exported="false" />
```

## Notes

- `KtService` inherits the functionality of Android's native `Service`.
- The default `onStartCommand()` returns `START_NOT_STICKY`.
- The service does not support binding because `onBind()` returns `null`.
- `KtService` does not automatically create a foreground service. Foreground services require additional implementation, including `startForeground()` and the applicable Android permissions and service declarations.
