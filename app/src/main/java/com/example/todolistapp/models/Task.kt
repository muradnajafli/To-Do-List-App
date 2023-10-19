package com.example.todolistapp.models

data class Task(
    val id: Long,
    val taskName: String,
    val isCompleted: Int,
    val todoListId: Long
)

