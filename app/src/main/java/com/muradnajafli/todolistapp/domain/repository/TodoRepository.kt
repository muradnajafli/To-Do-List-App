package com.muradnajafli.todolistapp.domain.repository

import com.muradnajafli.todolistapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    fun addTodo(title: String)
    fun updateTodoById(todoId: Long, title: String)
    fun deleteTodoById(todo: Todo)
}