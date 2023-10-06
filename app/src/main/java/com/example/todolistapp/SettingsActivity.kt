package com.example.todolistapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.example.todolistapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        binding.completedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.blueRadioButton -> {
                    editor.putInt("completedColor", R.color.blue)
                }
                R.id.yellowRadioButton -> {
                    editor.putInt("completedColor", R.color.yellow)
                }
                R.id.greenRadioButton -> {
                    editor.putInt("completedColor", R.color.green)
                }
            }
            editor.apply()
        }

        binding.notCompletedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.brownRadioButton -> {
                    editor.putInt("notCompletedColor", R.color.brown)
                }
                R.id.pinkRadioButton -> {
                    editor.putInt("notCompletedColor", R.color.pink)
                }
                R.id.violetRadioButton -> {
                    editor.putInt("notCompletedColor", R.color.violet)
                }
            }
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
                    findViewById<RadioButton>(R.id.blueRadioButton).isChecked = true
                }
                R.color.yellow -> {
                    findViewById<RadioButton>(R.id.yellowRadioButton).isChecked = true
                }
                R.color.green -> {
                    findViewById<RadioButton>(R.id.greenRadioButton).isChecked = true
                }
            }
        }

        if (notCompletedColorResId != -1) {
            when (notCompletedColorResId) {
                R.color.brown -> {
                    findViewById<RadioButton>(R.id.brownRadioButton).isChecked = true
                }
                R.color.pink -> {
                    findViewById<RadioButton>(R.id.pinkRadioButton).isChecked = true
                }
                R.color.violet -> {
                    findViewById<RadioButton>(R.id.violetRadioButton).isChecked = true
                }
            }
        }
    }
}