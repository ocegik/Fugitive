package com.example.fugitive.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun CustomBookmarkButton(isBookmarked: Boolean, onBookmarkToggle: () -> Unit) {
    Button(
        onClick = onBookmarkToggle,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp), // Matching size
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray, // Same as FugitivePrimaryButton
            contentColor = FugitiveColors.buttonText
        ),
        shape = RoundedCornerShape(12.dp) // Matching rounded corners
    ) {
        Image(
            painter = painterResource(
                id = if (isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_bookmark
            ),
            contentDescription = "Bookmark Icon",
            colorFilter = ColorFilter.tint(FugitiveColors.buttonText), // Matching text color
            modifier = Modifier.size(25.dp) // Slightly smaller icon for better spacing
        )
    }
}

