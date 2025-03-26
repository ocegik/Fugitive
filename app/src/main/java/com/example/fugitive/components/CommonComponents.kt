package com.example.fugitive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors



@Composable
fun InfoText(text: String) {
    Text(
        text = text,
        color = FugitiveColors.subheading,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun BookDetailsRow(chapters: Int, year: Int, wordCount: Int) {
    val formattedWordCount = "%,d".format(wordCount) // Adds commas (e.g., 50,000)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(
            "$chapters Chapters",
            "$year",
            "$formattedWordCount Words"
        ).forEach { text ->
            Text(
                text = text,
                color = FugitiveColors.subheading,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
