package com.muradnajafli.todolistapp.di

import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import com.muradnajafli.todolistapp.domain.usecase.task.AddTaskUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.AddTaskUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.task.DeleteTaskUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.DeleteTaskUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.settings.GetCompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.GetCompletedColorUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.task.GetTasksUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.GetTasksUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.settings.GetUncompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.GetUncompletedColorUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.settings.SetCompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.SetCompletedColorUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.settings.SetUncompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.SetUncompletedColorUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.task.UpdateTaskUseCase
import com.muradnajafli.todolistapp.domain.usecase.task.UpdateTaskUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.todo.AddTodoUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.AddTodoUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.todo.DeleteTodoUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.DeleteTodoUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.todo.GetTodosUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.GetTodosUseCaseImpl
import com.muradnajafli.todolistapp.domain.usecase.todo.UpdateTodoByIdUseCase
import com.muradnajafli.todolistapp.domain.usecase.todo.UpdateTodoByIdUseCaseImpl
import com.muradnajafli.todolistapp.utils.ColorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetTasksUseCase(
        taskRepository: TaskRepository
    ): GetTasksUseCase {
        return GetTasksUseCaseImpl(taskRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteTaskUseCase(
        taskRepository: TaskRepository
    ): DeleteTaskUseCase {
        return DeleteTaskUseCaseImpl(taskRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateTaskUseCase(
        taskRepository: TaskRepository
    ): UpdateTaskUseCase {
        return UpdateTaskUseCaseImpl(taskRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideAddTaskUseCase(
        taskRepository: TaskRepository
    ): AddTaskUseCase {
        return AddTaskUseCaseImpl(taskRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetTodosUseCase(
        todoRepository: TodoRepository
    ): GetTodosUseCase {
        return GetTodosUseCaseImpl(todoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteTodoUseCase(
        todoRepository: TodoRepository
    ): DeleteTodoUseCase {
        return DeleteTodoUseCaseImpl(todoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateTodoUseCase(
        todoRepository: TodoRepository
    ): UpdateTodoByIdUseCase {
        return UpdateTodoByIdUseCaseImpl(todoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideAddTodoUseCase(
        todoRepository: TodoRepository
    ): AddTodoUseCase {
        return AddTodoUseCaseImpl(todoRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetCompletedColorUseCase(
        colorManager: ColorManager
    ): GetCompletedColorUseCase {
        return GetCompletedColorUseCaseImpl(colorManager)
    }

    @ViewModelScoped
    @Provides
    fun provideGetUncompletedColorUseCase(
        colorManager: ColorManager
    ): GetUncompletedColorUseCase {
        return GetUncompletedColorUseCaseImpl(colorManager)
    }

    @ViewModelScoped
    @Provides
    fun provideSetCompletedColorUseCase(
        colorManager: ColorManager
    ): SetCompletedColorUseCase {
        return SetCompletedColorUseCaseImpl(colorManager)
    }

    @ViewModelScoped
    @Provides
    fun provideSetUncompletedColorUseCase(
        colorManager: ColorManager
    ): SetUncompletedColorUseCase {
        return SetUncompletedColorUseCaseImpl(colorManager)
    }

}