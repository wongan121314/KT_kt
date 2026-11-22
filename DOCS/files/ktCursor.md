# KtCursorExtensions

[<-- Back](../home.md)

`KtCursorExtensions` provides extension functions for Android's `Cursor` and `Uri` classes.

These extensions simplify reading database query results, navigating through cursor rows, checking columns, and appending record IDs to content URIs.

## Cursor Column Access

Normally, Android's `Cursor` requires a column index when retrieving a value.

For example:

```kotlin
val name = cursor.getString(
    cursor.getColumnIndexOrThrow("name")
)
```

With `KT_kt`, you can access the column directly by its name:

```kotlin
val name = cursor.string("name")
```

### Available Methods

| Method              | Description                            |
| ------------------- | -------------------------------------- |
| `index(column)`     | Gets the index of a column by name.    |
| `string(column)`    | Retrieves a String value.              |
| `int(column)`       | Retrieves an Int value.                |
| `long(column)`      | Retrieves a Long value.                |
| `float(column)`     | Retrieves a Float value.               |
| `double(column)`    | Retrieves a Double value.              |
| `blob(column)`      | Retrieves a ByteArray value.           |
| `isNull(column)`    | Checks whether a column value is null. |
| `hasColumn(column)` | Checks whether a column exists.        |

### Examples

```kotlin
val name = cursor.string("name")
val age = cursor.int("age")
val id = cursor.long("id")
val price = cursor.float("price")
val balance = cursor.double("balance")
val image = cursor.blob("image")
```

Check whether a column contains a null value:

```kotlin
if (cursor.isNull("name")) {
    println("Name is empty")
}
```

Check whether a column exists before accessing it:

```kotlin
if (cursor.hasColumn("email")) {
    val email = cursor.string("email")
}
```

**Important:** `index()` uses Android's `getColumnIndexOrThrow()`. It throws an exception if the requested column does not exist. Use `hasColumn()` when you need to check first.

Also, `string()` returns `String?`, but the other value-reading methods return non-null primitive or array types. A database SQL NULL value does not automatically become a Kotlin nullable value for those methods.

## Cursor Navigation

A `Cursor` represents the result of a database query. It allows you to move between records and read their values.

These extensions provide shorter names for Android's built-in navigation methods.

| Method      | Description                    |
| ----------- | ------------------------------ |
| `next()`    | Moves to the next row.         |
| `first()`   | Moves to the first row.        |
| `last()`    | Moves to the last row.         |
| `rows()`    | Returns the number of rows.    |
| `columns()` | Returns the number of columns. |

### Moving Through Records

```kotlin
while (cursor.next()) {
    val name = cursor.string("name")
    println(name)
}
```

Move directly to the first or last row:

```kotlin
if (cursor.first()) {
    println(cursor.string("name"))
}

if (cursor.last()) {
    println(cursor.string("name"))
}
```

### Getting Row and Column Counts

```kotlin
val totalRows = cursor.rows()
val totalColumns = cursor.columns()

println("Rows: $totalRows")
println("Columns: $totalColumns")
```

`rows()` returns the cursor's row count, while `columns()` returns the number of columns in the query result.

## Uri Extension

The `withId()` extension appends a numeric ID to an existing content URI using Android's `ContentUris.withAppendedId()`.

### Method

| Method       | Description                           |
| ------------ | ------------------------------------- |
| `withId(id)` | Appends a record ID to a content URI. |

### Example

```kotlin
val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

val specificImage = imageUri.withId(25)
```

This produces a URI identifying the record with ID `25` under the original content URI.

It is useful when working with Android MediaStore records and other content providers that identify individual records using numeric IDs.

## Complete Example

The following example demonstrates querying records and using the cursor extensions to read them.

```kotlin
db.querySql("SELECT * FROM users").use { cursor ->

    println("Total users: ${cursor.rows()}")

    while (cursor.next()) {

        val id = cursor.long("id")
        val name = cursor.string("name")

        println("$id : $name")
    }
}
```

The `use {}` block closes the cursor automatically after processing the results.

## Important Notes

- These are extension functions; they do not replace Android's native `Cursor` methods.
- Column names must match the column names returned by the query.
- `index()` throws an exception if the column does not exist.
- `hasColumn()` returns `false` when the column is not found.
- `next()`, `first()`, and `last()` return `Boolean`, indicating whether the cursor successfully moved to the requested position.
- Always close cursors after use. Prefer `.use {}` to ensure they are closed even if an exception occurs.
- `withId()` appends an ID to a URI; it does not query the database or verify that the record exists.

