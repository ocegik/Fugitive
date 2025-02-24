package com.example.fugitive.screens.misc

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
import com.example.fugitive.Screen
import com.example.fugitive.components.*
import com.example.fugitive.ui.theme.FugitiveColors


@Composable
fun VerifyCodeScreen(navController: NavController) {
    var otp by remember { mutableStateOf("") }

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

            HeadingText("Enter Verification Code", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(24.dp))

            SubheadingText("We’ve sent a code to your email. Enter it below to continue.")

            Spacer(modifier = Modifier.height(40.dp))

            OtpTextField(4, onOtpEntered = { otp = it })

            Spacer(modifier = Modifier.height(30.dp))

            FugitivePrimaryButton("Continue", onClick = { navController.navigate(Screen.ResetPass.route) })
        }
    }
}
