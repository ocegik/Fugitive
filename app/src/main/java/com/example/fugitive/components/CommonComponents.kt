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

@Composable
fun BookPlaceholder() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(170.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush = Brush.linearGradient(
                    colors = listOf(Color.LightGray, Color.Gray),
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f)
                ))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(16.dp)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(12.dp)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        )
    }
}


