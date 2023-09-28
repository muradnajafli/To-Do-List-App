package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDataBaseHelper: MyDataBaseHelper
    private lateinit var toDoList: ArrayList<String>
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        sharedPreferences = getSharedPreferences("ToDoList", Context.MODE_PRIVATE)


        myDataBaseHelper = MyDataBaseHelper(this)
        toDoList = ArrayList()
        toDoListAdapter = ToDoListAdapter(this, toDoList)

        binding.recyclerView.adapter = toDoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add ToDo List")

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("ADD") { _, _ ->
                val enteredText = input.text.toString()
                val editor = sharedPreferences.edit()
                editor.putString("list_${toDoList.size}", enteredText)
                editor.apply()
                toDoListAdapter.notifyDataSetChanged()

                val dbHelper = MyDataBaseHelper(this)
                dbHelper.addToDoList(enteredText)
                toDoList.add(enteredText)
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
        toDoList.clear()
        val size = sharedPreferences.all.size

        for (i in 0 until size) {
            val task = sharedPreferences.getString("list_$i", "")
            if (!task.isNullOrEmpty()) {
                toDoList.add(task)
            }
        }
        toDoListAdapter.notifyDataSetChanged()
    }


}
