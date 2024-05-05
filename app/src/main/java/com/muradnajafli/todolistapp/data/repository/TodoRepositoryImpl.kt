package com.muradnajafli.todolistapp.data.repository

import com.muradnajafli.todolistapp.domain.database.DbManagement
import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dbManagement: DbManagement
): TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        return dbManagement.getTodos()
    }

    override fun addTodo(title: String) {
        dbManagement.addTodo(title)
    }

    override fun updateTodoById(todoId: Long, title: String) {
        dbManagement.updateTodo(todoId, title)
    }

    override fun deleteTodoById(todo: Todo) {
        dbManagement.deleteTodo(todo)
    }

}