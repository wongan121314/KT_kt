
@file:Suppress("ALL")

package com.KT_kt

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings

class KtIntent(
    internal  val context: Context
) : Intent() {
    constructor(
        context: Context,
        target: Class<*>
    ) : this(context) {
        setClass(context, target)
    }
    // -------------------------------
    // OPENING URI / WEBSITES
    // -------------------------------

    fun openUri(uri: Uri): KtIntent {
        action = Intent.ACTION_VIEW
        data = uri
        return this
    }

    fun openWebsite(url: String): KtIntent {
        return openUri(Uri.parse(url))
    }

    fun openBrowser(url: String): KtIntent {
        return openWebsite(url)
    }

    // -------------------------------
    // COMMUNICATION
    // -------------------------------

    fun openEmail(email: String): KtIntent {
        action = Intent.ACTION_SENDTO
        data = Uri.fromParts("mailto", email, null)
        return this
    }

    fun sendEmail(
        email: String,
        subject: String = "",
        body: String = ""
    ): KtIntent {
        action = Intent.ACTION_SENDTO
        data = Uri.fromParts("mailto", email, null)

        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)

        return this
    }

    fun dial(phone: String): KtIntent {
        action = Intent.ACTION_DIAL
        data = Uri.fromParts("tel", phone, null)
        return this
    }

    fun makeCall(phone: String): KtIntent {
        action = Intent.ACTION_CALL
        data = Uri.fromParts("tel", phone, null)
        return this
    }

    fun sendSms(
        phone: String,
        message: String = ""
    ): KtIntent {
        action = Intent.ACTION_SENDTO
        data = Uri.fromParts("smsto", phone, null)
        putExtra("sms_body", message)
        return this
    }

    // -------------------------------
    // SHARING
    // -------------------------------

    fun shareText(message: String): KtIntent {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
        return this
    }

    fun shareFile(
        uri: Uri,
        mimeType: String = "*/*"
    ): KtIntent {
        action = Intent.ACTION_SEND
        type = mimeType
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return this
    }

    fun shareImage(uri: Uri): KtIntent {
        return shareFile(uri, "image/*")
    }

    fun shareMultiple(
        uris: List<Uri>,
        mimeType: String = "*/*"
    ): KtIntent {
        action = Intent.ACTION_SEND_MULTIPLE
        type = mimeType

        putParcelableArrayListExtra(
            Intent.EXTRA_STREAM,
            ArrayList(uris)
        )

        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return this
    }

    // -------------------------------
    // MAPS
    // -------------------------------

    fun openMaps(location: String): KtIntent {
        action = Intent.ACTION_VIEW
        data = Uri.parse(
            "geo:0,0?q=${Uri.encode(location)}"
        )
        return this
    }

    fun showLocation(latitude: Double, longitude: Double): KtIntent {
        action = Intent.ACTION_VIEW
        data = Uri.parse("geo:$latitude,$longitude")
        return this
    }

    fun navigateTo(latitude: Double, longitude: Double): KtIntent {
        action = Intent.ACTION_VIEW
        data = Uri.parse("google.navigation:q=$latitude,$longitude")
        return this
    }

    // -------------------------------
    // ANDROID SETTINGS
    // -------------------------------

    fun openSettings(): KtIntent {
        action = Settings.ACTION_SETTINGS
        return this
    }

    fun openAppSettings(): KtIntent {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts(
            "package",
            context.packageName,
            null
        )
        return this
    }

    // -------------------------------
    // OPENING APPLICATIONS
    // -------------------------------

    fun openApp(packageName: String): KtIntent {
        val launchIntent = context.packageManager
        .getLaunchIntentForPackage(packageName)
        ?: throw IllegalArgumentException(
            "No launchable application found: $packageName"
        )

        val component = launchIntent.component
        ?: throw IllegalStateException(
            "Application has no launchable component"
        )

        setClassName(
            component.packageName,
            component.className
        )

        return this
    }

    //others
    // ALARM AND CLOCK

    fun openAlarm() {
        action = AlarmClock.ACTION_SHOW_ALARMS
    }

    fun setAlarm(
        hour: Int,
        minutes: Int,
        message: String = "Alarm"
    ) {
        action = AlarmClock.ACTION_SET_ALARM
        putExtra(AlarmClock.EXTRA_HOUR, hour)
        putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        putExtra(AlarmClock.EXTRA_MESSAGE, message)
    }

    fun openTimer() {
        action = AlarmClock.ACTION_SET_TIMER
    }

    fun openClock() {
        action = AlarmClock.ACTION_SHOW_ALARMS
    }

    // CALENDAR

    fun openCalendar() {
        action = Intent.ACTION_VIEW
        data = CalendarContract.CONTENT_URI
    }

    fun createCalendarEvent(
        title: String,
        description: String = "",
        beginTime: Long,
        endTime: Long
    ) {
        action = Intent.ACTION_INSERT
        data = CalendarContract.Events.CONTENT_URI

        putExtra(CalendarContract.Events.TITLE, title)
        putExtra(CalendarContract.Events.DESCRIPTION, description)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
    }

    // CALCULATOR

    fun openCalculator() {
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_APP_CALCULATOR)
    }

    // CONTACTS

    fun openContacts() {
        action = Intent.ACTION_VIEW
        data = ContactsContract.Contacts.CONTENT_URI
    }

    // PHONE

    fun openPhone() {
        action = Intent.ACTION_DIAL
    }

    // SETTINGS

    // fun openSettings() {
    //     action = Settings.ACTION_SETTINGS
    // }

    fun openDateSettings() {
        action = Settings.ACTION_DATE_SETTINGS
    }

    // -------------------------------
    // ACTIVITY NAVIGATION
    // -------------------------------

    fun openActivity(
        target: Class<out Activity>
    ): KtIntent {
        setClass(context, target)
        return this
    }

    fun openActivityAndClearStack(
        target: Class<out Activity>
    ): KtIntent {
        setClass(context, target)

        addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        )

        return this
    }

    fun pickFile(mimeType: String = "*/*"): KtIntent {
        action = Intent.ACTION_GET_CONTENT
        type = mimeType
        return this
    }

    fun pickContact(): KtIntent {
        action = Intent.ACTION_PICK
        data = ContactsContract.Contacts.CONTENT_URI
        return this
    }
    fun openDocument(mimeType: String = "*/*"): KtIntent {
        action = Intent.ACTION_OPEN_DOCUMENT
        type = mimeType
        addCategory(Intent.CATEGORY_OPENABLE)
        return this
    }

    fun createDocument(mimeType: String, fileName: String): KtIntent {
        action = Intent.ACTION_CREATE_DOCUMENT
        type = mimeType
        addCategory(Intent.CATEGORY_OPENABLE)
        putExtra(Intent.EXTRA_TITLE, fileName)
        return this
    }

    fun captureImage(outputUri: Uri): KtIntent {
        action = MediaStore.ACTION_IMAGE_CAPTURE
        putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return this
    }

    fun captureVideo(outputUri: Uri): KtIntent {
        action = MediaStore.ACTION_VIDEO_CAPTURE
        putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        return this
    }

    fun openWifiSettings(): KtIntent {
        action = Settings.ACTION_WIFI_SETTINGS
        return this
    }

    fun openBluetoothSettings(): KtIntent {
        action = Settings.ACTION_BLUETOOTH_SETTINGS
        return this
    }

    fun openLocationSettings(): KtIntent {
        action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
        return this
    }

    fun openDisplaySettings(): KtIntent {
        action = Settings.ACTION_DISPLAY_SETTINGS
        return this
    }

    fun closeActivity() {
        (context as? Activity)?.finish()
    }

    // testing
    fun pickFile(
        type: String = "*/*",
        multiple: Boolean = false
    ): KtIntent {

        action = Intent.ACTION_OPEN_DOCUMENT
        this.type = type

        addCategory(Intent.CATEGORY_OPENABLE)
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiple)

        return this
    }

    private var resultCallback: ((KtActivityResult) -> Unit)? = null
    fun setOnResultListener(
        callback: (KtActivityResult) -> Unit
    ): KtIntent {

        resultCallback = callback

        return this
    }
    // -------------------------------
    // START
    // -------------------------------

    fun start(chooserTitle: String? = null) {
        val intentToStart = if (chooserTitle != null) {
            Intent.createChooser(this, chooserTitle)
        } else {
            this
        }

        if (context !is Activity) {
            intentToStart.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }

        context.startActivity(intentToStart)
    }

    // -------------------------------
    // CLOSE ACTIVITY
    // -------------------------------

    fun closeApp() {
        (context as? Activity)?.finish()
    }

    // -------------------------------
    // ACTIVITY AND SERVICE STARTING
    // -------------------------------

    fun startActivity() {
        if (context !is Activity) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(this)
    }

    fun startService(): ComponentName? {
        return context.startService(this)
    }

    fun startForegroundService(): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(this)
        } else {
            context.startService(this)
        }
    }

    fun stopService(): Boolean {
        return context.stopService(this)
    }
    fun startForResult(): KtIntent {

        val activity = context as? Activity
        ?: throw IllegalStateException(
            "Activity context required"
        )

        val callback = resultCallback
        ?: throw IllegalStateException(
            "Call setOnResultListener before startForResult"
        )

        KtActivityResultManager.launch(
            activity,
            this
        ) { result ->

            callback(result)
        }

        return this
    }
}

// -----------------------------------
// FACTORY FUNCTION
// -----------------------------------

fun Context.ktIntent(
    block: KtIntent.() -> Unit = {}
): KtIntent {
    return KtIntent(this).apply(block)
}

fun Context.ktIntent(
    target: Class<*>,
    block: KtIntent.() -> Unit = {}
): KtIntent {
    return KtIntent(this, target).apply(block)
}
