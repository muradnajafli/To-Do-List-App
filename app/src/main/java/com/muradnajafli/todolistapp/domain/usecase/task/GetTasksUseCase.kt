package com.muradnajafli.todolistapp.domain.usecase.task

import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTasksUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : GetTasksUseCase {
    override fun invoke(listId: Long): Flow<List<Task>> {
        return taskRepository.getTasks(listId)
    }
}

interface GetTasksUseCase {
    operator fun invoke(listId: Long): Flow<List<Task>>
}