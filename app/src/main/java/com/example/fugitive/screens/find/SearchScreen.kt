package com.example.fugitive.screens.find

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
import com.example.fugitive.Screen
import com.example.fugitive.components.BackButton
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun SearchScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Back Button - Ensuring it's visible and on top
        BackButton(
            modifier = Modifier
                .size(85.dp)  // Bigger and easier to tap
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .padding(start = 0.dp, top = 30.dp)
                .zIndex(1f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text("You're in the Book Reader!")

            Button(onClick = { navController.navigate(Screen.BookDetail.route) }) {
                Text("Go Back")
            }
        }
    }
}
