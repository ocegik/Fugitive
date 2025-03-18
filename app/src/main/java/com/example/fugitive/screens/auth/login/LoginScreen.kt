package com.example.fugitive.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import androidx.compose.material3.Text
import androidx.compose.ui.zIndex
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.EmailInputField
import com.example.fugitive.components.PassInputField
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.SocialLoginRow
import com.example.fugitive.components.SubheadingText
import com.example.fugitive.viewmodel.AuthViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.height(100.dp))

            HeadingText(
                "Welcome Back",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SubheadingText("Enter your details below or continue with your social account")

            Spacer(modifier = Modifier.height(24.dp))

            EmailInputField(email = email, onEmailChange = { email = it })

            Spacer(modifier = Modifier.height(12.dp))

            PassInputField(
                password = password,
                onPasswordChange = { password = it }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text("Remember Me", color = FugitiveColors.buttonText)
                }
                Text("Forgot Password?",
                    color = FugitiveColors.button,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.ForgotPass.route)
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            FugitivePrimaryButton(
                text = if (isLoading) "Logging in..." else "Login",
                onClick = {
                    isLoading = true

                    authViewModel.signIn(
                        email = email,
                        password = password,
                        onSuccess = {
                            isLoading = false
                            showToast(context, "Login successful!") // Call the corrected showToast function
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onError = { errorMessageText ->
                            isLoading = false
                            errorMessage = errorMessageText
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Or Continue With", color = FugitiveColors.subheading)

            Spacer(modifier = Modifier.height(12.dp))

            SocialLoginRow()
        }
    }
}

