package com.example.fugitive.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.inputs.NameInputField
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.viewmodels.UserViewModel

@Composable
fun EditProfileScreen(navController: NavController, userViewModel: UserViewModel, authViewModel: AuthViewModel) {

    val user by userViewModel.user
    val userId = user?.uid

    LaunchedEffect(Unit) {
        if (userId != null) {
            userViewModel.fetchUserDetails(userId)
        }
    }

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

            Text(text = "Name")
            Spacer(modifier = Modifier.height(20.dp))
            NameInputField(name = authViewModel.name, onValueChange = { authViewModel.name = it })


        }
    }
}