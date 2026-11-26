package com.KT_kt

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

private fun View.getConstraintParams(): ConstraintLayout.LayoutParams {

    val params = layoutParams

    if (params is ConstraintLayout.LayoutParams) {
        return params
    }

    return ConstraintLayout.LayoutParams(
        params?.width ?: ViewGroup.LayoutParams.WRAP_CONTENT,
        params?.height ?: ViewGroup.LayoutParams.WRAP_CONTENT
    ).also {
        layoutParams = it
    }
}

// Parent ID
val PARENT_ID = ConstraintLayout.LayoutParams.PARENT_ID

// TOP
var View.topToTop: Int
get() = getConstraintParams().topToTop
set(value) {
    getConstraintParams().topToTop = value
}

var View.topToBottom: Int
get() = getConstraintParams().topToBottom
set(value) {
    getConstraintParams().topToBottom = value
}

// BOTTOM
var View.bottomToTop: Int
get() = getConstraintParams().bottomToTop
set(value) {
    getConstraintParams().bottomToTop = value
}

var View.bottomToBottom: Int
get() = getConstraintParams().bottomToBottom
set(value) {
    getConstraintParams().bottomToBottom = value
}

// START
var View.startToStart: Int
get() = getConstraintParams().startToStart
set(value) {
    getConstraintParams().startToStart = value
}

var View.startToEnd: Int
get() = getConstraintParams().startToEnd
set(value) {
    getConstraintParams().startToEnd = value
}

// END
var View.endToStart: Int
get() = getConstraintParams().endToStart
set(value) {
    getConstraintParams().endToStart = value
}

var View.endToEnd: Int
get() = getConstraintParams().endToEnd
set(value) {
    getConstraintParams().endToEnd = value
}

var View.matchConstraintPercentWidth: Float
get() = getConstraintParams().matchConstraintPercentWidth
set(value) {
    getConstraintParams().apply {
        width = 0
        matchConstraintPercentWidth = value
    }
}

var View.matchConstraintPercentHeight: Float
get() = getConstraintParams().matchConstraintPercentHeight
set(value) {
    getConstraintParams().apply {
        height = 0
        matchConstraintPercentHeight = value
    }
}

var View.horizontalBias: Float
get() = getConstraintParams().horizontalBias
set(value) {
    getConstraintParams().horizontalBias = value
}

var View.verticalBias: Float
get() = getConstraintParams().verticalBias
set(value) {
    getConstraintParams().verticalBias = value
}

var View.dimensionRatio: String
get() = getConstraintParams().dimensionRatio
set(value) {
    getConstraintParams().dimensionRatio = value
}

var View.topMargin: Int
get() = getConstraintParams().topMargin
set(value) {
    getConstraintParams().topMargin = value
}

var View.bottomMargin: Int
get() = getConstraintParams().bottomMargin
set(value) {
    getConstraintParams().bottomMargin = value
}

var View.startMargin: Int
get() = getConstraintParams().marginStart
set(value) {
    getConstraintParams().marginStart = value
}

var View.endMargin: Int
get() = getConstraintParams().marginEnd
set(value) {
    getConstraintParams().marginEnd = value
}

fun Context.ktConstraintLayout(
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    block: ConstraintLayout.() -> Unit = {}
): ConstraintLayout {

    return ConstraintLayout(this).apply {
        layoutParams = ViewGroup.LayoutParams(width, height)

        block()
    }
}
