
```kotlin

fun <T> KtThread(work: () -> T, onDone: (Result<T>) -> Unit): Future<*> =
    pool.submit {
        val r = runCatching(work)             // catches exceptions into Result
        mainHandler.post { onDone(r) }
    }
```
* usage
```kotlin
ktThread(::boilWater) { result ->
    result
        .onSuccess { textView.text = it }     // it: String, never null
        .onFailure { textView.text = "Failed: ${it.message}" }
}



```
* others
```kotlin
var water: String? = null
KtThread(::boilWater) { water = it }yyutturetyt
println(water)        // still null here! callback hasn't fired yet
```

* others

```kotlin
val job = KtThread(::boilWater) { textView.text = it }
job.cancel(true)      // stop it
job.isDone            // finished?
// job.get()          // ❌ blocks the caller, and gives you nothing useful here (the Future<*> holds no result)k
```