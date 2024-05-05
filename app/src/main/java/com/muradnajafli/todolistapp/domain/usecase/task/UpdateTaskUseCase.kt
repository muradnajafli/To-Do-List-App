package com.muradnajafli.todolistapp.domain.usecase.task

import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : UpdateTaskUseCase {
    override fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}

interface UpdateTaskUseCase {
    operator fun invoke(task: Task)
}