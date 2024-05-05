package com.muradnajafli.todolistapp.domain.usecase.settings

import com.muradnajafli.todolistapp.utils.ColorManager


class SetUncompletedColorUseCaseImpl(
    private val colorManager: ColorManager
) : SetUncompletedColorUseCase {

    override operator fun invoke(color: Int) {
        colorManager.saveUncompletedColorToSharedPreferences(color)
    }
}

interface SetUncompletedColorUseCase {
    operator fun invoke(color: Int)
}