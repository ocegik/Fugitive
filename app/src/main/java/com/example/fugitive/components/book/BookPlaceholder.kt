package com.example.fugitive.components.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.fugitive.components.effects.ShimmerEffect


@Composable
fun BookPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(230.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            ShimmerEffect(widthFraction = 1f, height = 230.dp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        // Book Details Placeholder (Right Side)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            // Shimmer Title Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                ShimmerEffect(widthFraction = 0.6f, height = 24.dp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Shimmer Author Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                ShimmerEffect(widthFraction = 0.4f, height = 14.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Shimmer Description Placeholder (3 lines)
            repeat(3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f - it * 0.2f) // Shrinking width effect
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    ShimmerEffect(widthFraction = 0.9f - it * 0.2f, height = 20.dp)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Buttons Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                ShimmerEffect(widthFraction = 1f, height = 50.dp)
            }

        }
    }
}