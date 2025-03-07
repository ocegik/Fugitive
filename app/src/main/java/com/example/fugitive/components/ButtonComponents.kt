package com.example.fugitive.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun BackButton(modifier: Modifier = Modifier,
               onBackClick: () -> Unit)
{
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable { onBackClick() }
            .padding(8.dp) // Add padding from the edges
            .semantics { contentDescription = "Back Button" }

    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp) // Increase size for better UX
        )
    }
}

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

@Composable
fun CustomBookmarkButton(isBookmarked: Boolean, onBookmarkToggle: () -> Unit) {
    Button(
        onClick = onBookmarkToggle,
        modifier = Modifier.size(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)), // Custom color
        shape = RoundedCornerShape(8.dp) // Less rounded corners
    ) {
        Image(
            painter = painterResource(
                id = if (isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark
            ),
            contentDescription = "Bookmark Icon",
            colorFilter = ColorFilter.tint(Color.White), // Ensure visibility
            modifier = Modifier.size(24.dp) // Resize icon if needed
        )
    }
}

