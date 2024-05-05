package com.muradnajafli.todolistapp.domain.repository

import com.muradnajafli.todolistapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(todoId: Long): Flow<List<Task>>
    fun addTask(task: Task): Long
    fun updateTask(task: Task)
    fun deleteTaskById(taskId: Long)
}