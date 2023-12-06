package com.muradnajafli.todolistapp.data.model

data class Task(
    val id: Long,
    val taskName: String,
    val isCompleted: Int,
    val todoListId: Long
)

