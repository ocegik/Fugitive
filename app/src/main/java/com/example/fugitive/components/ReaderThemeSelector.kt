package com.example.fugitive.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fugitive.viewmodels.SettingsViewModel

@Composable
fun ReaderThemeSelector(settingsViewModel: SettingsViewModel) {
    var selectedTheme by remember { mutableStateOf(settingsViewModel.getReaderTheme()) }

    SegmentedControl(
        options = listOf("Light", "Dark", "Sepia"
        ),
        selectedOption = selectedTheme,
        onOptionSelected = { newTheme ->
            selectedTheme = newTheme
            settingsViewModel.saveReaderTheme(newTheme.lowercase()) // Save preference
        }
    )
}