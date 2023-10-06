package com.example.todolistapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var myDatabaseManager: DatabaseManager // Veritabanı yöneticisi
    private lateinit var taskList: ArrayList<Task>
    private lateinit var taskListAdapter: TaskAdapter
    private var listId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listId = intent.getLongExtra("listId", -1)
        myDatabaseManager = DatabaseManager(this)

        taskList = ArrayList()
        taskListAdapter = TaskAdapter(this, taskList, listId)

        binding.taskRecyclerView.adapter = taskListAdapter
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add ToDo Item")

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("ADD") { _, _ ->
                showAddTaskDialog()
            }

            builder.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()
        }

        loadTasksFromDatabase()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add ToDo Item")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("ADD") { _, _ ->
            val enteredText = input.text.toString()

            val taskId = myDatabaseManager.addTask(enteredText, 0, listId)

            if (taskId != -1L) {
                taskList.add(Task(taskId, enteredText, 0, listId))
                taskListAdapter.notifyDataSetChanged()
            }
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun loadTasksFromDatabase() {
        taskList.clear()
        val tasks = myDatabaseManager.getTasksByListId(listId)

        taskList.addAll(tasks)

        taskListAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}