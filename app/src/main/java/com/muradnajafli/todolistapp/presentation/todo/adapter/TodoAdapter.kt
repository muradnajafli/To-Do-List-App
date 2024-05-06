package com.muradnajafli.todolistapp.presentation.todo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.muradnajafli.todolistapp.domain.model.Todo

class TodoAdapter(
    private val onDeleteClicked: ((Todo) -> Unit),
    private val onEditClicked: ((Todo) -> Unit),
    private val onNavigateToTaskFragment: ((Long) -> Unit)
) : ListAdapter<Todo, TodoViewHolder>(TodoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position), onDeleteClicked, onEditClicked, onNavigateToTaskFragment)
    }

}
