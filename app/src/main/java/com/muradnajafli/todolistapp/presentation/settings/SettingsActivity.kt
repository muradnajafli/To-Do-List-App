package com.muradnajafli.todolistapp.presentation.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setupListeners()
    }

    private fun setupListeners() {
        binding.completedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val color = getColorResourceId(checkedId)
            viewModel.setCompletedColor(color)
        }

        binding.notCompletedColorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val color = getColorResourceId(checkedId)
            viewModel.setUnCompletedColor(color)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.completedColor.collect { color ->
                binding.completedColorRadioGroup.check(getCheckedRadioButtonId(color))
            }
        }
        lifecycleScope.launch {
            viewModel.unCompletedColor.collect { color ->
                binding.notCompletedColorRadioGroup.check(getCheckedRadioButtonId(color))
            }
        }
    }

    private fun getColorResourceId(checkedId: Int): Int {
        return when (checkedId) {
            R.id.blueRadioButton -> R.color.blue
            R.id.yellowRadioButton -> R.color.yellow
            R.id.greenRadioButton -> R.color.green
            R.id.brownRadioButton -> R.color.brown
            R.id.pinkRadioButton -> R.color.pink
            R.id.violetRadioButton -> R.color.violet
            else -> -1
        }
    }

    private fun getCheckedRadioButtonId(colorResId: Int): Int {
        return when (colorResId) {
            R.color.blue -> R.id.blueRadioButton
            R.color.yellow -> R.id.yellowRadioButton
            R.color.green -> R.id.greenRadioButton
            R.color.brown -> R.id.brownRadioButton
            R.color.pink -> R.id.pinkRadioButton
            R.color.violet -> R.id.violetRadioButton
            else -> -1
        }
    }
}
