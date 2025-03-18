package com.example.fugitive.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp


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