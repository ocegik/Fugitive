package com.example.fugitive.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme


// Dark Theme Colors
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6755FF), // Buttons and visual elements
    background = Color(0xFF121212), // Background
    onBackground = Color.White, // Headings
    onSurface = Color(0xFF999999), // Subheadings
    onPrimary = Color.White // Text on primary buttons
)

// Light Theme Colors
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6755FF), // Buttons and visual elements
    background = Color(0xFFF5F5F4), // Background
    onBackground = Color(0xFF333333), // Headings
    onSurface = Color(0xFF999999), // Subheadings
    onPrimary = Color.White // Text on primary buttons
)
