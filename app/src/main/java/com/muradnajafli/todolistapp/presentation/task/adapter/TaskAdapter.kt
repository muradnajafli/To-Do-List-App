package com.muradnajafli.todolistapp.presentation.task.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.muradnajafli.todolistapp.domain.model.Task

class TaskAdapter(
    private val onDeleteClick: ((Task) -> Unit),
    private val onEditClick: ((Task) -> Unit),
    private val onUpdate: ((Task) -> Unit)
) : ListAdapter<Task, TaskViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), onEditClick, onDeleteClick, onUpdate)
    }

}
