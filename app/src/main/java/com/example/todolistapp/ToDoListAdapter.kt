package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ListLayoutBinding

class ToDoListAdapter(var context: Context, private var toDoList: ArrayList<String>):
    RecyclerView.Adapter<ToDoListAdapter.ListViewHolder>() {

    inner class ListViewHolder(var binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root){

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
                    intent.putExtra("listName", toDoList[adapterPosition])
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
            toDoList[position] = updatedText
            notifyItemChanged(position)

            val sharedPreferences = context.getSharedPreferences("ToDoList", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("list_$position", updatedText)
            editor.apply()

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
            toDoList.removeAt(position)
            notifyItemRemoved(position)

            val sharedPreferences = context.getSharedPreferences("ToDoList", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("list_$position")
            editor.apply()
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
        myHolder.toDoListTv.text = task
    }

    override fun getItemCount() = toDoList.size

}