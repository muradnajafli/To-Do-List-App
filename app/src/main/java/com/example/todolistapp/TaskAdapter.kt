package com.example.todolistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.TaskLayoutBinding

class TaskAdapter(var context: Context,
                  private var taskList: ArrayList<String>,
                  private val listName: String?)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(var binding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.editButton.setOnClickListener {
                val position = adapterPosition
                editItem(position)
            }
            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                deleteItem(position)

            }
        }

    }

    fun editItem(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update ToDo Task")
        val input = EditText(context)
        builder.setView(input)

        builder.setPositiveButton("UPDATE") { _, _ ->
            val updatedText = input.text.toString()

            val sharedPreferences = context.getSharedPreferences("ToDoTask", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("task_${listName}_$position")

            taskList[position] = updatedText

            editor.putString("task_${listName}_$position", updatedText)
            editor.apply()

            notifyItemChanged(position)
        }
        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun deleteItem(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete ToDo Task")

        builder.setPositiveButton("DELETE") { _, _ ->
            val sharedPreferences = context.getSharedPreferences("ToDoTask", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove("task_${listName}_$position")
            editor.apply()

            taskList.removeAt(position)
            notifyItemRemoved(position)
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return TaskViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        val myHolder = holder.binding
        val context = myHolder.root.context
        val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val completedColorKey = "completedColor"
        val notCompletedColorKey = "notCompletedColor"

        val checkedId = sharedPreferences.getInt(completedColorKey, R.color.black)
        val unCheckedId = sharedPreferences.getInt(notCompletedColorKey, R.color.black)

        myHolder.checkBox.text = task

        val itemIsChecked = sharedPreferences.getBoolean("isChecked_$position", false)
        myHolder.checkBox.isChecked = itemIsChecked

        myHolder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("isChecked_$position", isChecked)

            val textColor = if (isChecked) {
                editor.putInt(completedColorKey, checkedId)
                ContextCompat.getColor(context, checkedId)
            } else {
                editor.putInt(notCompletedColorKey, unCheckedId)
                ContextCompat.getColor(context, unCheckedId)
            }
            editor.apply()

            myHolder.checkBox.setTextColor(textColor)
        }

        val textColor = if (itemIsChecked) {
            ContextCompat.getColor(context, checkedId)
        } else {
            ContextCompat.getColor(context, unCheckedId)
        }
        myHolder.checkBox.setTextColor(textColor)
    }

    override fun getItemCount() = taskList.size
}