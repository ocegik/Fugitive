package com.example.fugitive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    val formattedWordCount = "%,d".format(wordCount)

    val stats = listOf(
        Triple("📖", chapters.toString(), "Chapters"),
        Triple("📅", year.toString(), "Published"),
        Triple("✍️", formattedWordCount, "Words"),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stats.forEach { (icon, value, label) ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF1A1A1A))
                    .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(14.dp))
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = icon, fontSize = 16.sp)
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = label,
                    color = Color(0xFF999999),
                    fontSize = 10.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
