@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * ktVibration class, create vibrationns
 * @constructor ktVibration
 * 
 * **WARNING: Do not use this class directly.**
 * Use `Context.ktVibration()` instead.
 *              
 */
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

    @Deprecated(
        message = "Use stop() instead",
        replaceWith = ReplaceWith("stop()"),
        level = DeprecationLevel.WARNING
    )
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

/**
 * create vibration once
 * @param pattern:Int
 * @return KtVibration
 *         
 * ```
 * val vibration = ktVibration(200)
 * vibration.start()
 * vibration.stop()
 * ```
 */
fun Context.ktVibration(duration: Int): KtVibration {
    return KtVibration.create(this, duration)
}

/**
 * create vibration effects
 * @param pattern:LongArray
 * @return KtVibration
 *         
 * ```
 * val vibration = ktVibration(LongArrayOf(200,0,111))
 * vibration.start()
 * vibration.stop()
 * ```
 */
fun Context.ktVibration(pattern: LongArray): KtVibration {
    return KtVibration.create(this, pattern)
}
