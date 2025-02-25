package com.example.fugitive.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun SubheadingText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 18,
    fontWeight: FontWeight = FontWeight.Medium,
    color: Color = FugitiveColors.subheading
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 32,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = FugitiveColors.heading,
    lineHeight: Int = 40
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        color = color,
        lineHeight = lineHeight.sp
    )
}