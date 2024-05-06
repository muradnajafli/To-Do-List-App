package com.muradnajafli.todolistapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muradnajafli.todolistapp.R
import com.muradnajafli.todolistapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
