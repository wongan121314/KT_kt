package com.KT_kt

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

open class Kt_Vibrator0 private constructor(
    private val vibrator: Vibrator,
    private val effect: VibrationEffect
) {

    constructor(context: Context, duration: Int) : this(
        getVibrator(context),
        VibrationEffect.createOneShot(
            duration.toLong(),
            VibrationEffect.DEFAULT_AMPLITUDE
        )
    )

    constructor(context: Context, pattern: LongArray) : this(
        getVibrator(context),
        VibrationEffect.createWaveform(pattern, -1)
    )

    fun vibrate() {
        vibrator.vibrate(effect)
    }

    fun cancel() {
        vibrator.cancel()
    }

    companion object {
        private fun getVibrator(context: Context): Vibrator {
            return if (Build.VERSION.SDK_INT >= 31) {
                context.getSystemService(VibratorManager::class.java).defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        }
    }
}

class Kt_Vibrator private constructor(
    private val vibrator: Kt_Vibrator0
) {

    constructor(context: Context, duration: Int) :
    this(Kt_Vibrator0(context, duration))

    constructor(context: Context, pattern: LongArray) :
    this(Kt_Vibrator0(context, pattern))

    fun vibrate() {
        vibrator.vibrate()
    }

    fun cancel() {
        vibrator.cancel()
    }
}
