@file:Suppress("ALL")

package com.KT_kt

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri

fun Cursor.index(column: String): Int =
getColumnIndexOrThrow(column)

fun Cursor.string(column: String): String? =
getString(index(column))

fun Cursor.int(column: String): Int =
getInt(index(column))

fun Cursor.long(column: String): Long =
getLong(index(column))

fun Cursor.float(column: String): Float =
getFloat(index(column))

fun Cursor.double(column: String): Double =
getDouble(index(column))

fun Cursor.blob(column: String): ByteArray =
getBlob(index(column))

fun Cursor.isNull(column: String): Boolean =
isNull(index(column))

fun Cursor.next(): Boolean = moveToNext()

fun Cursor.first(): Boolean = moveToFirst()

fun Cursor.last(): Boolean = moveToLast()

fun Cursor.rows(): Int = count

fun Cursor.columns(): Int = columnCount

fun Cursor.hasColumn(column: String): Boolean =
    getColumnIndex(column) >= 0

fun Uri.withId(id: Long): Uri =
    ContentUris.withAppendedId(this, id)