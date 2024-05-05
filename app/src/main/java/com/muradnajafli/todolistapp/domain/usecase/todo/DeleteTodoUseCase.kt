package com.muradnajafli.todolistapp.domain.usecase.todo

import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import javax.inject.Inject


class DeleteTodoUseCaseImpl @Inject constructor(
    private val todoRepository: TodoRepository
) : DeleteTodoUseCase {
    override operator fun invoke(todo: Todo) {
        todoRepository.deleteTodoById(todo)
    }
}

interface DeleteTodoUseCase {
    operator fun invoke(todo: Todo)
}