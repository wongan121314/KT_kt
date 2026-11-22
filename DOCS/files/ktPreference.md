# KtPreferences

[<-- Back](../home.md)

`KtPreferences` provides extension functions for working with Android's `SharedPreferences`.

It simplifies storing and managing small amounts of persistent application data, such as user settings, preferences, and simple configuration values.

## Usage

Create or access a preferences instance using `ktPreferences()`:

```kotlin
val prefs = ktPreferences()
```

You can also specify a custom preferences file name:

```kotlin
val prefs = ktPreferences("UserSettings")
```

By default, the preferences file is created using `Context.MODE_PRIVATE`.

## Methods

| Method                      | Description                                        |
| --------------------------- | -------------------------------------------------- |
| `ktPreferences(name, mode)` | Creates or retrieves a SharedPreferences instance. |
| `putString(key, value)`     | Stores a String value.                             |
| `putInt(key, value)`        | Stores an Int value.                               |
| `putBoolean(key, value)`    | Stores a Boolean value.                            |
| `putFloat(key, value)`      | Stores a Float value.                              |
| `putLong(key, value)`       | Stores a Long value.                               |
| `delete(key)`               | Removes a value using its key.                     |
| `clearAll()`                | Removes all stored values.                         |
| `has(key)`                  | Checks whether a key exists.                       |

## Creating Preferences

The `ktPreferences()` extension function is available on `Context`.

```kotlin
val prefs = ktPreferences()
```

It accepts two optional parameters:

| Parameter | Default                | Description                          |
| --------- | ---------------------- | ------------------------------------ |
| `name`    | `"KtPreferences"`      | Name of the preferences file.        |
| `mode`    | `Context.MODE_PRIVATE` | Access mode of the preferences file. |

Example with a custom name:

```kotlin
val prefs = ktPreferences(
    name = "UserSettings"
)
```

Calling `ktPreferences()` with the same name accesses the same preferences file.

## Saving Values

Use the `put` extension functions to save values.

### String

```kotlin
prefs.putString("username", "John")
```

### Integer

```kotlin
prefs.putInt("age", 25)
```

### Boolean

```kotlin
prefs.putBoolean("isLoggedIn", true)
```

### Float

```kotlin
prefs.putFloat("volume", 0.75f)
```

### Long

```kotlin
prefs.putLong("timestamp", System.currentTimeMillis())
```

These functions use `edit()` and `apply()` internally, so changes are applied asynchronously.

## Reading Values

Reading is done using the native `SharedPreferences` methods.

Each method accepts a key and a default value. The default is returned when the key does not exist.

```kotlin
val username = prefs.getString("username", "") ?: ""
val age = prefs.getInt("age", 0)
val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
val volume = prefs.getFloat("volume", 1.0f)
val timestamp = prefs.getLong("timestamp", 0L)
```

## Checking for a Key

Use `has()` to check whether a particular key exists.

```kotlin
if (prefs.has("username")) {
    println("Username is saved")
}
```

It returns `true` if the key exists and `false` otherwise.

## Deleting Values

Use `delete()` to remove a specific value:

```kotlin
prefs.delete("username")
```

This removes the value associated with the specified key.

## Clearing Preferences

Use `clearAll()` to remove every stored key-value pair from the preferences file:

```kotlin
prefs.clearAll()
```

This does not delete the preferences file itself. It clears its stored contents.

## Complete Example

```kotlin
class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        val prefs = ktPreferences("UserSettings")

        // Save values
        prefs.putString("username", "John")
        prefs.putInt("age", 25)
        prefs.putBoolean("isLoggedIn", true)

        // Read values
        val username = prefs.getString("username", "") ?: ""
        val age = prefs.getInt("age", 0)
        val loggedIn = prefs.getBoolean("isLoggedIn", false)

        // Check a key
        if (prefs.has("username")) {
            println("Welcome $username")
        }

        // Delete a value
        prefs.delete("age")

        // Clear all values
        // prefs.clearAll()
    }
}
```

## Notes

- `SharedPreferences` stores data persistently, so values remain available after the application is closed and reopened.
- The default preferences file name is `"KtPreferences"`.
- `Context.MODE_PRIVATE` restricts access to the preferences file to the application.
- The `put` functions use `apply()`, which updates the in-memory preferences immediately and writes changes to disk asynchronously.
- Reading methods such as `getString()` and `getInt()` are inherited from Android's `SharedPreferences` API.
- `delete()` removes only the specified key.
- `clearAll()` removes all stored entries from the selected preferences file.
- These functions are intended for simple key-value data, not large databases or complex structured records.