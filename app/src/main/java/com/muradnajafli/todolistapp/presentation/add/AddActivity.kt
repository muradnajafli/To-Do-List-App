package com.muradnajafli.todolistapp.presentation.add

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.muradnajafli.todolistapp.data.model.Task
import com.muradnajafli.todolistapp.data.database.DatabaseManager
import com.muradnajafli.todolistapp.databinding.ActivityAddBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    @Inject lateinit var myDatabaseManager: DatabaseManager
    private lateinit var taskList: ArrayList<Task>
    private lateinit var taskListAdapter: TaskAdapter
    private var listId: Long = -1

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listId = intent.getLongExtra("listId", -1)
        taskList = ArrayList()
        taskListAdapter = TaskAdapter(this, taskList, listId)

        binding.taskRecyclerView.adapter = taskListAdapter
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            showAddTaskDialog()
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
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}