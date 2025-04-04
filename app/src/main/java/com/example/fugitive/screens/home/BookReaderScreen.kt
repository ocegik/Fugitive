package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel

@Composable
fun BookReaderScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    bookId: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp)
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .zIndex(2f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }

    }
}
