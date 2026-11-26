@file:Suppress("ALL")

package com.KT_kt

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder

import java.util.WeakHashMap
import java.util.concurrent.atomic.AtomicInteger

open class KtActivity : Activity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KtContext.context = applicationContext

        KtThemeManager.attach(this)

        actionBar?.hide()

        onCreate()
    }
    open fun onCreate() {

        // Override this in subclasses.
    }
    override fun onDestroy() {
        KtThemeManager.detach(this)
        super.onDestroy()
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        KtActivityResultManager.onActivityResult(
            this,
            requestCode,
            resultCode,
            data
        )
    }

}

/*
 * service,start a background or foreground service, and call using intents
 */
open class KtService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        return onStartCommand(intent)
    }

    open fun onStartCommand(intent: Intent?): Int {
        return START_NOT_STICKY
    }

}

//--

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
