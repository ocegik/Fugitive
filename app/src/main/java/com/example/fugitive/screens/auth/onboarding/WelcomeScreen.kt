package com.example.fugitive.screens.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.button.FugitiveOutlineButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.SubheadingText
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

            HeadingText("Fugitive", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(12.dp))

            SubheadingText("One app. Endless adventures.\nTurn the page to something new.", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(24.dp))

            FugitivePrimaryButton("Login",onClick = { navController.navigate(Screen.Login.route)})

            Spacer(modifier = Modifier.height(16.dp))

            FugitiveOutlineButton(
                "Sign Up",
                onClick = { navController.navigate(Screen.SignUp.route) }
            )

        }


    }
}
