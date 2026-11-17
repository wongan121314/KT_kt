package com.KT_kt

import android.os.Handler
import android.os.Looper

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