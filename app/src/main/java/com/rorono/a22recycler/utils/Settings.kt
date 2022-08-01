package com.rorono.a22recycler.utils

import android.content.Context

object Settings {

    private const val THEME = "chosen_theme"
    private const val LANGUAGE = "chosen_language"
    private const val ORIENTATION = "chosen_orientation"
    private const val KEY_THEME = "key_theme"
    private const val KEY_LANGUAGE = "key_language"
    private const val KEY_ORIENTATION = "key_orientation"

    fun saveTheme(context: Context, theme: Int) {
        val sharedPreferences = context.getSharedPreferences(THEME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_THEME, theme)
        editor.apply()
    }

    fun saveLanguage(context: Context, language: Int) {
        val sharedPreferences = context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_LANGUAGE, language)
        editor.apply()

    }

    fun saveOrientation(context: Context, orientation: Int) {
        val sharedPreferences = context.getSharedPreferences(ORIENTATION, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_ORIENTATION, orientation)
        editor.apply()
    }

    fun loadTheme(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(THEME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_THEME, 1)
    }

    fun loadLanguage(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_LANGUAGE, 1)
    }

    fun loadOrientation(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(ORIENTATION, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_ORIENTATION, 1)
    }
}