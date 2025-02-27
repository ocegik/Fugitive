package com.example.fugitive.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodel.AuthViewModel
import com.example.fugitive.components.FugitivePrimaryButton

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("User Profile")

        Spacer(modifier = Modifier.height(20.dp))

        FugitivePrimaryButton(
            text = "Go to Home",
            onClick = { navController.navigate(Screen.Home.route) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FugitivePrimaryButton(
            text = "Log Out",
            onClick = {
                authViewModel.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true } // Clears navigation stack
                }
            }
        )
    }
}