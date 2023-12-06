package com.muradnajafli.todolistapp.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.muradnajafli.todolistapp.SettingsActivity
import com.muradnajafli.todolistapp.data.model.ToDoList
import com.muradnajafli.todolistapp.data.database.DatabaseManager
import com.muradnajafli.todolistapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var myDatabaseManager: DatabaseManager
    private lateinit var toDoList: ArrayList<ToDoList>
    private lateinit var toDoListAdapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        toDoList = ArrayList()

        setUpRecyclerView()
        setUpSettingsButton()
        setUpAddButton()

        binding.addButton.setOnClickListener {
            showAddListDialog()
        }

        loadListsFromDatabase()
    }

    private fun setUpRecyclerView() {
        toDoListAdapter = ToDoListAdapter(this, toDoList)
        binding.recyclerView.adapter = toDoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpSettingsButton() {
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun setUpAddButton() {
        binding.addButton.setOnClickListener {
            showAddListDialog()
        }
    }

    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add ToDo List")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("ADD") { _, _ ->
            val enteredText = input.text.toString()

            if (enteredText.isNotEmpty()) {
                val newList = ToDoList(0, enteredText)
                val listId = myDatabaseManager.addTodoList(newList)
                myDatabaseManager.close()

                if (listId != -1L) {
                    Toast.makeText(this, "List Added to DATABASE", Toast.LENGTH_SHORT).show()
                    newList.id = listId
                    toDoList.add(newList)
                    toDoListAdapter.notifyItemInserted(toDoList.size - 1)
                }
            }
        }

        builder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadListsFromDatabase() {
        toDoList.clear()
        val lists = myDatabaseManager.getAllToDoLists()
        toDoList.addAll(lists)
        toDoListAdapter.notifyDataSetChanged()
    }
}



