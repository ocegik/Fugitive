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
            .height(50.dp),
        border = BorderStroke(2.dp, borderColor) // Explicit border definition
    ) {
        Text(text = text, color = textColor)
    }
}

@Composable
fun FugitivePrimaryButton(
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = FugitiveColors.button,
        contentColor = FugitiveColors.buttonText // Default text color
    ),
    shape: Shape = RoundedCornerShape(12.dp),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = colors,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text, color = colors.contentColor) // ✅ Fix: Use colors.contentColor
    }
}
