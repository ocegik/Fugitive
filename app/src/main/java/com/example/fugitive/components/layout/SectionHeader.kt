package com.example.fugitive.components.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp, // Explicit font size
            fontWeight = FontWeight.Bold, // Explicit weight
            fontStyle = FontStyle.Normal // Explicit style (Italic if needed)
        ),
        color = FugitiveColors.button
    )
    Spacer(modifier = Modifier.height(20.dp))
}