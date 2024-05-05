package com.muradnajafli.todolistapp.di

import com.muradnajafli.todolistapp.data.database.DbManager
import com.muradnajafli.todolistapp.data.repository.TaskRepositoryImpl
import com.muradnajafli.todolistapp.data.repository.TodoRepositoryImpl
import com.muradnajafli.todolistapp.domain.database.DbManagement
import com.muradnajafli.todolistapp.domain.repository.TaskRepository
import com.muradnajafli.todolistapp.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    fun bindTodoRepository(
        todoRepositoryImpl: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    @Singleton
    fun bindDbManager(
        dbManagerImpl: DbManager
    ): DbManagement

}