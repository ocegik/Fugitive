package com.example.fugitive.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


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


