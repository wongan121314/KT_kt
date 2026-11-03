package com.KT_kt

import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager

fun Context.KtClipboard(): ClipboardManager {
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