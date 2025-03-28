package com.example.fugitive.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fugitive.viewmodels.UserViewModel

@Composable
fun ReaderThemeSelector(userViewModel: UserViewModel) {
    var selectedTheme by remember { mutableStateOf(userViewModel.getReaderTheme()) }

    SegmentedControl(
        options = listOf("Light", "Dark", "Sepia"
        ),
        selectedOption = selectedTheme,
        onOptionSelected = { newTheme ->
            selectedTheme = newTheme
            userViewModel.saveReaderTheme(newTheme.lowercase()) // Save preference
        }
    )
}