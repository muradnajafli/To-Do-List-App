package com.muradnajafli.todolistapp.domain.model

import java.util.UUID

data class Task(
    val id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val taskName: String,
    val isCompleted: Int = 0,
    val todoId: Long
)

