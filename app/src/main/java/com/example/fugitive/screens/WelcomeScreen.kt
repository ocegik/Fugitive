package com.example.fugitive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.Screen
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Fugitive",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = FugitiveColors.heading
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "One app. Endless adventures.\nTurn the page to something new.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = FugitiveColors.subheading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                colors = ButtonDefaults.buttonColors(containerColor = FugitiveColors.button),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login", color = FugitiveColors.buttonText)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.SignUp.route) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = FugitiveColors.heading),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Text(text = "Sign-up")
            }

        }


    }
}
