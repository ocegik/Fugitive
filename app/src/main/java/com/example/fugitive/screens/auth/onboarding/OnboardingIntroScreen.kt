package com.example.fugitive.screens.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.components.CarouselEffect
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.button.FugitiveOutlineButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.SubheadingText
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun OnBoardingIntroScreen(navController: NavController) {
    val pages = listOf(
        "Discover a world of books, curated for you.",
        "Customize your reading experience effortlessly.",
        "Read offline and explore stories anytime.",
        "Your next favorite book is just a tap away!"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(10.dp)
            .padding(WindowInsets.statusBars.asPaddingValues()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeadingText("Welcome to Fugitive – Your Escape into Stories!", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(12.dp))

            SubheadingText("Discover a world of books, curated for your unique reading journey. Customize your experience, read offline, and explore stories the way you love.", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(48.dp))

            CarouselEffect(pages = pages)

            Spacer(modifier = Modifier.height(30.dp))


            FugitivePrimaryButton("Get Started",onClick = { navController.navigate(Screen.OnBoardingFeatures.route)})
            Spacer(modifier = Modifier.height(16.dp)) // Added spacing
            FugitiveOutlineButton(
                text = "Skip",
                onClick = { navController.navigate(Screen.Home.route) }
            )
            Spacer(modifier = Modifier.height(50.dp))

        }
        Spacer(modifier = Modifier.height(50.dp))

    }
}
