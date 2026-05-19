package com.example.fugitive.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fugitive.components.button.FugitivePrimaryButton

@Composable
fun FeaturedBook(
    title: String,
    author: String,
    description: String,
    imageUri: String?,
    onReadClick: () -> Unit
) {
    // Outer card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1A1A2E))
            .border(
                width = 1.dp,
                color = Color(0xFF6755FF).copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        // Subtle purple glow in top-right of card
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = (-60).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.18f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            // Book cover with glow shadow effect via border
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(190.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2A2A2A))
                    .border(
                        1.dp,
                        Color(0xFF6755FF).copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    )
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(190.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        lineHeight = 22.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = author,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF999999)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color(0xFF999999),
                        maxLines = 3,
                        lineHeight = 18.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                FugitivePrimaryButton(
                    text = "Start Reading",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    onClick = onReadClick
                )
            }
        }
    }
}