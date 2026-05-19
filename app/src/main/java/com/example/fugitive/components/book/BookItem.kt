package com.example.fugitive.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun BookItem(
    title: String,
    author: String,
    imageUri: String?,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() }
    ) {
        // Cover
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1E1E1E)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
            )

            // Subtle gradient overlay at the bottom for text legibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF121212).copy(alpha = 0.6f)
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 2,
            lineHeight = 18.sp,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = author,
            fontSize = 11.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}