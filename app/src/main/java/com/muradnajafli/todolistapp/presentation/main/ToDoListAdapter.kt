package com.muradnajafli.todolistapp.presentation.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.muradnajafli.todolistapp.presentation.add.AddActivity
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.data.model.ToDoList
import com.muradnajafli.todolistapp.data.database.DatabaseManager
import com.muradnajafli.todolistapp.databinding.ListLayoutBinding
import javax.inject.Inject

class ToDoListAdapter(var context: Context, var toDoList: ArrayList<ToDoList>) : RecyclerView.Adapter<ToDoListAdapter.ListViewHolder>() {

    @Inject lateinit var myDatabaseManager: DatabaseManager

    inner class ListViewHolder(var binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            val dropDownMenu = PopupMenu(binding.root.context, binding.listOptionsButton)
            dropDownMenu.inflate(R.menu.list_options_menu)
            dropDownMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_option -> {
                        val position = adapterPosition
                        editList(position)
                        true
                    }
                    R.id.delete_option -> {
                        val position = adapterPosition
                        deleteList(position)
                        true
                    }
                    else -> false
                }
            }
            binding.listOptionsButton.setOnClickListener {
                dropDownMenu.show()
            }
            binding.cardView.setOnClickListener {
                val intent = Intent(context, AddActivity::class.java)
                intent.putExtra("listId", toDoList[adapterPosition].id)
                context.startActivity(intent)
            }
        }
    }

    fun editList(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update ToDo")
        val input = EditText(context)
        builder.setView(input)

        builder.setPositiveButton("UPDATE") { _, _ ->
            val updatedText = input.text.toString()
            val listId = toDoList[position].id

            myDatabaseManager.updateList(listId, updatedText)

            toDoList[position].title = updatedText
            notifyItemChanged(position)
        }
        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun deleteList(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete ToDo")

        builder.setPositiveButton("DELETE") { _, _ ->
            val listId = toDoList[position].id

            myDatabaseManager.deleteListWithTasks(listId)

            toDoList.removeAt(position)
            notifyItemRemoved(position)
        }
        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = toDoList[position]
        val myHolder = holder.binding
        myHolder.toDoListTv.text = task.title
    }

    override fun getItemCount() = toDoList.size
}