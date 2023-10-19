package com.example.todolistapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.todolistapp.models.Task
import com.example.todolistapp.models.ToDoList

class DatabaseManager(context: Context) {

    private val dbHelper: MyDatabaseHelper = MyDatabaseHelper(context)
    private var database: SQLiteDatabase? = null

    companion object {

        private const val LIST_TABLE_NAME = "todolist"
        private const val LIST_COLUMN_TITLE = "list"

        private const val TASK_TABLE_NAME = "task"
        private const val TASK_COLUMN_NAME = "task_name"
        private const val TASK_COLUMN_COMPLETED = "is_completed"
        private const val TASK_COLUMN_TODO_LIST_ID = "todo_list_id"

        private const val LIST_COLUMN_ID = "id"
        private const val TASK_COLUMN_ID = "id"

    }


    fun close() {
        database?.close()
    }

    fun addTodoList(toDoList: ToDoList): Long {
        database = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(LIST_COLUMN_TITLE, toDoList.title)

        return database?.insert(LIST_TABLE_NAME, null, values) ?: -1
    }

    fun addTask(taskName: String, isCompleted: Int, todoListId: Long): Long {
        database = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(TASK_COLUMN_NAME, taskName)
        values.put(TASK_COLUMN_COMPLETED, isCompleted)
        values.put(TASK_COLUMN_TODO_LIST_ID, todoListId)

        val taskId = database?.insert(TASK_TABLE_NAME, null, values) ?: -1
        database?.close()

        return taskId
    }

    @SuppressLint("Range")
    fun getAllToDoLists(): List<ToDoList> {
        val list = ArrayList<ToDoList>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $LIST_TABLE_NAME", null)

        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(LIST_COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(LIST_COLUMN_TITLE))

                val toDoList = ToDoList(id, title)
                list.add(toDoList)
            }
        }

        db.close()
        return list
    }

    @SuppressLint("Range")
    fun getTasksByListId(todoListId: Long): List<Task> {
        val tasks = ArrayList<Task>()
        val db = dbHelper.readableDatabase
        val selection = "$TASK_COLUMN_TODO_LIST_ID = ?"
        val selectionArgs = arrayOf(todoListId.toString())

        val cursor = db.query(
            TASK_TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(TASK_COLUMN_ID))
                val taskName = cursor.getString(cursor.getColumnIndex(TASK_COLUMN_NAME))
                val isCompleted = cursor.getInt(cursor.getColumnIndex(TASK_COLUMN_COMPLETED))

                val task = Task(id, taskName, isCompleted, todoListId)
                tasks.add(task)
            }
        }

        db.close()
        return tasks
    }

    fun deleteTask(taskName: String, listId: Long): Int {
        val db = dbHelper.writableDatabase
        val args = arrayOf(taskName, listId.toString())
        val deletedRows = db.delete(
            TASK_TABLE_NAME,
            "$TASK_COLUMN_NAME = ? AND $TASK_COLUMN_TODO_LIST_ID = ?",
            args
        )
        db.close()
        return deletedRows
    }

    fun updateTask(taskId: Long, newTaskName: String, listId: Long, isCompleted: Int): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(TASK_COLUMN_NAME, newTaskName)
        values.put(TASK_COLUMN_COMPLETED, isCompleted)

        val args = arrayOf(taskId.toString(), listId.toString())

        val updatedRows = db.update(
            TASK_TABLE_NAME,
            values,
            "$TASK_COLUMN_ID = ? AND $TASK_COLUMN_TODO_LIST_ID = ?",
            args
        )
        db.close()
        return updatedRows
    }

    fun updateList(listId: Long, newListTitle: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(LIST_COLUMN_TITLE, newListTitle)

        val args = arrayOf(listId.toString())

        val updatedRows = db.update(LIST_TABLE_NAME, values, "$LIST_COLUMN_ID = ?", args)
        db.close()
        return updatedRows
    }

    fun deleteListWithTasks(listId: Long): Int {
        val db = dbHelper.writableDatabase
        val listArgs = arrayOf(listId.toString())

        val deletedTasks = db.delete(TASK_TABLE_NAME, "$TASK_COLUMN_TODO_LIST_ID = ?", listArgs)

        val deletedList = db.delete(LIST_TABLE_NAME, "$LIST_COLUMN_ID = ?", listArgs)

        db.close()
        return deletedTasks + deletedList
    }
}
