package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun NotificationsScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(
            modifier = Modifier
                .size(85.dp)  // Bigger and easier to tap
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .padding(start = 0.dp, top = 30.dp)
                .zIndex(1f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }
    }
}
