package com.KT_kt

import android.os.Handler
import android.os.Looper

class KtInterval(
    private var interval: Long = 1000,
    private val block: (Long) -> Unit
) {

    private val handler = Handler(Looper.getMainLooper())

    var isRunning = false
    private set

    var count = 0L
    private set

    private var isPaused = false

    private val task = object : Runnable {
        override fun run() {

            if (!isRunning || isPaused) return

            count++
            block(count)

            if (isRunning && !isPaused) {
                handler.postDelayed(this, interval)
            }
        }
    }

    fun start() {
        if (isRunning) return

        count = 0
        isRunning = true
        isPaused = false

        handler.removeCallbacks(task)
        handler.post(task)
    }

    fun resetCount() {
        count = 0
    }

    fun stop() {
        isRunning = false
        isPaused = false
        handler.removeCallbacks(task)
    }

    fun pause() {
        if (!isRunning || isPaused) return

        isPaused = true
        handler.removeCallbacks(task)
    }

    fun resume() {
        if (!isRunning || !isPaused) return

        isPaused = false
        handler.post(task)
    }

    fun setInterval(milliseconds: Long) {
        require(milliseconds > 0) {
            "Interval must be positive"
        }

        interval = milliseconds
    }
}

/**
 *val interval = ktInterval(1000) { count ->
    println("Count: $count")
}

interval.start()
 */
fun ktInterval(
    interval: Long = 1000,
    block: (Long) -> Unit
): KtInterval {
    return KtInterval(interval, block)
}

//--------------------

class KtTimer(
    private var delay: Long = 1000,
    private val block: () -> Unit
) {
    private val handler = Handler(Looper.getMainLooper())

    var isRunning = false
    private set

    private val task = Runnable {
        isRunning = false
        block()
    }

    fun start() {
        if (isRunning) return

        isRunning = true
        handler.postDelayed(task, delay)
    }

    fun stop() {
        handler.removeCallbacks(task)
        isRunning = false
    }

    fun restart() {
        stop()
        start()
    }

    fun setDelay(milliseconds: Long) {
        require(milliseconds >= 0)
        delay = milliseconds
    }
}

/**
 *val timer = ktTimer(3000) {
    println("Time is up!")
}

timer.start()
 */
fun ktTimer(
    delay: Long = 1000,
    block: () -> Unit
): KtTimer {
    return KtTimer(delay, block)
}

enum class KtDirection {
    FORWARD,
    BACKWARD
}

class KtCounterConfig<T> {
    var items: MutableList<T> = mutableListOf()

    var direction: KtDirection = KtDirection.FORWARD

    var step: Int = 1
}

class KtCounter<T>(
    private val config: KtCounterConfig<T>
) {
    private var index = 0

    // Properties

    var direction: KtDirection
    get() = config.direction
    set(value) {
        config.direction = value
    }

    var step: Int
    get() = config.step
    set(value) {
        require(value > 0) { "Step must be greater than zero" }
        config.step = value
    }

    val items: List<T>
    get() = config.items

    val size: Int
    get() = config.items.size

    val isEmpty: Boolean
    get() = config.items.isEmpty()

    // Main counter

    fun count(): T {
        require(items.isNotEmpty()) {
            "KtCounter has no items"
        }

        require(step > 0) {
            "Step must be greater than zero"
        }

        val value = items[index]

        val movement = step % size

        index = if (direction == KtDirection.FORWARD) {
            (index + movement) % size
        } else {
            (index - movement + size) % size
        }

        return value
    }

    fun reverse() {
        direction = if (direction == KtDirection.FORWARD) {
            KtDirection.BACKWARD
        } else {
            KtDirection.FORWARD
        }
    }

    // Position methods

    fun reset() {
        index = 0
    }

    fun moveTo(position: Int) {
        require(position in items.indices) {
            "Invalid position: $position"
        }

        index = position
    }

    fun current(): T {
        require(items.isNotEmpty()) {
            "KtCounter has no items"
        }

        return items[index]
    }

    fun first(): T {
        require(items.isNotEmpty())
        return items.first()
    }

    fun last(): T {
        require(items.isNotEmpty())
        return items.last()
    }

    // Collection methods

    fun add(item: T) {
        config.items.add(item)
    }

    fun remove(item: T): Boolean {
        val position = index

        val removedIndex = config.items.indexOf(item)

        if (removedIndex == -1) return false

        config.items.removeAt(removedIndex)

        if (config.items.isEmpty()) {
            index = 0
        } else if (removedIndex < position) {
            index--
        } else if (index >= size) {
            index = 0
        }

        return true
    }

    fun clear() {
        config.items.clear()
        index = 0
    }

    fun toList(): List<T> = items.toList()
}

/**
val c = ktCounter {
items = mutableListOf("RED", "GREEN", "BLUE")
direction = KtDirection.FORWARD
step = 1
}.count()
*/
fun <T> ktCounter(): KtCounter<T> {
    return KtCounter(KtCounterConfig())
}

fun <T> ktCounter(
    items: MutableList<T>
): KtCounter<T> {
    return KtCounter(
        KtCounterConfig<T>().apply {
            this.items = items
        }
    )
}

fun <T> ktCounter(
    block: KtCounterConfig<T>.() -> Unit
): KtCounter<T> {
    return KtCounter(
        KtCounterConfig<T>().apply(block)
    )
}

class KtToggle<T>(
    private val a: T,
    private val b: T
) {
    private var togglevar: T? = null

    fun toggle(): T {
        return if (togglevar == null || togglevar == a) {
            togglevar = b
            b
        } else {
            togglevar = a
            a
        }
    }

}

/**
 val myToggle = ktToggle("ON", "OFF")

println(myToggle.toggle()) // OFF
println(myToggle.toggle()) // ON
println(myToggle.toggle()) // OFF
println(myToggle.toggle()) // ON
 */
fun <T> ktToggle(a: T, b: T): KtToggle<T> {
    return KtToggle(a, b)
}
