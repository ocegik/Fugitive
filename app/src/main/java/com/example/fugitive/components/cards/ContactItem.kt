package com.example.fugitive.components.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun ContactItem(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            color = FugitiveColors.heading
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp
            ),
            color = FugitiveColors.button
        )
    }
}