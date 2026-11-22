# useState()

[<-- Back](../home.md)

`useState()` creates a state holder that stores a value and allows other parts of your application to observe changes to that value.

When the stored value changes, registered listeners can receive the updated value.

## Usage

Use `useState()` when you want to create a variable whose changes can be observed by multiple UI components.

For example, multiple `TextView` instances can listen to the same state and update their text whenever its value changes.

## Structure

```kotlin
val (variable, onChange) = useState<T>(initialValue)
```

| Parameter      | Description                    |
| -------------- | ------------------------------ |
| `T`            | The type of value being stored |
| `initialValue` | The initial value of the state |

### Returned values

**1. `variable`**

A `UseState<T>` instance that holds the state.

```kotlin
variable.value       // Read the current value
variable.value = ... // Update the value
```

**2. `onChange`**

A function used to register a listener that receives the current and subsequent values.

```kotlin
onChange { 
    // Access the new value through it
}
```

You can also explicitly declare the lambda parameter:

```kotlin
onChange { result ->
    // Use result
}
```

## Examples

### 1. Sharing state between TextViews

```kotlin
val (name, onName) = useState<String>("James")

textView1.apply {
    onName { text = it }
}

textView2.apply {
    onName { text = it }
}

btn.setOnClickListener {
    name.value = "John"
}
```

Both `TextView` instances receive the updated name through their registered listeners.

### 2. Using state with numbers

```kotlin
val (count, onCount) = useState(0)

onCount {
    textView.text = it.toString()
}

btn.setOnClickListener {
    count.value = count.value + 1
}
```

Each time the button is clicked, the count is incremented and the listener can update the `TextView`.

### 3. Using state with different data types

```kotlin
val (isLoggedIn, onLogin) = useState(false)

onLogin { loggedIn ->
    textView.text = if (loggedIn) {
        "Welcome!"
    } else {
        "Please log in"
    }
}

btn.setOnClickListener {
    isLoggedIn.value = true
}
```

## Related methods

| Method                       | Description                            |
| ---------------------------- | -------------------------------------- |
| `onChange(listener)`         | Registers a listener for state changes |
| `setOnValueChange(listener)` | An alternative name for `onChange()`   |

## Notes

- The initial value is delivered to a listener when it is registered.
- Listeners can be registered from different UI components.
- The state holder can be accessed through its `value` property.
