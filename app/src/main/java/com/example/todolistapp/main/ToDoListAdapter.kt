package com.example.todolistapp.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.add.AddActivity
import com.example.todolistapp.R
import com.example.todolistapp.models.ToDoList
import com.example.todolistapp.database.DatabaseManager
import com.example.todolistapp.databinding.ListLayoutBinding

class ToDoListAdapter(var context: Context, private var toDoList: ArrayList<ToDoList>) :
    RecyclerView.Adapter<ToDoListAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            val dropDownMenu = PopupMenu(context, binding.listOptionsButton)
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

            // Update the list title in the database
            val myDatabaseManager = DatabaseManager(context)
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

            val myDatabaseManager = DatabaseManager(context)
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
        val binding = ListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = toDoList[position]
        val myHolder = holder.binding
        myHolder.toDoListTv.text = task.title
    }

    override fun getItemCount() = toDoList.size
}