package com.example.fugitive.screens.settings

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
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun PreferencesScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents status bar overlap
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                BackButton(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 15.dp)
                        .zIndex(2f) // Ensures it's above other content
                ) {
                    navController.popBackStack()
                }
            }

            Spacer(modifier = Modifier.height(40.dp)) // More spacing after the button

            Text(text = "No New Notifications", color = FugitiveColors.heading)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /* Do something */ }) {
                Text(text = "Refresh")
            }
        }
    }
}