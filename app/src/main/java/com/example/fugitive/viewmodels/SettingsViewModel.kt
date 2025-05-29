package com.example.fugitive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fugitive.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.StateFlow


class SettingsViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    val userTheme: StateFlow<String> = userPreferences.themeFlow


    fun saveUserTheme(mode: String) {
        userPreferences.setThemeMode(mode)
    }

    fun getUserTheme(): String {
        return userPreferences.getThemeMode()
    }

    fun saveFontSize(size: Int) {
        userPreferences.setFontSize(size)
    }

    fun getFontSize(): Int {
        return userPreferences.getFontSize()
    }

    // 🚀 Reader-specific settings (store them but don't use them yet)
    fun saveReaderTheme(theme: String) {
        userPreferences.setReaderTheme(theme)
    }

    fun getReaderTheme(): String {
        return userPreferences.getReaderTheme()
    }

    fun saveFontStyle(style: String) {
        userPreferences.setFontStyle(style)
    }

    fun getFontStyle(): String {
        return userPreferences.getFontStyle()
    }
}
