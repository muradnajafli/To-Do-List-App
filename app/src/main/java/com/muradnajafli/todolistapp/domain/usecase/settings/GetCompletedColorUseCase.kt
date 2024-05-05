package com.muradnajafli.todolistapp.domain.usecase.settings

import com.muradnajafli.todolistapp.utils.ColorManager


class GetCompletedColorUseCaseImpl(
    private val colorManager: ColorManager
) : GetCompletedColorUseCase {

    override operator fun invoke() = colorManager.getCompletedColor()

}
interface GetCompletedColorUseCase {

    operator fun invoke(): Int

}