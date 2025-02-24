package com.example.fugitive.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object FugitiveColors {
    val background: Color
        @Composable get() = MaterialTheme.colorScheme.background
    val heading: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground
    val subheading: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface
    val button: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    val buttonText: Color
        @Composable get() = MaterialTheme.colorScheme.onPrimary

}
