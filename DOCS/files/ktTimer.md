[<-- Back](../home.md)

# KtTimer

`KtTimer` executes a block of code once after a specified delay.

It uses Android's main thread Handler to schedule execution and provides methods to start, stop and restart the timer.

Unlike `KtInterval`, which repeatedly executes its block, `KtTimer` executes its block only once per start.

## Usage

Create a `KtTimer` by specifying the delay and the block to execute.

```kotlin
val timer = KtTimer(3000) {

    // Executed after 3 seconds

}
```

## Structure

```kotlin
KtTimer(
    delay: Long = 1000,
    block: () -> Unit
)
```

| Parameter | Description |
|---|---|
| `delay` | Time to wait before execution, in milliseconds. Defaults to `1000` (1 second). |
| `block` | Code to execute when the timer finishes. |

## Properties

| Property | Description |
|---|---|
| `isRunning` | Indicates whether the timer is currently waiting to execute. |

The property has a private setter.

## Methods

| Method | Description |
|---|---|
| `start()` | Starts the timer. |
| `stop()` | Stops the timer and removes its scheduled task. |
| `restart()` | Stops and starts the timer again. |
| `setDelay(milliseconds)` | Changes the timer delay. |

## Examples

### 1. Basic timer

```kotlin
val timer = KtTimer(3000) {
    textView.text = "Time is up!"
}

timer.start()
```

The message appears after 3 seconds.

### 2. Starting and stopping

```kotlin
val timer = KtTimer(5000) {
    textView.text = "Finished!"
}

startButton.setOnClickListener {
    timer.start()
}

stopButton.setOnClickListener {
    timer.stop()
}
```

### 3. Restarting the timer

```kotlin
val timer = KtTimer(5000) {
    textView.text = "Finished!"
}

timer.start()

restartButton.setOnClickListener {
    timer.restart()
}
```

Calling `restart()` cancels the previous scheduled execution and starts a new countdown.

### 4. Changing the delay

```kotlin
timer.setDelay(10000)
timer.start()
```

Changes the delay to 10 seconds.

### 5. Using a zero delay

```kotlin
val timer = KtTimer(0) {
    textView.text = "Executed!"
}

timer.start()
```

A zero delay schedules the block for execution as soon as the main thread can process it.

## Notes

- Time values are specified in milliseconds.
- The default delay is 1000 milliseconds.
- The execution block runs on the main thread.
- Calling `start()` while the timer is already running has no effect.
- Calling `stop()` cancels the scheduled execution.
- Calling `restart()` starts a fresh timer.
- The delay must be zero or greater.
- `isRunning` becomes `false` before the execution block is called.
- The timer does not automatically stop when an Activity is destroyed. Stop it when it is no longer needed.

