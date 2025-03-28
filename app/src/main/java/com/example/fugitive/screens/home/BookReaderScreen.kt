package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun BookReaderScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp)
    ) {
        Text("You're in the Book Reader!")

        Button(onClick = { navController.navigate(Screen.BookDetail.route) }) {
            Text("Go Back")
        }
    }
}
