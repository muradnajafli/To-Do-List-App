package com.muradnajafli.todolistapp.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private var completedColorPreference by context.sharedPreferences(Constants.COMPLETED_COLOR_KEY)
    private var uncompletedColorPreference by context.sharedPreferences(Constants.UNCOMPLETED_COLOR_KEY)

    fun getCompletedColor(): Int {
        return completedColorPreference
    }

    fun saveCompletedColorToSharedPreferences(color: Int) {
        completedColorPreference = color
    }

    fun getUncompletedColor(): Int {
        return uncompletedColorPreference
    }

    fun saveUncompletedColorToSharedPreferences(color: Int) {
        uncompletedColorPreference = color
    }

}
