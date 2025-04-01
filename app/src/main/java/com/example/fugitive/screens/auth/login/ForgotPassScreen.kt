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
import com.example.fugitive.components.*
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.inputs.EmailInputField
import com.example.fugitive.ui.theme.FugitiveColors



@Composable
fun ForgotPassScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Back Button - Ensuring it's visible and on top
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .padding(start = 15.dp, top = 15.dp)
                .zIndex(2f) // Makes sure it stays on top
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

            HeadingText("Forgot Password", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(24.dp))

            SubheadingText("Enter your email and we'll send you a verification code to reset your password.")

            Spacer(modifier = Modifier.height(24.dp))

            EmailInputField(email = email, onEmailChange = { email = it })

            Spacer(modifier = Modifier.height(24.dp))

            FugitivePrimaryButton(
                text = "Continue",
                onClick = { navController.navigate(Screen.VerifyCode.route) })
        }
    }
}
