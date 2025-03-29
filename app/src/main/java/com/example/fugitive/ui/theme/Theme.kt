package com.example.fugitive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.koin.compose.koinInject
import com.example.fugitive.data.local.UserPreferences

@Composable
fun FugitiveTheme(content: @Composable () -> Unit) {
    val userPreferences: UserPreferences = koinInject()

    val themeMode by userPreferences.themeFlow.collectAsState()
    val darkTheme = when (themeMode) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme() // "system" or any invalid value
    }

    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}