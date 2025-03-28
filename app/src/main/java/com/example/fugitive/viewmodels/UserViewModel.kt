package com.example.fugitive.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fugitive.data.local.UserPreferences
import com.example.fugitive.repository.UserRepository


class UserViewModel(private val userRepository: UserRepository, private val userPreferences: UserPreferences) : ViewModel() {

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
}