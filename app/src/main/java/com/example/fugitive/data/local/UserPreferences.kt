package com.example.fugitive.data.local

import android.content.SharedPreferences

class UserPreferences(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_FONT_SIZE = "font_size"
    }

    fun setThemeMode(mode: String) {
        sharedPreferences.edit().putString(KEY_THEME_MODE, mode).apply()
    }

    fun getThemeMode(): String {
        return sharedPreferences.getString(KEY_THEME_MODE, "dark") ?: "dark"
    }

    fun setFontSize(size: Int) {
        sharedPreferences.edit().putInt(KEY_FONT_SIZE, size).apply()
    }

    fun getFontSize(): Int {
        return sharedPreferences.getInt(KEY_FONT_SIZE, 14)
    }
}
