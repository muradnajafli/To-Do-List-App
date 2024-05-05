package com.muradnajafli.todolistapp.domain.usecase.todo

import com.muradnajafli.todolistapp.domain.model.Todo
import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTodosUseCaseImpl @Inject constructor(
    private val todoRepository: TodoRepository
) : GetTodosUseCase {
    override fun invoke(): Flow<List<Todo>> {
        return todoRepository.getTodos()
    }
}

interface GetTodosUseCase {
    operator fun invoke(): Flow<List<Todo>>
}