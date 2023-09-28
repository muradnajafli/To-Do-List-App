package com.example.todolistapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class MyDataBaseHelper(
    private val context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "mylist"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "list"
        private const val COLUMN_TASK = "task"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_TASK TEXT)"

        db?.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addToDoList(list: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_TITLE, list)

        val result = db.insert(TABLE_NAME, null, contentValues)
        if (result.toInt() == -1) {
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        }
        db.close()

    }

    fun addToDoTask(task: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_TASK, task)

        val result = db.insert(TABLE_NAME, null, contentValues)
        if (result.toInt() == -1) {
            Toast.makeText(context, "FAILED $task", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "ADDED SUCCESSFULLY $task", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }


    fun getAllToDoTasks(): List<String> {
        val taskList = mutableListOf<String>()

        val db = this.readableDatabase
        val query = "SELECT $COLUMN_TASK FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val task = cursor.getString(cursor.run { getColumnIndex(COLUMN_TASK) })
                taskList.add(task)
            }
        }

        cursor.close()
        db.close() // Veritabanını kapatmayı unutmayın
        return taskList
    }

}