package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.components.FugitivePrimaryButton
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text("Welcome to Fugitive!", color = FugitiveColors.heading)

        Spacer(modifier = Modifier.height(24.dp))

        FugitivePrimaryButton("Go to Settings", onClick = { navController.navigate(Screen.Settings.route) })

        Spacer(modifier = Modifier.height(24.dp))

        FugitivePrimaryButton("Go to Book Details", onClick = { navController.navigate(Screen.BookDetail.route) })

        }
    }

