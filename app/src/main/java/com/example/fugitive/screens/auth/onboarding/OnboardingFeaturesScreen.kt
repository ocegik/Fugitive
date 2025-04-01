package com.example.fugitive.screens.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.components.CarouselEffect
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun OnBoardingFeaturesScreen(navController: NavController) {
    val pages = listOf(
        "Discover a world of books, curated for your unique reading journey.",
        "Customize your experience, read offline, and explore stories the way you love.",
        "Track your reading progress, get recommendations, and enjoy a seamless experience."
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
            HeadingText(
                text = "Welcome to Fugitive – Your Escape into Stories!",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))

            // 🔥 Use your CarouselWithDots component here
            CarouselEffect(pages)

            Spacer(modifier = Modifier.height(30.dp))

            FugitivePrimaryButton(
                text = "Get Started",
                onClick = {
                    navController.navigate(Screen.PfpSelect.route)
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}