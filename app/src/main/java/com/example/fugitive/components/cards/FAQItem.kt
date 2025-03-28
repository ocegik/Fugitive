package com.example.fugitive.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun FAQItem(question: String, answer: String) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(
            text = question,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp, // Explicit font size
                fontWeight = FontWeight.Medium, // Explicit weight
                fontStyle = FontStyle.Normal // Explicit style (Italic if needed)
            ),
            color = FugitiveColors.heading
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = answer,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp, // Explicit font size
                fontWeight = FontWeight.Normal, // Explicit weight
                fontStyle = FontStyle.Normal // Explicit style (Italic if needed)
            ),
            color = FugitiveColors.subheading
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}