@file:Suppress("ALL")

package com.KT_kt

import android.app.usage.UsageStats
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class KtVibration private constructor(
    private val vibrator: Vibrator,
    private val effect: VibrationEffect
) {

    private constructor(
        context: Context,
        duration: Int
    ) : this(
        getVibrator(context),
        VibrationEffect.createOneShot(
            duration.toLong(),
            VibrationEffect.DEFAULT_AMPLITUDE
        )
    )

    private constructor(
        context: Context,
        pattern: LongArray
    ) : this(
        getVibrator(context),
        VibrationEffect.createWaveform(pattern, -1)
    )

    fun start() {
        vibrator.vibrate(effect)
    }

    fun cancel() {
        vibrator.cancel()
    }

    companion object {

        private fun getVibrator(context: Context): Vibrator {
            return if (Build.VERSION.SDK_INT >= 31) {
                context.getSystemService(VibratorManager::class.java)
                    .defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        }

        fun create(context: Context, duration: Int) =
            KtVibration(context, duration)

        fun create(context: Context, pattern: LongArray) =
            KtVibration(context, pattern)
    }
}

// what must be used

fun Context.ktVibration(duration: Int): KtVibration {
    return KtVibration.create(this, duration)
}

fun Context.ktVibration(pattern: LongArray): KtVibration {
    return KtVibration.create(this, pattern)
}


/**Usage 
 * 
    val vibration = ktVibration(500)
    vibration.start()
    vibration.stop()
 * 
 * or 
    val vibration = ktVibration(longArrayOf(0, 200, 100, 300))

    vibration.start()
    vibration.cancel()
 */