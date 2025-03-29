package com.example.fugitive.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fugitive.viewmodels.UserViewModel

@Composable
fun FontSizeSelector(userViewModel: UserViewModel) {
    var selectedSize by remember { mutableStateOf(userViewModel.getFontSize().toString()) }

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
            userViewModel.saveFontSize(fontSize)
        }
    )
}