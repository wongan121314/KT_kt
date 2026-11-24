@file:Suppress("ALL")

package com.KT_kt

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

open class KtActivity : Activity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()
        onCreate()

    }
    open fun onCreate() {

        // Override this in subclasses.
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
