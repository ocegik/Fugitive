package com.example.fugitive.screens

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.fugitive.components.EmailInputField
import com.example.fugitive.components.PrimaryButton
import com.example.fugitive.components.SocialLoginRow

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome Back",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = FugitiveColors.heading
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Enter your details below or continue with your social account",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = FugitiveColors.subheading
        )

        Spacer(modifier = Modifier.height(24.dp))

        EmailInputField(email = email, onEmailChange = { email = it })

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Text("Remember Me", color = FugitiveColors.button)
            }
            Text("Forgot Password?",
                color = FugitiveColors.button,
                modifier = Modifier.clickable{
                    navController.navigate(Screen.ForgotPass.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(text = "Login", onClick = { navController.navigate(Screen.Home.route) })

        Spacer(modifier = Modifier.height(20.dp))

        Text("Or Continue With", color = FugitiveColors.subheading)

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginRow()
    }
}

