package com.muradnajafli.todolistapp.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.domain.usecase.todo.AddTodoUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.DeleteTodoUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.GetTodosUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.UpdateTodoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoById: UpdateTodoByIdUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()

    init {
        getTodos()
    }

    private fun getTodos() {
        viewModelScope.launch {
            getTodosUseCase().collect {
                _todos.value = it
            }
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            addTodoUseCase(title)
            getTodos()
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            updateTodoById(todo.id, todo.title)
            getTodos()
        }
    }

    fun deleteTodoById(todo: Todo) {
        viewModelScope.launch {
            deleteTodoUseCase(todo)
            getTodos()
        }
    }
}