package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var myDataBaseHelper: MyDataBaseHelper
    private lateinit var taskList: ArrayList<String>
    private lateinit var taskListAdapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var listName: String? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("ToDoTask", Context.MODE_PRIVATE)
        listName = intent.getStringExtra("listName")

        myDataBaseHelper = MyDataBaseHelper(this)
        taskList = ArrayList()
        taskListAdapter = TaskAdapter(this, taskList, listName)

        binding.taskRecyclerView.adapter = taskListAdapter
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add ToDo Item")

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("ADD") { _, _ ->
                val enteredText = input.text.toString()
                val editor = sharedPreferences.edit()
                editor.putString("task_${listName}_${taskList.size}", enteredText)
                editor.apply()
                taskListAdapter.notifyDataSetChanged()

                val dbHelper = MyDataBaseHelper(this)
                dbHelper.addToDoTask(enteredText)
                taskList.add(enteredText)

            }
            builder.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()
        }
        loadTasksFromSharedPreferences()

    }
    private fun loadTasksFromSharedPreferences() {
        taskList.clear()

        val allTasks = sharedPreferences.all
        val taskKeys = allTasks.keys.filter { it.startsWith("task_${listName}_") }

        val sortedTaskKeys = taskKeys.sortedBy { it.substringAfterLast("_").toInt() }

        for (key in sortedTaskKeys) {
            val task = allTasks[key].toString()
            if (task.isNotEmpty()) {
                taskList.add(task)
            }
        }
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