package com.muradnajafli.todolistapp.domain.model

import java.util.UUID

data class Todo(
    val id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val title: String
)
