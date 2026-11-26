package com.KT_kt

import android.R.id
import android.graphics.drawable.ColorDrawable
import android.opengl.ETC1
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import java.util.WeakHashMap

@Deprecated("does not work")
fun View.freeze(status: Boolean = true): View {
    isSaveEnabled = status
    isSaveFromParentEnabled = status
    return this
}


private data class ViewSnapshot(
    val alpha: Float,
    val rotation: Float,
    val visibility: Int,
    val enabled: Boolean,

    // TextView-specific properties
    val text: String?,
    val textColor: Int?,
    val textSize: Float?,

    // Solid background only
    val backgroundColor: Int?
)

/**
 * **dont use this**
 */
object FreezeMe {

    private val savedStates = mutableMapOf<Int, ViewSnapshot>()

    private val listeners =
    WeakHashMap<View, View.OnAttachStateChangeListener>()

    private val colorStore = mutableMapOf<Int, Int>()
    private val backgroundStore = mutableMapOf<Int, Int>()
    private val textSizeStore = mutableMapOf<Int, Float>()

    fun store(id: Int, color: Int) {
        colorStore[id] = color
    }

    fun retrieve(id: Int): Int? = colorStore[id]

    //---
    fun storeBackground(id: Int, color: Int) {
        backgroundStore[id] = color
    }

    fun retrieveBackground(id: Int): Int? {
        return backgroundStore[id]
    }

    //--
    fun storeTextSize(id: Int, size: Float) {
        textSizeStore[id] = size
    }

    fun retrieveTextSize(id: Int): Float? {
        return textSizeStore[id]
    }
    fun isFrozen(view: View): Boolean {
        return listeners.containsKey(view)
    }

    fun unfreeze(view: View) {
        listeners.remove(view)?.let {
            view.removeOnAttachStateChangeListener(it)
        }
    }

    fun freeze(view: View) {

        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }

        if (listeners.containsKey(view)) return

        view.isSaveEnabled = true

        val listener = object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                restore(v)
            }

            override fun onViewDetachedFromWindow(v: View) {
                save(v)
            }
        }

        listeners[view] = listener
        view.addOnAttachStateChangeListener(listener)
    }

    fun save(view: View) {

        val tv = view as? TextView

        val backgroundColor =
        (view.background as? ColorDrawable)?.color

        savedStates[view.id] = ViewSnapshot(
            alpha = view.alpha,
            rotation = view.rotation,
            visibility = view.visibility,
            enabled = view.isEnabled,

            text = tv?.text?.toString(),
            textColor = tv?.currentTextColor,
            textSize = tv?.textSize,

            backgroundColor = backgroundColor
        )
    }

    fun restore(view: View) {

        val state = savedStates[view.id] ?: return

        view.alpha = state.alpha
        view.rotation = state.rotation
        view.visibility = state.visibility
        view.isEnabled = state.enabled

        (view as? TextView)?.let { tv ->

            state.text?.let {
                tv.text = it
            }

            state.textColor?.let {
                tv.setTextColor(it)
            }

            state.textSize?.let {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
            }
        }

        state.backgroundColor?.let {
            view.setBackgroundColor(it)
        }
    }

    fun clear(id: Int) {
        savedStates.remove(id)
    }
}

/**
 * freese the whole object ,helps to prevent loss of important propeties like color ,text ,size etc 
 * ```freezesMe =true/false```
 * set it to true and make sure the view has an id
 */
var View.freezesMe: Boolean
get() = FreezeMe.isFrozen(this)
set(value) {
    if (value) {
        FreezeMe.freeze(this)
    } else {
        FreezeMe.unfreeze(this)
    }
}

private val registeredViews =
java.util.Collections.newSetFromMap(
    java.util.WeakHashMap<TextView, Boolean>()
)

/**
 * View.freezesTextColor  //remember to give the view an id 
 * @param Boolean
 * freeze the state of the view upon Activity recreation
 * ```
 * ktTextView().apply {
 *   id = 10
 *   freezesTextColor =true
 * } 
 * ```
 */
var TextView.freezesTextColor: Boolean
get() = registeredViews.contains(this)

set(value) {
    if (!value || !registeredViews.add(this)) return

    if (id == android.view.View.NO_ID) {
        id = android.view.View.generateViewId()
    }

    isSaveEnabled = true

    addOnAttachStateChangeListener(
        object : android.view.View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: android.view.View) {
                FreezeMe.retrieve(v.id)?.let {
                    (v as TextView).setTextColor(it)
                }
            }

            override fun onViewDetachedFromWindow(v: android.view.View) {
                FreezeMe.store(
                    v.id,
                    (v as TextView).currentTextColor
                )
            }
        }
    )
}

/**
 * View.freezesBackgroundColor   //remember to give the view an id 
 * @param Boolean
 * freeze the state of the view upon Activity recreation
 * ```
 * ktTextView().apply {
 *  id =10
 *  freezesBackgroundColor =true
 * }
 * ```
 */
var TextView.freezesBackgroundColor: Boolean
get() = false
set(value) {
    if (!value) return

    if (id == View.NO_ID) {
        id = View.generateViewId()
    }

    addOnAttachStateChangeListener(
        object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                FreezeMe.retrieveBackground(v.id)?.let {
                    v.setBackgroundColor(it)
                }
            }

            override fun onViewDetachedFromWindow(v: View) {
                val drawable = v.background
                if (drawable is android.graphics.drawable.ColorDrawable) {
                    FreezeMe.storeBackground(v.id, drawable.color)
                }
            }
        }
    )
}

/**
 * View.freezesTextSize  //remember to give the view an id 
 * @param Boolean
 * freeze the state of the view upon Activity recreation
 * ```
 * ktTextView().apply {
 *  id =10
 *  freezesTextSize =true
 * }
 * ```
 */
var TextView.freezesTextSize: Boolean
get() = false
set(value) {
    if (!value) return

    if (id == View.NO_ID) {
        id = View.generateViewId()
    }

    addOnAttachStateChangeListener(
        object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                FreezeMe.retrieveTextSize(v.id)?.let {
                    (v as TextView).setTextSize(
                        android.util.TypedValue.COMPLEX_UNIT_PX,
                        it
                    )
                }
            }

            override fun onViewDetachedFromWindow(v: View) {
                val size = (v as TextView).textSize
                FreezeMe.storeTextSize(v.id, size)
            }
        }
    )
}
