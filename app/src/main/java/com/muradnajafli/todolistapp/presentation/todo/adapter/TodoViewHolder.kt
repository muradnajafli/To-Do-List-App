package com.muradnajafli.todolistapp.presentation.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.databinding.ItemTodoBinding
import com.muradnajafli.todolistapp.domain.model.Todo

class TodoViewHolder(
    private val binding: ItemTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        todo: Todo,
        onDeleteClicked: ((Todo) -> Unit)?,
        onEditClicked: ((Todo) -> Unit)?,
        onNavigateToTaskFragment: ((Long) -> Unit)?,
    ) {
        binding.apply {
            toDoListTv.text = todo.title
            listOptionsButton.setOnClickListener {
                showPopupMenu(todo, onDeleteClicked, onEditClicked)
            }
            root.setOnClickListener { onNavigateToTaskFragment?.invoke(todo.id) }
        }
    }

    private fun showPopupMenu(
        todo: Todo,
        onDeleteClicked: ((Todo) -> Unit)?,
        onEditClicked: ((Todo) -> Unit)?
    ) {
        val dropDownMenu = PopupMenu(binding.root.context, binding.listOptionsButton)
        dropDownMenu.inflate(R.menu.list_options_menu)
        dropDownMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_option -> {
                    onEditClicked?.invoke(todo)
                    true
                }

                R.id.delete_option -> {
                    onDeleteClicked?.invoke(todo)
                    true
                }

                else -> false
            }
        }
        dropDownMenu.show()
    }

    companion object {
        fun from(
            parent: ViewGroup,
        ): TodoViewHolder {
            val binding = ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TodoViewHolder(binding)
        }
    }
}