package com.example.todolistapp.add

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.models.Task
import com.example.todolistapp.database.DatabaseManager
import com.example.todolistapp.databinding.TaskLayoutBinding

class TaskAdapter(
    var context: Context,
    private var taskList: ArrayList<Task>,
    private var listId: Long,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

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

            val taskId = taskList[position].id

            val myDatabaseManager = DatabaseManager(context)
            myDatabaseManager.updateTask(taskId, updatedText, listId, taskList[position].isCompleted)

            taskList[position] = Task(taskId, updatedText, taskList[position].isCompleted, listId)
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
            val myDatabaseManager = DatabaseManager(context)
            myDatabaseManager.deleteTask(taskList[position].taskName, listId)

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

        myHolder.checkBox.text = task.taskName
        myHolder.checkBox.isChecked = task.isCompleted == 1
        myHolder.checkBox.setOnCheckedChangeListener { _, isChecked ->

            val myDatabaseManager = DatabaseManager(context)
            val newIsCompleted = if (isChecked) 1 else 0
            myDatabaseManager.updateTask(task.id, task.taskName, listId, newIsCompleted)

            taskList[position] = Task(task.id, task.taskName, newIsCompleted, listId)
            val textColor = if (isChecked) {
                ContextCompat.getColor(context, checkedId)
            } else {
                ContextCompat.getColor(context, unCheckedId)
            }
            myHolder.checkBox.setTextColor(textColor)
        }

        val textColor = if (task.isCompleted == 1) {
            ContextCompat.getColor(context, checkedId)
        } else {
            ContextCompat.getColor(context, unCheckedId)
        }
        myHolder.checkBox.setTextColor(textColor)
    }

    override fun getItemCount() = taskList.size
}