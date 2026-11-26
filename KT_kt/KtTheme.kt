
package com.KT_kt

import android.graphics.Color
import java.lang.ref.WeakReference
import android.content.res.Configuration

enum class KtThemeMode {
    DAY,
    NIGHT
}

internal  data class KtStyle(
    val day: Int,
    val night: Int
)

internal object KtThemeManager {

    private val styles = mutableMapOf<String, KtStyle>()
    private var followSystem = true
    var mode = KtThemeMode.DAY
    private set

    private var activity = WeakReference<KtActivity>(null)

    fun register(styles: Map<String, KtStyle>) {
        this.styles.clear()
        this.styles.putAll(styles)
    }

    fun attach(activity: KtActivity) {
        this.activity = WeakReference(activity)

        if (followSystem) {
            syncSystemTheme(activity)
        }
    }
    private fun syncSystemTheme(activity: KtActivity) {
        val nightMode = activity.resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK

        mode = if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            KtThemeMode.NIGHT
        } else {
            KtThemeMode.DAY
        }
    }

    fun detach(activity: KtActivity) {
        if (this.activity.get() === activity) {
            this.activity.clear()
        }
    }
    fun setMode(mode: KtThemeMode) {
        followSystem = false

        if (this.mode == mode) return

        this.mode = mode
        activity.get()?.recreate()
    }
    fun getColor(name: String): Int {
        val style = styles[name.lowercase()]

        if (style == null) {
            return defaultColor(name)
        }

        return if (mode == KtThemeMode.DAY) {
            style.day
        } else {
            style.night
        }
    }
    fun useSystem() {
        followSystem = true

        activity.get()?.let {
            val oldMode = mode
            syncSystemTheme(it)

            if (oldMode != mode) {
                it.recreate()
            }
        }
    }

    private fun defaultColor(name: String): Int {
        val pair = when (name.lowercase()) {
            "primary" -> Color.BLUE to Color.CYAN
            "secondary" -> Color.LTGRAY to Color.DKGRAY
            "background" -> Color.WHITE to Color.BLACK
            "text" -> Color.BLACK to Color.WHITE
            else -> Color.TRANSPARENT to Color.TRANSPARENT
        }

        return if (mode == KtThemeMode.DAY) pair.first else pair.second
    }
}

// Style registration

class KtStyleRegistry internal constructor(
    private val config: KtThemeConfig
) {
    operator fun get(name: String): (Int, Int) -> Unit {
        return { day, night ->
            config.registerStyle(name, day, night)
        }
    }
}

// Theme configuration

class KtThemeConfig {

    private val styles = mutableMapOf<String, KtStyle>()

    val setStyle = KtStyleRegistry(this)

    internal fun registerStyle(
        name: String,
        day: Int,
        night: Int
    ) {
        styles[name.lowercase()] = KtStyle(day, night)
    }

    operator fun component1(): KtTheme {
        KtThemeManager.register(styles)
        return KtTheme()
    }

    operator fun component2(): KtThemeSetter {
        return KtThemeSetter()
    }
}

// Theme colors

class KtTheme {

    val primary: Int
    get() = KtThemeManager.getColor("primary")

    val secondary: Int
    get() = KtThemeManager.getColor("secondary")

    val background: Int
    get() = KtThemeManager.getColor("background")

    val text: Int
    get() = KtThemeManager.getColor("text")

    operator fun get(name: String): Int {
        return KtThemeManager.getColor(name)
    }
}

// Theme controls

class KtThemeSetter {

    fun day() {
        KtThemeManager.setMode(KtThemeMode.DAY)
    }

    fun night() {
        KtThemeManager.setMode(KtThemeMode.NIGHT)
    }
    fun system() {
        KtThemeManager.useSystem()
    }
    fun change() {
        val next = if (KtThemeManager.mode == KtThemeMode.DAY) {
            KtThemeMode.NIGHT
        } else {
            KtThemeMode.DAY
        }

        KtThemeManager.setMode(next)
    }
}

// Factory

fun ktTheme(): KtThemeConfig {
    return KtThemeConfig()
}
