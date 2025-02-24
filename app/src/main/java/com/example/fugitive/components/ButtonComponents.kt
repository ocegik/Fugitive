package com.example.fugitive.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun BackButton(modifier: Modifier = Modifier,
               onBackClick: () -> Unit
)
{
    Box(
        modifier = modifier
            .padding(16.dp) // Add padding from the edges
            .clickable { onBackClick() }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.size(32.dp) // Increase size for better UX
        )
    }
}

@Composable
fun FugitiveOutlineButton(
    text: String,
    onClick: () -> Unit
){
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = FugitiveColors.heading),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        border = ButtonDefaults.outlinedButtonBorder
    ) {Text(text = text, color=FugitiveColors.buttonText) }
}

@Composable
fun FugitivePrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = FugitiveColors.button),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text, color = FugitiveColors.buttonText)
    }
}

