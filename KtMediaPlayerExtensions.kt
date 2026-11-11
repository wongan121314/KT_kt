@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class KtMediaPlayer(
    private val context: Context
) : MediaPlayer() {

    fun setFile(path: String) {
        reset()
        setDataSource(path)
    }

    fun setFile(uri: Uri) {
        reset()
        setDataSource(context, uri)
    }

    fun play() {
        setOnPreparedListener {
            start()
        }

        prepareAsync()
    }
}

fun Context.ktMediaPlayer(
    block: KtMediaPlayer.() -> Unit = {}
): KtMediaPlayer {
    return KtMediaPlayer(this).apply(block)
}
