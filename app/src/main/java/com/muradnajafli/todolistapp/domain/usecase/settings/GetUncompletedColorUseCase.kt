package com.muradnajafli.todolistapp.domain.usecase.settings

import com.muradnajafli.todolistapp.utils.ColorManager


class GetUncompletedColorUseCaseImpl(
    private val colorManager: ColorManager
) : GetUncompletedColorUseCase {

    override operator fun invoke() = colorManager.getUncompletedColor()

}

interface GetUncompletedColorUseCase {
    operator fun invoke(): Int
}