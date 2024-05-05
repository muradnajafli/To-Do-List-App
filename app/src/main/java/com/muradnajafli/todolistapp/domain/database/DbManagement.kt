package com.muradnajafli.todolistapp.domain.database

import android.content.ContentValues
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface DbManagement {
    fun getTodos(): Flow<List<Todo>>
    fun addTodo(title: String): Long
    fun updateTodo(todoId: Long?, newTitle: String): Int
    fun deleteTodo(todo: Todo): Int
    fun getTasksByTodoId(todoId: Long): Flow<List<Task>>
    fun addTask(task: Task): Long
    fun updateTask(task: Task): Int
    fun deleteTask(taskId: Long): Int
}