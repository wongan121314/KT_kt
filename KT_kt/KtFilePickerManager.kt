@file:Suppress("ALL", "kt.usePropertyAccess", "package.mismatch")
package com.KT_kt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import java.util.WeakHashMap
import java.util.concurrent.atomic.AtomicInteger

@Deprecated("old")
object KtFilePickerManager {

    private val callbacks =
    WeakHashMap<Activity, MutableMap<Int, (List<Uri>) -> Unit>>()

    private val counter = AtomicInteger(100)

    fun launch(
        activity: Activity,
        type: String,
        multiple: Boolean,
        callback: (List<Uri>) -> Unit
    ) {
        val requestCode = counter.getAndIncrement()

        callbacks.getOrPut(activity) { mutableMapOf() }[requestCode] = callback

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            this.type = type
            addCategory(Intent.CATEGORY_OPENABLE)

            if (multiple) {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            } else {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            }
        }

        try {
            activity.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            callbacks[activity]?.remove(requestCode)
            callback(emptyList())
        }
    }

    fun onActivityResult(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val callback = callbacks[activity]?.remove(requestCode)
        ?: return

        val uris = mutableListOf<Uri>()

        if (resultCode == Activity.RESULT_OK && data != null) {

            data.clipData?.let { clip ->
                for (i in 0 until clip.itemCount) {
                    clip.getItemAt(i).uri?.let { uris.add(it) }
                }
            }

            if (uris.isEmpty()) {
                data.data?.let { uris.add(it) }
            }
        }

        callback(uris)
    }
}

class KtFilePickerRequest(
    private val activity: Activity,
    private val type: String,
    private val multiple: Boolean
) {

    private var fileListener: ((Uri) -> Unit)? = null
    private var filesListener: ((List<Uri>) -> Unit)? = null

    fun setOnFileSelectedListener(
        listener: (Uri) -> Unit
    ): KtFilePickerRequest {
        fileListener = listener
        return this
    }

    fun setOnFilesSelectedListener(
        listener: (List<Uri>) -> Unit
    ): KtFilePickerRequest {
        filesListener = listener
        return this
    }

    fun launch(): KtFilePickerRequest {

        KtFilePickerManager.launch(
            activity,
            type,
            multiple
        ) { uris ->

            if (multiple) {
                filesListener?.invoke(uris)
            } else {
                uris.firstOrNull()?.let {
                    fileListener?.invoke(it)
                }
            }
        }

        return this
    }
}
