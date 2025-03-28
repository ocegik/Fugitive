package com.example.fugitive.components.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun ScreenTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = title,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            color = FugitiveColors.heading
        )
    }
}
