package com.muradnajafli.todolistapp.domain.model

import java.util.UUID

data class Todo(
    var id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    var title: String
)
