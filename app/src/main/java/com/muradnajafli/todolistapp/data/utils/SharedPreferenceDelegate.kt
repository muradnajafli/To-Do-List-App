package com.muradnajafli.todolistapp.data.utils


import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferenceDelegate(
    private val context: Context,
    private val name: String,
    private val defaultValue: Int = -1
): ReadWriteProperty<Any, Int> {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return sharedPreferences.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(name, value)
            apply()
        }
    }
}

fun Context.sharedPreferences(name: String) = SharedPreferenceDelegate(this, name)
