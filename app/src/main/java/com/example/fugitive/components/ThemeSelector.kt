package com.example.fugitive.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fugitive.viewmodels.UserViewModel
import java.util.Locale

@Composable
fun ThemeSelector(userViewModel: UserViewModel) {
    val currentTheme by userViewModel.userTheme.collectAsState() // Get saved theme

    var selectedTheme by remember { mutableStateOf(currentTheme.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }) } // Fix: Ensure it syncs with currentTheme

    SegmentedControl(
        options = listOf("Light", "Dark", "System"),
        selectedOption = selectedTheme,
        onOptionSelected = { newTheme ->
            selectedTheme = newTheme
            userViewModel.saveUserTheme(newTheme.lowercase()) // Save preference
        }
    )
}