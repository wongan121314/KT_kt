package com.KT_kt

import android.graphics.Color as AndroidColor
import kotlin.math.abs

open class Kt_Color {

    // RGB
    fun rgb(r: Int, g: Int, b: Int): Int {
        return AndroidColor.rgb(r, g, b)
    }

    // RGBA
    fun rgba(r: Int, g: Int, b: Int, a: Int): Int {
        return AndroidColor.argb(a, r, g, b)
    }

    // HEX
    fun hex(c: String): Int {
        return AndroidColor.parseColor(c)
    }

    // HSV
    fun hsv(h: Float, s: Float, v: Float): Int {
        return AndroidColor.HSVToColor(floatArrayOf(h, s, v))
    }

    // HSVA
    fun hsva(h: Float, s: Float, v: Float, a: Int): Int {
        return AndroidColor.HSVToColor(
            a,
            floatArrayOf(h, s, v)
        )
    }

    // HSL
    fun hsl(h: Float, s: Float, l: Float): Int {
        val c = (1 - abs(2 * l - 1)) * s
        val x = c * (1 - abs((h / 60) % 2 - 1))
        val m = l - c / 2

        val (r, g, b) = when {
            h < 60 -> Triple(c, x, 0f)
            h < 120 -> Triple(x, c, 0f)
            h < 180 -> Triple(0f, c, x)
            h < 240 -> Triple(0f, x, c)
            h < 300 -> Triple(x, 0f, c)
            else -> Triple(c, 0f, x)
        }

        return rgb(
            ((r + m) * 255).toInt(),
            ((g + m) * 255).toInt(),
            ((b + m) * 255).toInt()
        )
    }
}
