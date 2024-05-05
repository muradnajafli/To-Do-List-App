package com.muradnajafli.todolistapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muradnajafli.todolistapp.domain.usecase.settings.GetCompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.GetUncompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.SetCompletedColorUseCase
import com.muradnajafli.todolistapp.domain.usecase.settings.SetUncompletedColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getCompletedColorUseCase: GetCompletedColorUseCase,
    private val setCompletedColorUseCase: SetCompletedColorUseCase,
    private val getUncompletedColorUseCase: GetUncompletedColorUseCase,
    private val setUncompletedColorUseCase: SetUncompletedColorUseCase
) : ViewModel() {

    private val _completedColor = MutableStateFlow(0)
    val completedColor = _completedColor.asStateFlow()

    private val _unCompletedColor = MutableStateFlow(0)
    val unCompletedColor = _unCompletedColor.asStateFlow()

    init {
        getCompletedColor()
        getUnCompletedColor()
    }

    private fun getCompletedColor() {
        viewModelScope.launch {
            _completedColor.value = getCompletedColorUseCase()
        }
    }

    fun setCompletedColor(color: Int) {
        viewModelScope.launch {
            setCompletedColorUseCase(color)
        }
    }

    private fun getUnCompletedColor() {
        viewModelScope.launch {
            _unCompletedColor.value = getUncompletedColorUseCase()
        }
    }

    fun setUnCompletedColor(color: Int) {
        viewModelScope.launch {
            setUncompletedColorUseCase(color)
        }
    }
}
