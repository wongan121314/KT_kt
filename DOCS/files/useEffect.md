useEffect()
<-- Back
`useEffect()` observes a `UseState<T>` object and executes a callback when the state value changes.
The callback is also executed immediately when the observer is registered, so it receives the current value before waiting for later changes.
Usage
Use `useEffect()` when you want to perform an action whenever a state value changes.
For example, you can update a `TextView`, react to a counter change, or run another piece of code whenever the observed state is updated.
Structure
```kotlin
useEffect(state) { value ->
    // Respond to the current or updated value
}
```
Parameter	Description
`state`	The `UseState<T>` instance to observe
`effect`	A callback that receives the current value and subsequent updates
Examples
1. Observing a state value
```kotlin
val name = useState("James")

useEffect(name) { value ->
    textView.text = value
}
```
When `useEffect()` is registered, the `TextView` immediately receives `"James"`. It is updated again whenever `name.value` changes.
2. Reacting to a button click
```kotlin
val count = useState(0)

useEffect(count) { value ->
    textView.text = value.toString()
}

btn.setOnClickListener {
    count.value++
}
```
Each button click changes the state. The effect receives the updated count and updates the `TextView`.
3. Observing a Boolean state
```kotlin
val isLoggedIn = useState(false)

useEffect(isLoggedIn) { loggedIn ->
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
The effect responds both to the initial value and to the later login-state update.
Related methods
Method	Description
`onChange(listener)`	Registers a listener on a `UseState` object
`setOnValueChange(listener)`	An alternative name for `onChange()`
Notes
`useEffect()` observes an existing `UseState<T>` object; it does not create a state.
The callback receives the current value immediately when it is registered.
The callback runs again whenever the observed state value is assigned a new value.
The callback receives the value as its lambda parameter.