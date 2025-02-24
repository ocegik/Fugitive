package com.example.fugitive.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun SubheadingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = FugitiveColors.subheading // Uses theme-defined color
    )
}

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = FugitiveColors.heading, // Uses theme-defined color
        lineHeight = 40.sp
    )
}

