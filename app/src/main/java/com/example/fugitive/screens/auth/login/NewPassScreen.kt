package com.example.fugitive.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.inputs.PassInputField
import com.example.fugitive.components.text.HeadingText
import com.example.fugitive.components.text.SubheadingText
import com.example.fugitive.ui.theme.*


@Composable
fun ResetPassScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            modifier = Modifier
                .fillMaxSize()
                .background(FugitiveColors.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            HeadingText("Set a New Password", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(24.dp))


            SubheadingText("Enter a strong password to secure your account.")

            Spacer(modifier = Modifier.height(24.dp))

            PassInputField(
                password = password,
                onPasswordChange = { password = it },
                label = "New Password",
                placeholder = "Enter your new password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            PassInputField(
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                label = "Confirm Password",
                placeholder = "Confirm your new password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FugitivePrimaryButton(
                text = "Continue",
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.ResetPass.route) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}
