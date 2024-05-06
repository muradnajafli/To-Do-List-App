package com.muradnajafli.todolistapp.presentation.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.muradnajafli.todolistapp.databinding.ItemTaskBinding
import com.muradnajafli.todolistapp.domain.model.Task
import com.muradnajafli.todolistapp.utils.Constants
import com.muradnajafli.todolistapp.utils.sharedPreferences

class TaskViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    private val completedColor by context.sharedPreferences(Constants.COMPLETED_COLOR_KEY)
    private val unCompletedColor by context.sharedPreferences(Constants.UNCOMPLETED_COLOR_KEY)

    fun bind(
        task: Task,
        onEditClick: ((Task) -> Unit),
        onDeleteClick: ((Task) -> Unit),
        onUpdate: ((Task) -> Unit)
    ) {
        binding.apply {
            checkBox.text = task.taskName
            checkBox.isChecked = task.isCompleted == 1
            val textColor = ContextCompat.getColor(
                context,
                if (task.isCompleted == 1) completedColor else unCompletedColor
            )
            checkBox.setTextColor(textColor)

            setOnCheckedChangeListener(task, onUpdate)

            deleteButton.setOnClickListener {
                onDeleteClick.invoke(task)
            }
            editButton.setOnClickListener {
                onEditClick.invoke(task)
            }

        }
    }

    private fun setOnCheckedChangeListener(
        task: Task,
        onUpdate: ((Task) -> Unit)
    ) {
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val newIsCompleted = if (isChecked) 1 else 0
            val newTask = task.copy(isCompleted = newIsCompleted)
            onUpdate.invoke(newTask)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup
        ): TaskViewHolder {
            val binding = ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TaskViewHolder(binding)
        }
    }
}

