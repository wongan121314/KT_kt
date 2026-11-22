[<-- Back](../home.md)

# KtInterval

`KtInterval` repeatedly executes a block of code at specified time intervals.

It uses Android's main thread Handler to schedule executions and provides methods to start, stop, pause, resume and reset the interval.

## Usage

Create a `KtInterval` by specifying the interval duration and the block to execute.

```kotlin
val interval = KtInterval(1000) { count ->

    // Executed every second

}
```

The block receives a `Long` value representing the current execution count.

## Structure

```kotlin
KtInterval(
    interval: Long = 1000,
    block: (Long) -> Unit
)
```

| Parameter  | Description                                                             |
| ---------- | ----------------------------------------------------------------------- |
| `interval` | Time between executions in milliseconds. Defaults to `1000` (1 second). |
| `block`    | Code to execute on each interval. Receives the execution count.         |

## Properties

| Property    | Description                                                         |
| ----------- | ------------------------------------------------------------------- |
| `isRunning` | Indicates whether the interval is running.                          |
| `count`     | Holds the number of executions since the last start or count reset. |

Both properties have private setters.

## Methods

| Method                      | Description                                       |
| --------------------------- | ------------------------------------------------- |
| `start()`                   | Starts the interval.                              |
| `stop()`                    | Stops the interval and clears its scheduled task. |
| `pause()`                   | Pauses the interval.                              |
| `resume()`                  | Resumes a paused interval.                        |
| `resetCount()`              | Resets the execution count to zero.               |
| `setInterval(milliseconds)` | Changes the interval duration.                    |

## Examples

### 1. Basic interval

```kotlin
val interval = KtInterval(1000) { count ->
    textView.text = count.toString()
}

interval.start()
```

The `TextView` updates every second with the current execution count.

### 2. Starting and stopping

```kotlin
val interval = KtInterval(1000) {
    textView.text = it.toString()
}

startButton.setOnClickListener {
    interval.start()
}

stopButton.setOnClickListener {
    interval.stop()
}
```

### 3. Pausing and resuming

```kotlin
val interval = KtInterval(1000) {
    textView.text = it.toString()
}

interval.start()

pauseButton.setOnClickListener {
    interval.pause()
}

resumeButton.setOnClickListener {
    interval.resume()
}
```

Pausing preserves the current count. Resuming continues from that count.

### 4. Resetting the count

```kotlin
interval.resetCount()
```

Resets the count to zero without stopping the interval.

### 5. Changing the interval

```kotlin
interval.setInterval(2000)
```

Changes the interval duration to 2 seconds.

The new duration is used when the next task is scheduled.

## Notes

- Time values are specified in milliseconds.
- The default interval is 1000 milliseconds.
- The execution block runs on the main thread.
- Calling `start()` while already running has no effect.
- Calling `stop()` resets the running and paused states.
- Calling `start()` after stopping resets the count to zero.
- Calling `pause()` preserves the count.
- The interval must be greater than zero.
- The interval does not automatically stop when an Activity is destroyed. Stop it when it is no longer needed.
