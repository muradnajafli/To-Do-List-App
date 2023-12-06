package com.muradnajafli.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muradnajafli.todolistapp.data.utils.sharedPreferences
import com.muradnajafli.todolistapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private var completedColor by sharedPreferences("completedColor")
    private var notCompletedColor by sharedPreferences("notCompletedColor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.completedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.blueRadioButton -> {
                    completedColor = R.color.blue
                }
                R.id.yellowRadioButton -> {
                    completedColor = R.color.yellow
                }
                R.id.greenRadioButton -> {
                    completedColor = R.color.green
                }
            }
        }

        binding.notCompletedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.brownRadioButton -> {
                    notCompletedColor = R.color.brown
                }
                R.id.pinkRadioButton -> {
                    notCompletedColor = R.color.pink
                }
                R.id.violetRadioButton -> {
                    notCompletedColor = R.color.violet
                }
            }
        }

        loadTasksFromSharedPreferences()
    }

    private fun loadTasksFromSharedPreferences() {
        val completedColorResId = completedColor
        val notCompletedColorResId = notCompletedColor

        if (completedColorResId != -1) {
            when (completedColorResId) {
                R.color.blue -> {
                    binding.blueRadioButton.isChecked = true
                }
                R.color.yellow -> {
                    binding.yellowRadioButton.isChecked = true
                }
                R.color.green -> {
                    binding.greenRadioButton.isChecked = true
                }
            }
        }

        if (notCompletedColorResId != -1) {
            when (notCompletedColorResId) {
                R.color.brown -> {
                    binding.brownRadioButton.isChecked = true
                }
                R.color.pink -> {
                    binding.pinkRadioButton.isChecked = true
                }
                R.color.violet -> {
                    binding.violetRadioButton.isChecked = true
                }
            }
        }
    }
}