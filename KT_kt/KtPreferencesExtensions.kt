package com.KT_kt

import android.content.Context
import android.content.SharedPreferences


fun Context.KtPreferences(
    name: String = "KtPreferences",
    mode: Int = Context.MODE_PRIVATE
): SharedPreferences {
    return getSharedPreferences(name, mode)
}
// CREATE / UPDATE

fun SharedPreferences.putString(key: String, value: String) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.putInt(key: String, value: Int) {
    edit().putInt(key, value).apply()
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}

fun SharedPreferences.putFloat(key: String, value: Float) {
    edit().putFloat(key, value).apply()
}

fun SharedPreferences.putLong(key: String, value: Long) {
    edit().putLong(key, value).apply()
}

// DELETE

fun SharedPreferences.delete(key: String) {
    edit().remove(key).apply()
}

// CLEAR

fun SharedPreferences.clearAll() {
    edit().clear().apply()
}

// CHECK

fun SharedPreferences.has(key: String): Boolean {
    return contains(key)
}
