package com.KT_kt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import java.util.WeakHashMap
import java.util.concurrent.atomic.AtomicInteger

object KtActivityResultManager {

    private val callbacks =
    WeakHashMap<Activity, MutableMap<Int, (KtActivityResult) -> Unit>>()

    private val counter = AtomicInteger(1000)

    fun launch(
        activity: Activity,
        intent: Intent,
        callback: (KtActivityResult) -> Unit
    ) {
        val requestCode = counter.getAndIncrement()

        callbacks.getOrPut(activity) { mutableMapOf() }[requestCode] = callback

        try {
            activity.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {

            callbacks[activity]?.remove(requestCode)

            callback(
                KtActivityResult(
                    Activity.RESULT_CANCELED,
                    null
                )
            )
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

        callback(KtActivityResult(resultCode, data))
    }
}

data class KtActivityResult(
    val resultCode: Int,
    val data: Intent?
) {

    val isSuccess: Boolean
    get() = resultCode == Activity.RESULT_OK

    val isCancelled: Boolean
    get() = resultCode == Activity.RESULT_CANCELED

    val uri: Uri?
    get() = data?.data

    val uris: List<Uri>
    get() {
        val result = mutableListOf<Uri>()

        data?.clipData?.let { clip ->
            for (i in 0 until clip.itemCount) {
                result.add(clip.getItemAt(i).uri)
            }
        }

        if (result.isEmpty()) {
            data?.data?.let { result.add(it) }
        }

        return result
    }

    val intent: Intent?
    get() = data
}
