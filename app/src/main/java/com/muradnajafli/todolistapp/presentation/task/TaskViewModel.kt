package com.muradnajafli.todolistapp.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.usecase.task.AddTaskUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.DeleteTaskUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.GetTasksUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    fun getTasks(todoId: Long) {
        viewModelScope.launch {
            getTasksUseCase(todoId).collect {
                _tasks.value = it
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase(task)
            getTasks(task.todoId)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            getTasks(task.todoId)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task.id)
            getTasks(task.todoId)
        }
    }

}
