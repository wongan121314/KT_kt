
@file:Suppress("ALL")

package com.KT_kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class KtIntent(
    private val context: Context
) : Intent() {

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
}

// -----------------------------------
// FACTORY FUNCTION
// -----------------------------------

fun Context.ktIntent(
    block: KtIntent.() -> Unit = {}
): KtIntent {
    return KtIntent(this).apply(block)
}
