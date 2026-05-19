package com.example.fugitive.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.ui.theme.FugitiveColors
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.zIndex
import com.example.fugitive.components.*
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.inputs.EmailInputField
import com.example.fugitive.components.inputs.PassInputField
import com.example.fugitive.components.inputs.TermsCheckbox
import com.example.fugitive.components.text.HeadingText
import com.example.fugitive.viewmodels.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.height(60.dp))


            HeadingText("Create Account",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Enter your details below or continue with your social account",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = FugitiveColors.subheading
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = authViewModel.name,
                onValueChange = { authViewModel.name = it },
                label = { Text("Name") },
                placeholder = { Text("Enter your name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            EmailInputField(email = authViewModel.email, onEmailChange = { authViewModel.email = it })

            Spacer(modifier = Modifier.height(20.dp))

            PassInputField(
                password = authViewModel.password,
                onPasswordChange = { authViewModel.password = it }
            )


            Spacer(modifier = Modifier.height(20.dp))

            TermsCheckbox(
                isChecked = authViewModel.termsCond,
                onCheckedChange = { authViewModel.termsCond = it },
                navController = navController
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (authViewModel.isLoading) {
                CircularProgressIndicator()
            } else {
                FugitivePrimaryButton(
                    text = "Sign Up",
                    onClick = {
                        authViewModel.signUp(
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





