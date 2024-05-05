package com.muradnajafli.todolistapp.domain.usecase.task

import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override fun invoke(task: Task): Long {
        return taskRepository.addTask(task)
    }
}

interface AddTaskUseCase {
    operator fun invoke(task: Task): Long
}