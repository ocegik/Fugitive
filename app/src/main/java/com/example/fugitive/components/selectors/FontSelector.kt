package com.example.fugitive.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fugitive.viewmodels.SettingsViewModel

@Composable
fun FontSelector(settingsViewModel: SettingsViewModel) {
    var selectedSize by remember { mutableStateOf(settingsViewModel.getFontSize().toString()) }

    SegmentedControl(
        options = listOf(
            "Small", "Medium", "Large"
        ),
        selectedOption = selectedSize,
        onOptionSelected = { newSize ->
            selectedSize = newSize
            val fontSize = when (newSize) {
                "Small" -> 12
                "Medium" -> 16
                "Large" -> 20
                else -> 16
            }
            settingsViewModel.saveFontSize(fontSize)
        }
    )
}