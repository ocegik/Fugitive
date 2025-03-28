package com.example.fugitive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.koin.compose.koinInject
import com.example.fugitive.data.local.UserPreferences

@Composable
fun FugitiveTheme(content: @Composable () -> Unit) {
    val userPreferences: UserPreferences = koinInject()

    val darkTheme by produceState(initialValue = isSystemInDarkTheme()) {
        value = userPreferences.getThemeMode() == "dark"
    }

    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}