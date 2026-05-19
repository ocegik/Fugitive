package com.example.fugitive.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.inputs.EmailInputField
import com.example.fugitive.components.inputs.PassInputField
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.text.HeadingText
import com.example.fugitive.components.SocialLoginRow
import com.example.fugitive.components.text.SubheadingText
import com.example.fugitive.viewmodels.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Back Button - Ensuring it's visible and on top
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 15.dp, top = 15.dp)
                .zIndex(2f)
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FugitiveColors.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            HeadingText(
                "Welcome Back",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(40.dp))

            SubheadingText("Enter your details below or continue with your social account")

            Spacer(modifier = Modifier.height(30.dp))

            EmailInputField(
                email = authViewModel.email,
                onEmailChange = { authViewModel.email = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            PassInputField(
                password = authViewModel.password,
                onPasswordChange = { authViewModel.password = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = authViewModel.rememberMe,
                        onCheckedChange = { authViewModel.rememberMe = it }
                    )
                    Text("Remember Me", color = FugitiveColors.buttonText)
                }
                Text(
                    "Forgot Password?",
                    color = FugitiveColors.button,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.ForgotPass.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (authViewModel.errorMessage.isNotEmpty()) {
                Text(text = authViewModel.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (authViewModel.isLoading) {
                CircularProgressIndicator()
            } else {
                FugitivePrimaryButton(
                    text = "Login",
                    onClick = {
                        authViewModel.signIn(
                            navController = navController,
                            context = context
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SocialLoginRow(authViewModel, navController)
        }
    }
}