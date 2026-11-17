
@file:Suppress("ALL")
package com.KT_kt

import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class KtSQLite(
    context: Context,
    name: String,
    version: Int,
    private val onCreateDB: (SQLiteDatabase) -> Unit,
    private val onUpgradeDB: (
        SQLiteDatabase,
        Int,
        Int
    ) -> Unit = { _, _, _ -> }
) : SQLiteOpenHelper(
    context.applicationContext,
    name,
    null,
    version
) {

    override fun onCreate(db: SQLiteDatabase) {
        onCreateDB(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        onUpgradeDB(db, oldVersion, newVersion)
    }

    // Execute SQL
    fun execSql(
        sql: String,
        args: Array<out Any>? = null
    ) {
        if (args == null) {
            writableDatabase.execSQL(sql)
        } else {
            writableDatabase.execSQL(sql, args)
        }
    }

    // Query records
    fun querySql(
        sql: String,
        args: Array<String>? = null
    ): Cursor {
        return readableDatabase.rawQuery(sql, args)
    }

}

// to avoid passing this
fun KtActivity.ktSqlite(
    name: String,
    version: Int,
    onCreateDB: (SQLiteDatabase) -> Unit,
    onUpgradeDB: (
        SQLiteDatabase,
        Int,
        Int
    ) -> Unit = { _, _, _ -> }
): KtSQLite {

    return KtSQLite(
        context = this,
        name = name,
        version = version,
        onCreateDB = onCreateDB,
        onUpgradeDB = onUpgradeDB
    )
}

/**usage
class MainActivity : KtActivity() {

private val db by lazy {
ktSqlite("MyDatabase.db", 1) { database ->

database.execSQL("""
CREATE TABLE users (
id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT
)
""")
}
}

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

// Execute SQL
db.exec("SELECT * FROM users") // Executes, but doesn't return records

// Query
db.query("SELECT * FROM users").use {
while (it.moveToNext()) {
println(it.getString(1))
}
}

// Access inherited methods
val writable = db.writableDatabase
val readable = db.readableDatabase

// Close
db.close()
}

override fun onDestroy() {
super.onDestroy()
db.close()
}
}
*/

/**
val name = "John"
val age = 25

db.exec(
"INSERT INTO users (name, age) VALUES (?, ?)",
arrayOf(name, age)
)
*/
