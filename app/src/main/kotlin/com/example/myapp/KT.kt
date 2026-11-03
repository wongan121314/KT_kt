package com.example.myapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

open class ktActivity : Activity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
    }

    open fun onCreate() {
        // Override this in subclasses.
    }
}

//---
fun Context.Button(
    block: Button.() -> Unit = {}
): Button {
    return Button(this).apply(block)
}

fun Context.LinearLayout(
    block: LinearLayout.() -> Unit = {}
): LinearLayout {

    return LinearLayout(this).apply(block)
}

fun Context.TextView(
    block: TextView.() -> Unit = {}
): TextView {
    return TextView(this).apply(block)
}

fun Context.EditText(
    block: EditText.() -> Unit = {}
): EditText {
    return EditText(this).apply(block)
}

fun Context.ImageView(
    block: ImageView.() -> Unit = {}
): ImageView {
    return ImageView(this).apply(block)
}

fun Context.CheckBox(
    block: CheckBox.() -> Unit = {}
): CheckBox {
    return CheckBox(this).apply(block)
}

fun Context.Switch(
    block: Switch.() -> Unit = {}
): Switch {
    return Switch(this).apply(block)
}

fun Context.ProgressBar(
    block: ProgressBar.() -> Unit = {}
): ProgressBar {
    return ProgressBar(this).apply(block)
}

fun <T> Context.ListView(
    items: List<T>,
    itemView: Context.(T) -> View
): ListView {
    return ListView(this).apply {
        adapter = object : ArrayAdapter<T>(this@ListView, 0, items) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return itemView(context, getItem(position)!!)
            }
        }
    }
}

//---

fun Context.Toast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
): Toast {
    return Toast.makeText(this, message, duration)
}

fun Context.Dialog(
    block: Dialog.() -> Unit = {}
): Dialog {
    return Dialog(this).apply(block)
}

class Shape(
    block: Shape.() -> Unit = {}
) : GradientDrawable() {

    init {
        apply(block)
    }
}
