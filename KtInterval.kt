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
