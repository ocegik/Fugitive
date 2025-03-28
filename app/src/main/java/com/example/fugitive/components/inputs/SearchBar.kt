package com.example.fugitive.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun SearchBar(onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = lerp(FugitiveColors.background, Color.White, 0.2f), // Custom background color
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search, // Search icon
                contentDescription = "Search",
                tint = FugitiveColors.heading.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Find your next favorite read…",
                color = FugitiveColors.subheading.copy(alpha = 0.6f), // Placeholder text color
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
        }
    }
}