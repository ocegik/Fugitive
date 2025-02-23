package com.example.fugitive.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.components.*


@Composable
fun VerifyCodeScreen(navController: NavController) {
    var otp by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        OtpTextField(4, onOtpEntered = {otp = it})
        PrimaryButton("Continue", onClick = {navController.navigate(Screen.ResetPass.route)})
    }
}
