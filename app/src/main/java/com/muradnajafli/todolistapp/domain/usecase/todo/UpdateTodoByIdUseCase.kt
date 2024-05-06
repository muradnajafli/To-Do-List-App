package com.muradnajafli.todolistapp.domain.usecase.todo

import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import javax.inject.Inject


class UpdateTodoByIdUseCaseImpl @Inject constructor(
    private val todoRepository: TodoRepository
): UpdateTodoByIdUseCase {
    override operator fun invoke(todoId: Long, title: String) {
        todoRepository.updateTodoById(todoId, title)
    }

}

interface UpdateTodoByIdUseCase {
    operator fun invoke(todoId: Long, title: String)
}