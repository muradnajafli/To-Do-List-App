package com.muradnajafli.todolistapp.data.repository

import com.muradnajafli.todolistapp.domain.database.DbManagement
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dbManagement: DbManagement
) : TaskRepository {
    override fun getTasks(todoId: Long): Flow<List<Task>> {
        return dbManagement.getTasksByTodoId(todoId)
    }

    override fun addTask(task: Task): Long {
        return dbManagement.addTask(task)
    }

    override fun updateTask(task: Task) {
        dbManagement.updateTask(task)
    }

    override fun deleteTaskById(taskId: Long) {
        dbManagement.deleteTask(taskId)
    }

}