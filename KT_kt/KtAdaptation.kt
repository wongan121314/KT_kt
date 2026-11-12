@file:Suppress("ALL")

package com.KT_kt

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.BaseExpandableListAdapter

//-----------
class KtAdapter<T>(
    private val context: Context,
    private val items: List<T>
) : BaseAdapter() {

    private var viewRenderer: (
        T, Int
    ) -> View = { _, _ ->
        View(context)
    }

    private var dropDownRenderer: (
        T, Int
    ) -> View = viewRenderer

    fun getDropDownView(
        block: (item: T, position: Int) -> View
    ): KtAdapter<T> {
        dropDownRenderer = block
        notifyDataSetChanged()
        return this
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return dropDownRenderer(getItem(position), position)
    }
    
    fun getView(
        block: (item: T, position: Int) -> View
    ): KtAdapter<T> {
        viewRenderer = block
        notifyDataSetChanged()
        return this
    }

    override fun getCount() = items.size

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return viewRenderer(
            getItem(position),
            position
        )
    }
}

class KtExpandableAdapter<G, C>(
    private val context: Context,
    private val groups: List<G>,
    private val children: List<List<C>>
) : BaseExpandableListAdapter() {

    private var groupRenderer: (
        G, Int
    ) -> View = { _, _ ->
        View(context)
    }

    private var childRenderer: (
        G, C, Int, Int
    ) -> View = { _, _, _, _ ->
        View(context)
    }

    // Configure group views
    fun getGroupView(
        block: (group: G, position: Int) -> View
    ): KtExpandableAdapter<G, C> {
        groupRenderer = block
        notifyDataSetChanged()
        return this
    }

    // Configure child views
    fun getChildView(
        block: (
            group: G,
            child: C,
            groupPosition: Int,
            childPosition: Int
        ) -> View
    ): KtExpandableAdapter<G, C> {
        childRenderer = block
        notifyDataSetChanged()
        return this
    }

    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return children[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): G {
        return groups[groupPosition]
    }

    override fun getChild(
        groupPosition: Int,
        childPosition: Int
    ): C {
        return children[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(
        groupPosition: Int,
        childPosition: Int
    ): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean = false

    override fun isChildSelectable(
        groupPosition: Int,
        childPosition: Int
    ): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return groupRenderer(
            getGroup(groupPosition),
            groupPosition
        )
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return childRenderer(
            getGroup(groupPosition),
            getChild(groupPosition, childPosition),
            groupPosition,
            childPosition
        )
    }
}

//-- to user
fun <T> Context.ktAdapter(
    items: List<T>,
    block: KtAdapter<T>.() -> Unit = {}
): KtAdapter<T> {

    return KtAdapter(this, items).apply(block)
}

fun <G, C> Context.ktExpandableAdapter(
    groups: List<G>,
    children: List<List<C>>,
    block: KtExpandableAdapter<G, C>.() -> Unit = {}
): KtExpandableAdapter<G, C> {

    return KtExpandableAdapter(
        context = this,
        groups = groups,
        children = children
    ).apply(block)
}

/**
*
val adapter = ktAdapter(
listOf("Apple", "Orange", "Mango")
) {
getView { item, position ->
ktTextView(
text = "$position - $item",
size = 18f,
width = MATCH_PARENT
)
}
}
*
Usage val adapter = ktAdapter(
listOf("Apple", "Orange", "Mango")
)

adapter.getView { item, position ->
ktTextView(
text = "$position - $item",
size = 18f,
width = MATCH_PARENT
)
}
*/
