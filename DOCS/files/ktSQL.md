# KtSQLite

[<-- Back](../home.md)

`KtSQLite` is a database helper class in the `KT_kt` library. It extends Android's native `SQLiteOpenHelper` and provides a simpler way to create, upgrade, execute SQL statements, and retrieve records from an SQLite database.

It also provides a `ktSqlite()` extension function for creating a database directly from a `KtActivity`.

## Creating a Database

Use `ktSqlite()` inside a class that extends `KtActivity`.

```kotlin
class MainActivity : KtActivity() {

    private val db by lazy {
        ktSqlite(
            name = "MyDatabase.db",
            version = 1,
            onCreateDB = { database ->

                database.execSQL("""
                    CREATE TABLE users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT
                    )
                """)
            }
        )
    }

    override fun onCreate() {
        super.onCreate()
    }
}
```

### Parameters

| Parameter     | Description                                                       |
| ------------- | ----------------------------------------------------------------- |
| `name`        | Database filename.                                                |
| `version`     | Database version number. Must be greater than zero.               |
| `onCreateDB`  | Callback invoked when the database is created for the first time. |
| `onUpgradeDB` | Optional callback invoked when the database version increases.    |

The `onCreateDB` callback receives an `SQLiteDatabase` object. Use it to create tables and initialize the database.

The `onUpgradeDB` callback receives three arguments:

- `database`: The database being upgraded.
- `oldVersion`: The previous database version.
- `newVersion`: The requested database version.

For example:

```kotlin
private val db by lazy {
    ktSqlite(
        name = "MyDatabase.db",
        version = 2,

        onCreateDB = { database ->
            database.execSQL("""
                CREATE TABLE users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT
                )
            """)
        },

        onUpgradeDB = { database, oldVersion, newVersion ->

            if (oldVersion < 2) {
                database.execSQL(
                    "ALTER TABLE users ADD COLUMN age INTEGER"
                )
            }
        }
    )
}
```

**Important:** `onCreateDB` is called when the database is first created, not every time the application opens it. `onUpgradeDB` is called when the requested version is higher than the existing database version.

## Methods

| Method                | Description                            |
| --------------------- | -------------------------------------- |
| `execSql(sql, args)`  | Executes an SQL statement.             |
| `querySql(sql, args)` | Executes a query and returns a Cursor. |

## Executing SQL Statements

Use `execSql()` to execute SQL statements that do not need to return records.

For example, inserting a record:

```kotlin
db.execSql(
    "INSERT INTO users (name) VALUES (?)",
    arrayOf("John")
)
```

You can also execute statements without arguments:

```kotlin
db.execSql("""
    CREATE TABLE products (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT
    )
""")
```

The `args` parameter is optional. When provided, its values are bound to the `?` placeholders in the SQL statement.

You can use `execSql()` for operations such as:

- `CREATE TABLE`
- `INSERT`
- `UPDATE`
- `DELETE`
- `ALTER TABLE`

It does not return query records.

## Querying Records

Use `querySql()` to retrieve records from the database.

```kotlin
db.querySql("SELECT * FROM users").use { cursor ->

    while (cursor.moveToNext()) {

        val id = cursor.getInt(0)
        val name = cursor.getString(1)

        println("$id : $name")
    }
}
```

The method returns an Android `Cursor`, which allows you to move through the result rows and read their column values.

The `use {}` block automatically closes the cursor when the block finishes.

### Using Query Arguments

You can use placeholders to pass values safely into a query.

```kotlin
val name = "John"

db.querySql(
    "SELECT * FROM users WHERE name = ?",
    arrayOf(name)
).use { cursor ->

    while (cursor.moveToNext()) {
        println(cursor.getString(1))
    }
}
```

**Note:** `querySql()` accepts `Array<String>?` for its arguments, while `execSql()` accepts `Array<out Any>?`.

## Accessing Native SQLiteDatabase

Because `KtSQLite` extends Android's `SQLiteOpenHelper`, you can still access its inherited properties and methods.

```kotlin
val writable = db.writableDatabase
val readable = db.readableDatabase
```

- `writableDatabase` provides a database connection for reading and writing.
- `readableDatabase` provides a database connection for reading.

You can use these objects to access native Android database functionality, including transactions.

## Closing the Database

When you no longer need the database helper, call `close()` to release its resources.

```kotlin
override fun onDestroy() {
    super.onDestroy()
    db.close()
}
```

If the database helper is accessed again after being closed, it can be reopened through its inherited helper functionality.

## Important Notes

- `KtSQLite` uses the application context internally, rather than retaining the Activity context.
- The database is opened when `readableDatabase` or `writableDatabase` is accessed.
- Use `execSql()` for statements that do not return records and `querySql()` for retrieving records.
- Always close returned cursors. Using `.use {}` is a convenient way to do this.
- Database version changes should be handled in `onUpgradeDB`.
- SQL statements that contain user-provided values should use placeholders and bound arguments instead of directly concatenating those values into SQL strings.
- `close()` closes the helper's database connections; it does not delete the database file.

## Example: Complete Usage

```kotlin
class MainActivity : KtActivity() {

    private val db by lazy {
        ktSqlite(
            name = "MyDatabase.db",
            version = 1
        ) { database ->

            database.execSQL("""
                CREATE TABLE users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    age INTEGER
                )
            """)
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Insert a record
        db.execSql(
            "INSERT INTO users (name, age) VALUES (?, ?)",
            arrayOf("John", 25)
        )

        // Retrieve records
        db.querySql("SELECT * FROM users").use { cursor ->

            while (cursor.moveToNext()) {
                val name = cursor.getString(1)
                val age = cursor.getInt(2)

                println("$name is $age years old")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
```

