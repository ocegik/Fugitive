package com.example.fugitive.screens.auth.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fugitive.components.*
import com.example.fugitive.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsCond by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


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


            HeadingText("Create Account", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Enter your details below or continue with your social account",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = FugitiveColors.subheading
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                placeholder = { Text("Enter your name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.fillMaxWidth()
            )

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
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = termsCond,
                    onCheckedChange = { termsCond = it }
                )


                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = FugitiveColors.subheading
                        )
                    ) {
                        append("Agree With ")
                    }
                    pushStringAnnotation(
                        tag = "Terms & Conditions",
                        annotation = "Terms & Conditions"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = FugitiveColors.button,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Terms & Conditions")
                    }
                    pop()
                }

                Text(
                    text = annotatedString,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Terms.route)
                    }
                        .padding(start = 8.dp)
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                FugitivePrimaryButton(text = "Sign Up", onClick = {
                    errorMessage = "" // Reset error before validation

                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        errorMessage = "All fields are required."
                        return@FugitivePrimaryButton
                    }

                    if (!termsCond) {
                        errorMessage = "You must agree to the Terms & Conditions."
                        return@FugitivePrimaryButton
                    }

                    if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters long."
                        return@FugitivePrimaryButton
                    }

                    authViewModel.signUp(
                        name, email, password,  // ✅ Added name
                        onSuccess = {
                            isLoading = false  // ✅ Changed location
                            Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onError = { error ->
                            isLoading = false  // ✅ Changed location
                            errorMessage = error
                        }
                    )

                    isLoading = true  // ✅ Moved this after signUp call
                })

                Spacer(modifier = Modifier.height(30.dp))

                Text("Or Sign Up With", color = FugitiveColors.subheading)

                Spacer(modifier = Modifier.height(12.dp))

                SocialLoginRow()

            }
        }
    }
}




