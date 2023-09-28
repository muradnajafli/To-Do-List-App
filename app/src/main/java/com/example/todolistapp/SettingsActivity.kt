package com.example.todolistapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolistapp.databinding.ActivitySettingsBinding
import com.example.todolistapp.databinding.TaskLayoutBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Completed Renk Seçimi
        binding.blueColorButton.setOnClickListener {
            editor.putInt("completedColor", R.color.blue)
            editor.apply()
        }

        binding.yellowColorButton.setOnClickListener {
            editor.putInt("completedColor", R.color.yellow)
            editor.apply()
        }

        binding.greenColorButton.setOnClickListener {
            editor.putInt("completedColor", R.color.green)
            editor.apply()
        }

        // Not Completed Renk Seçimi
        binding.brownColorButton.setOnClickListener {
            editor.putInt("notCompletedColor", R.color.brown)
            editor.apply()
        }

        binding.pinkColorButton.setOnClickListener {
            editor.putInt("notCompletedColor", R.color.pink)
            editor.apply()
        }

        binding.violetColorButton.setOnClickListener {
            editor.putInt("notCompletedColor", R.color.violet)
            editor.apply()
        }

        loadTasksFromSharedPreferences()
    }

    private fun loadTasksFromSharedPreferences() {
        val completedColorResId = sharedPreferences.getInt("completedColor", -1)
        val notCompletedColorResId = sharedPreferences.getInt("notCompletedColor", -1)

        if (completedColorResId != -1) {
            when (completedColorResId) {
                R.color.blue -> {
                    binding.blueColorButton.isChecked = true
                }
                R.color.yellow -> {
                    binding.yellowColorButton.isChecked = true
                }
                R.color.green -> {
                    binding.greenColorButton.isChecked = true
                }
            }
        }

        if (notCompletedColorResId != -1) {
            when (notCompletedColorResId) {
                R.color.brown -> {
                    binding.brownColorButton.isChecked = true
                }
                R.color.pink -> {
                    binding.pinkColorButton.isChecked = true
                }
                R.color.violet -> {
                    binding.violetColorButton.isChecked = true
                }
            }
        }
    }
}
