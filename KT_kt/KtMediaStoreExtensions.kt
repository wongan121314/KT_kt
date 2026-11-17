@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import android.annotation.SuppressLint

fun Context.ktAudioStore(): Uri =
MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

fun Context.ktVideoStore(): Uri =
MediaStore.Video.Media.EXTERNAL_CONTENT_URI

fun Context.ktImageStore(): Uri =
MediaStore.Images.Media.EXTERNAL_CONTENT_URI

fun Context.ktdownloadStore(): Uri =
MediaStore.Downloads.EXTERNAL_CONTENT_URI

fun Context.ktFileStore(): Uri =
MediaStore.Files.getContentUri("external")

@SuppressLint("NewApi")
fun Context.ktThumbnail(
    uri: Uri,
    width: Int = 200,
    height: Int = 200
): Bitmap {

    return contentResolver.loadThumbnail(
        uri,
        Size(width, height),
        null
    )
}

object KtAudioStore {

    val URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    val ID = MediaStore.Audio.Media._ID
    val TITLE = MediaStore.Audio.Media.TITLE
    val ARTIST = MediaStore.Audio.Media.ARTIST
    val ALBUM = MediaStore.Audio.Media.ALBUM
    val DURATION = MediaStore.Audio.Media.DURATION
    val SIZE = MediaStore.Audio.Media.SIZE
    val DISPLAY_NAME = MediaStore.Audio.Media.DISPLAY_NAME
    val ALBUM_ID = MediaStore.Audio.Media.ALBUM_ID
    val ALBUM_ARTIST = MediaStore.Audio.Media.ALBUM_ARTIST
    val MIME_TYPE = MediaStore.Audio.Media.MIME_TYPE
    val DATE_ADDED = MediaStore.Audio.Media.DATE_ADDED
    val DATE_MODIFIED = MediaStore.Audio.Media.DATE_MODIFIED
}

object KtVideoStore {
    val URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

    val ID = MediaStore.Video.Media._ID
    val TITLE = MediaStore.Video.Media.TITLE
    val DURATION = MediaStore.Video.Media.DURATION
    val SIZE = MediaStore.Video.Media.SIZE
    val WIDTH = MediaStore.Video.Media.WIDTH
    val HEIGHT = MediaStore.Video.Media.HEIGHT
    val DISPLAY_NAME = MediaStore.Video.Media.DISPLAY_NAME
    val MIME_TYPE = MediaStore.Video.Media.MIME_TYPE
    val DATE_ADDED = MediaStore.Video.Media.DATE_ADDED
    val DATE_MODIFIED = MediaStore.Video.Media.DATE_MODIFIED
}

object KtImageStore {
    val URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val ID = MediaStore.Images.Media._ID
    val TITLE = MediaStore.Images.Media.TITLE
    val DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME
    val SIZE = MediaStore.Images.Media.SIZE
    val WIDTH = MediaStore.Images.Media.WIDTH
    val HEIGHT = MediaStore.Images.Media.HEIGHT
    val MIME_TYPE = MediaStore.Images.Media.MIME_TYPE
    val DATE_ADDED = MediaStore.Images.Media.DATE_ADDED
    val DATE_MODIFIED = MediaStore.Images.Media.DATE_MODIFIED
}

object KtDownloadStore {
    val URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI

    val ID = MediaStore.Downloads._ID
    val DISPLAY_NAME = MediaStore.Downloads.DISPLAY_NAME
    val TITLE = MediaStore.Downloads.TITLE
    val SIZE = MediaStore.Downloads.SIZE
    val MIME_TYPE = MediaStore.Downloads.MIME_TYPE
    val DATE_ADDED = MediaStore.Downloads.DATE_ADDED
    val DATE_MODIFIED = MediaStore.Downloads.DATE_MODIFIED
}

object KtFileStore {
    val URI = MediaStore.Files.getContentUri("external")

    val ID = MediaStore.MediaColumns._ID
    val DISPLAY_NAME = MediaStore.MediaColumns.DISPLAY_NAME
    val TITLE = MediaStore.MediaColumns.TITLE
    val SIZE = MediaStore.MediaColumns.SIZE
    val MIME_TYPE = MediaStore.MediaColumns.MIME_TYPE
    val DATE_ADDED = MediaStore.MediaColumns.DATE_ADDED
    val DATE_MODIFIED = MediaStore.MediaColumns.DATE_MODIFIED
}

/**
* ```
UsageStatsval cursor = contentResolver.query(
KtAudioStore.URI,
arrayOf(
KtAudioStore.ID,
KtAudioStore.TITLE,
KtAudioStore.ARTIST
),
null,
null,
null
)
* ```
*/
