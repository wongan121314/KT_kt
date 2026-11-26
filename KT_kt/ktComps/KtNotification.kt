package com.KT_kt

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build

@Suppress("kt.usePropertyAccess", "kt.deprecation")
class KtNotification(
    private val context: Context
) {

    private var channelId = "kt_general"
    private var channelName = "General Notifications"

    private var title = "Notification"
    private var message = ""

    private var smallIcon = android.R.drawable.ic_dialog_info

    private var notificationId = 100
    private var autoCancel = true
    private var ongoing = false

    private var progressMax = 0
    private var progressValue = 0
    private var indeterminate = false

    private var bigText: String? = null

    fun setTitle(value: String) = apply {
        title = value
    }

    fun setMessage(value: String) = apply {
        message = value
    }

    fun setIcon(value: Int) = apply {
        smallIcon = value
    }

    fun setId(value: Int) = apply {
        notificationId = value
    }

    fun setChannel(
        id: String,
        name: String
    ) = apply {
        channelId = id
        channelName = name
    }

    /*
     * for service notification and showForeground fun 
     */
    fun build(): Notification {

        createChannel()

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
            .setPriority(Notification.PRIORITY_DEFAULT)
        }

        builder
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(smallIcon)
        .setAutoCancel(autoCancel)
        .setOngoing(ongoing)

        if (progressMax > 0) {
            builder.setProgress(
                progressMax,
                progressValue,
                indeterminate
            )
        }

        bigText?.let {
            builder.setStyle(
                Notification.BigTextStyle().bigText(it)
            )
        }

        return builder.build()
    }
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = context.getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(channel)
        }
    }

    fun setAutoCancel(value: Boolean) = apply {
        autoCancel = value
    }

    fun setOngoing(value: Boolean) = apply {
        ongoing = value
    }

    fun setProgress(
        max: Int,
        progress: Int,
        indeterminate: Boolean = false
    ) = apply {
        progressMax = max
        progressValue = progress
        this.indeterminate = indeterminate
    }

    fun setBigText(value: String) = apply {
        bigText = value
    }

    fun show(): KtNotification {

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.notify(notificationId, build())

        return this
    }

    /**
 * class MyForegroundService : KtService() {

    override fun onStartCommand(intent: Intent?): Int {

        ktNotification {
            setTitle("KT_kt")
            setMessage("Service is running")
            setChannel("messages", "Messages")
            setOngoing(true)
        }.showForeground()

        return START_STICKY
    }
}
 */
    fun showForeground(): KtNotification {

        val service = context as? Service
        ?: throw IllegalStateException(
            "showForeground() must be called from a Service context"
        )

        service.startForeground(
            notificationId,
            build()
        )

        return this
    }

    fun cancel(): KtNotification {
        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.cancel(notificationId)

        return this
    }
}

fun Context.ktNotification(
    block: KtNotification.() -> Unit = {}
): KtNotification {

    return KtNotification(this).apply(block)
}
