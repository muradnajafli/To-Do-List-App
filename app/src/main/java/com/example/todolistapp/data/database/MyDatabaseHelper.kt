package com.example.todolistapp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "todoapp.db"
        private const val DATABASE_VERSION = 1

        private const val LIST_TABLE_NAME = "todolist"
        private const val LIST_COLUMN_ID = "id"
        private const val LIST_COLUMN_TITLE = "list"

        private const val TASK_TABLE_NAME = "task"
        private const val TASK_COLUMN_ID = "id"
        private const val TASK_COLUMN_NAME = "task_name"
        private const val TASK_COLUMN_COMPLETED = "is_completed"
        private const val TASK_COLUMN_TODO_LIST_ID = "todo_list_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $LIST_TABLE_NAME (" +
                "$LIST_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$LIST_COLUMN_TITLE TEXT)")

        db?.execSQL("CREATE TABLE $TASK_TABLE_NAME (" +
                "$TASK_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TASK_COLUMN_NAME TEXT," +
                "$TASK_COLUMN_COMPLETED INTEGER," +
                "$TASK_COLUMN_TODO_LIST_ID INTEGER," +
                "FOREIGN KEY($TASK_COLUMN_TODO_LIST_ID) REFERENCES $LIST_TABLE_NAME($TASK_COLUMN_TODO_LIST_ID))")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $LIST_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TASK_TABLE_NAME")
        onCreate(db)
    }
}