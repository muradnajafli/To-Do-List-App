package com.muradnajafli.todolistapp.domain.usecase.task

import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import javax.inject.Inject


class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : DeleteTaskUseCase {
    override fun invoke(taskId: Long) {
        taskRepository.deleteTaskById(taskId)
    }
}
interface DeleteTaskUseCase {
    operator fun invoke(taskId: Long)
}