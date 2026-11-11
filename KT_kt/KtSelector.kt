package com.KT_kt

import android.app.Activity
import android.view.View
import android.view.ViewGroup

fun Activity.ktSelect(selector: String): List<View> {

    val root = window.decorView
    val results = mutableListOf<View>()

    fun matches(view: View): Boolean {

        return when {
            // ID selector
            selector.startsWith("#") -> {
                val name = selector.removePrefix("#")

                val id = resources.getIdentifier(
                    name,
                    "id",
                    packageName
                )

                id != 0 && view.id == id
            }

            // Class/tag selector
            selector.startsWith(".") -> {
                view.tag == selector.removePrefix(".")
            }

            // Attribute/tag selector
            selector.startsWith("[tag=") &&
            selector.endsWith("]") -> {
                val tag = selector.substring(
                    5,
                    selector.length - 1
                )

                view.tag == tag
            }

            // View type selector
            else -> {
                var type: Class<*>? = view.javaClass

                var found = false

                while (type != null && type != Any::class.java) {
                    if (type.simpleName == selector) {
                        found = true
                        break
                    }

                    type = type.superclass
                }

                found
            }
        }
    }

    fun search(view: View) {
        if (matches(view)) {
            results.add(view)
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                search(view.getChildAt(i))
            }
        }
    }

    search(root)

    return results
}
