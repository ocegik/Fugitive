package com.example.fugitive.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
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

