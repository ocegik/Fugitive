package com.example.fugitive.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.Screen

@Composable
fun BookDetailsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("BookDetailsScreen")
        Button(onClick = { navController.navigate(Screen.BookReader.route) }) {
            Text("Read Book")
        }
    }
}
