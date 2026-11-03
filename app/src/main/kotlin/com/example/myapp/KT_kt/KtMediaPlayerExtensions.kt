package com.KT_kt



import android.content.Context
import android.media.MediaPlayer
import android.net.Uri


fun Context.KtMediaPlayer(
    block: MediaPlayer.() -> Unit = {}
): MediaPlayer {
    return MediaPlayer().apply(block)
}


fun MediaPlayer.setFile(path: String) {
    setDataSource(path)
}

fun MediaPlayer.setFile(context: Context, uri: Uri) {
    setDataSource(context, uri)
}

fun MediaPlayer.play() {
    start()
}

fun MediaPlayer.clear() {
    reset()
}

fun MediaPlayer.stopPlayback() {
    stop()
}