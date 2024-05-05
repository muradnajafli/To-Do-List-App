package com.muradnajafli.todolistapp.data.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.muradnajafli.todolistapp.domain.database.DbManagement
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_ID
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_IS_COMPLETED
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_TABLE_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_ID
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_TABLE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbManager @Inject constructor(
    private val dbHelper: DbHelper
) : DbManagement {

    private val database: SQLiteDatabase by lazy { dbHelper.writableDatabase }

    override fun getTodos(): Flow<List<Todo>> = flow {
        val todos = mutableListOf<Todo>()
        val cursor = database.rawQuery("SELECT * FROM $TODO_TABLE_NAME", null)

        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(TODO_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(TODO_NAME))
                todos.add(Todo(id, title))
            }
        }
        emit(todos)
    }

    override fun addTodo(title: String): Long {
        val values = ContentValues().apply {
            put(TODO_NAME, title)
        }
        return insertItem(TODO_TABLE_NAME, values)
    }

    override fun updateTodo(todoId: Long?, newTitle: String): Int {
        val values = ContentValues().apply {
            put(TODO_NAME, newTitle)
        }
        val whereClause = "$TODO_ID = ?"
        val whereArgs = arrayOf(todoId.toString())
        return updateItem(TODO_TABLE_NAME, values, whereClause, whereArgs)
    }

    override fun deleteTodo(todo: Todo): Int {
        val listArgs = arrayOf(todo.id.toString())
        val deletedTodo = deleteItem(TODO_TABLE_NAME, "$TODO_ID = ?", listArgs)
        val deletedTasks = deleteItem(TASK_TABLE_NAME, "$TODO_ID = ?", listArgs)
        val totalDeleted = deletedTasks + deletedTodo
        return totalDeleted
    }

    override fun getTasksByTodoId(todoId: Long): Flow<List<Task>> = flow {
        val tasks = mutableListOf<Task>()
        val selection = "$TODO_ID = ?"
        val selectionArgs = arrayOf(todoId.toString())

        val cursor = database.query(
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
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(TASK_ID))
                val taskName = cursor.getString(cursor.getColumnIndexOrThrow(TASK_NAME))
                val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(TASK_IS_COMPLETED))
                tasks.add(Task(id, taskName, isCompleted, todoId))
            }
        }
        emit(tasks)
    }

    override fun addTask(task: Task): Long {
        val values = ContentValues().apply {
            put(TASK_NAME, task.taskName)
            put(TASK_IS_COMPLETED, task.isCompleted)
            put(TODO_ID, task.todoId)
        }
        return insertItem(TASK_TABLE_NAME, values)
    }

    override fun updateTask(task: Task): Int {
        val values = ContentValues().apply {
            put(TASK_NAME, task.taskName)
            put(TASK_IS_COMPLETED, task.isCompleted)
        }
        val whereClause = "$TASK_ID = ? AND $TODO_ID = ?"
        val whereArgs = arrayOf(task.id.toString(), task.todoId.toString())
        return updateItem(TASK_TABLE_NAME, values, whereClause, whereArgs)
    }

    override fun deleteTask(taskId: Long): Int {
        val whereClause = "$TASK_ID = ?"
        val whereArgs = arrayOf(taskId.toString())
        return deleteItem(TASK_TABLE_NAME, whereClause, whereArgs)
    }

    private fun insertItem(tableName: String, values: ContentValues): Long {
        return database.insert(tableName, null, values)
    }

    private fun updateItem(
        tableName: String,
        values: ContentValues,
        whereClause: String,
        whereArgs: Array<String>
    ): Int {
        return database.update(tableName, values, whereClause, whereArgs)
    }

    private fun deleteItem(tableName: String, whereClause: String, whereArgs: Array<String>): Int {
        return database.delete(tableName, whereClause, whereArgs)
    }

}

