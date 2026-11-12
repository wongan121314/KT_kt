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

        KtFilePickerManager.onActivityResult(
            this,
            requestCode,
            resultCode,
            data
        )
    }

}

open class KtService : Service() {
    override fun onBind(intent: Intent): IBinder {
        TODO("Not yet implemented")
    }
}
