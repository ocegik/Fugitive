package com.example.fugitive.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.fugitive.ui.theme.FugitiveColors


@Composable
fun FugitiveOutlineButton(
    text: String,
    textColor: Color = FugitiveColors.buttonText,
    borderColor: Color = FugitiveColors.button,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,  // Keeps it an outline button
            contentColor = textColor
        ),
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        border = BorderStroke(2.dp, borderColor) // Explicit border definition
    ) {
        Text(text = text, color = textColor)
    }
}

@Composable
fun FugitivePrimaryButton(
    modifier: Modifier = Modifier
        .fillMaxWidth()  // Full width
        .height(60.dp),   // Bigger button
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = FugitiveColors.button,
        contentColor = FugitiveColors.buttonText
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = colors,
        shape = shape,
        modifier = modifier
    ) {
        Text(text, color = colors.contentColor)
    }
}
