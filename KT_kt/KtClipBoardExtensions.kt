@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager

fun Context.ktClipboard(): ClipboardManager {
    return getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}

fun ClipboardManager.copy(text: String) {
    setPrimaryClip(
        ClipData.newPlainText("text", text)
    )
}

fun ClipboardManager.paste(): CharSequence? {
    return primaryClip?.getItemAt(0)?.text
}

fun ClipboardManager.clear() {
    clearPrimaryClip()
}

/**
    class MainActivity : KtActivity() {

    override fun onCreate() {
        super.onCreate()

        val clipboard = ktClipboard()

        // Copy
        clipboard.copy("Hello from KT_kt")

        // Paste
        val copiedText = clipboard.paste()

        // Display copied text
        println(copiedText)

        // Clear
        clipboard.clear()
    }
    }
 */