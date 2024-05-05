package com.muradnajafli.todolistapp.domain.usecase.settings

import com.muradnajafli.todolistapp.utils.ColorManager

class SetCompletedColorUseCaseImpl(
    private val colorManager: ColorManager
) : SetCompletedColorUseCase {

    override operator fun invoke(color: Int) {
        colorManager.saveCompletedColorToSharedPreferences(color)
    }
}

interface SetCompletedColorUseCase {
    operator fun invoke(color: Int)
}