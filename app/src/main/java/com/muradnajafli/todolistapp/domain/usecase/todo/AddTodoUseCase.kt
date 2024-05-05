package com.muradnajafli.todolistapp.domain.usecase.todo

import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import javax.inject.Inject


class AddTodoUseCaseImpl @Inject constructor(
    private val todoRepository: TodoRepository
) : AddTodoUseCase {
    override fun invoke(title: String) {
        todoRepository.addTodo(title)
    }
}

interface AddTodoUseCase {
    operator fun invoke(title: String)
}