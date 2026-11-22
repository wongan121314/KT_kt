# KtAdapter

[<-- Back](../home.md)

`KtAdapter` provides simplified adapters for Android list-based views.

It contains two adapter classes:

- `KtAdapter<T>` – displays a simple list of items.
- `KtExpandableAdapter<G, C>` – displays groups with expandable child items.

Both adapters use lambda functions to define how their views should be created.

## KtAdapter

`KtAdapter<T>` extends Android's `BaseAdapter`.

It accepts a list of items and allows you to define how each item and its dropdown view should look.

### Usage

Create an adapter using `ktAdapter()`:

```kotlin
val adapter = ktAdapter(
    listOf("Apple", "Orange", "Mango")
)
```

You can configure the item layout inside the initialization block:

```kotlin
val adapter = ktAdapter(
    listOf("Apple", "Orange", "Mango")
) {
    getView { item, position ->
        ktTextView(
            text = "$position - $item",
            size = 18f
        )
    }
}
```

The lambda receives:
- `item` – the current list item.
- `position` – the item's position in the list.

### Configuring Item Views

Use `getView {}` to define how each item should be displayed.

```kotlin
adapter.getView { item, position ->

    ktTextView(
        text = "$position - $item",
        size = 18f
    )
}
```

This renderer is called when Android requests a view for a list item.

### Configuring Dropdown Views

Use `getDropDownView {}` to define how items should appear in a dropdown.

```kotlin
adapter.getDropDownView { item, position ->

    ktTextView(
        text = "Select: $item"
    )
}
```

This is particularly useful when using the adapter with an Android `Spinner`, where the selected item and the dropdown item can have different appearances.

### Using the Adapter

For example, attach the adapter to a `ListView`:

```kotlin
val items = listOf(
    "Apple",
    "Orange",
    "Mango"
)

val adapter = ktAdapter(items) {
    getView { item, position ->
        ktTextView(
            text = "$position - $item",
            size = 18f
        )
    }
}

listView.adapter = adapter
```

## KtExpandableAdapter

`KtExpandableAdapter<G, C>` extends Android's `BaseExpandableListAdapter`.

It displays data in two levels:

- Groups – the main list entries.
- Children – items belonging to each group.

The generic types allow groups and children to use different data types.

### Usage

Create an expandable adapter using `ktExpandableAdapter()`:

```kotlin
val groups = listOf(
    "Fruits",
    "Animals"
)

val children = listOf(
    listOf("Apple", "Orange", "Mango"),
    listOf("Dog", "Cat", "Lion")
)

val adapter = ktExpandableAdapter(
    groups,
    children
)
```

Each group corresponds to a child list at the same position.

For example:

- `groups[0]` corresponds to `children[0]`.
- `groups[1]` corresponds to `children[1]`.

### Configuring Group Views

Use `getGroupView {}` to define how each group should appear.

```kotlin
adapter.getGroupView { group, position ->

    ktTextView(
        text = group,
        size = 20f
    )
}
```

The lambda receives:
- `group` – the current group item.
- `position` – the group's position.

### Configuring Child Views

Use `getChildView {}` to define how each child should appear.

```kotlin
adapter.getChildView {
    group, child, groupPosition, childPosition ->

    ktTextView(
        text = "$childPosition - $child",
        size = 16f
    )
}
```

The lambda receives:

| Parameter       | Description                             |
| --------------- | --------------------------------------- |
| `group`         | The parent group.                       |
| `child`         | The current child item.                 |
| `groupPosition` | Position of the parent group.           |
| `childPosition` | Position of the child within its group. |

### Using the Adapter

Attach the adapter to an `ExpandableListView`:

```kotlin
val groups = listOf(
    "Fruits",
    "Animals"
)

val children = listOf(
    listOf("Apple", "Orange", "Mango"),
    listOf("Dog", "Cat", "Lion")
)

val adapter = ktExpandableAdapter(
    groups,
    children
) {

    getGroupView { group, position ->
        ktTextView(
            text = group,
            size = 20f
        )
    }

    getChildView { group, child, groupPosition, childPosition ->
        ktTextView(
            text = child,
            size = 16f
        )
    }
}

expandableListView.setAdapter(adapter)
```

## Methods

### KtAdapter

| Method                   | Description                          |
| ------------------------ | ------------------------------------ |
| `getView(block)`         | Sets the item view renderer.         |
| `getDropDownView(block)` | Sets the dropdown item renderer.     |
| `getCount()`             | Returns the number of items.         |
| `getItem(position)`      | Returns an item at a given position. |
| `getItemId(position)`    | Returns the item's ID.               |

### KtExpandableAdapter

| Method                                            | Description                                 |
| ------------------------------------------------- | ------------------------------------------- |
| `getGroupView(block)`                             | Sets the group view renderer.               |
| `getChildView(block)`                             | Sets the child view renderer.               |
| `getGroupCount()`                                 | Returns the number of groups.               |
| `getChildrenCount(position)`                      | Returns the number of children in a group.  |
| `getGroup(position)`                              | Returns a group at a given position.        |
| `getChild(groupPosition, childPosition)`          | Returns a child at the specified positions. |
| `getGroupId(position)`                            | Returns the group's ID.                     |
| `getChildId(groupPosition, childPosition)`        | Returns the child's ID.                     |
| `hasStableIds()`                                  | Indicates whether item IDs are stable.      |
| `isChildSelectable(groupPosition, childPosition)` | Indicates whether a child is selectable.    |

## Notes

- Both adapters accept lists of generic types.
- The rendering functions return Android `View` objects.
- Calling a renderer configuration method invokes `notifyDataSetChanged()`.
- `KtAdapter` uses the item's position as its ID.
- `KtExpandableAdapter` also uses positions as IDs and reports that its IDs are not stable.
- `KtExpandableAdapter` expects one child list for every group.
- The adapters do not provide methods for adding, removing, or updating the underlying data lists.
- The `convertView` parameter supplied by Android is not reused by the current rendering implementation.