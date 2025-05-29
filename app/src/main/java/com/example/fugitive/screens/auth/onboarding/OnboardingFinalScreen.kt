package com.example.fugitive.screens.auth.onboarding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.components.effects.CarouselEffect
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.text.HeadingText
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.text.SubheadingText
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun OnBoardingFinalScreen(navController: NavController) {
    val finalMessages = listOf(
        "Save your favorite books effortlessly.",
        "Explore thousands of new stories.",
        "Customize your reading experience.",
        "Enjoy a seamless, distraction-free reading journey."
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
                text = "You're Ready to Begin!",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            SubheadingText(
                text = "Your reading journey starts here. Save your favorite books, explore new stories, and make Fugitive your own.",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Final Illustration or Animation Placeholder (Optional)
            CarouselEffect(pages = finalMessages)

            Spacer(modifier = Modifier.height(30.dp))

            FugitivePrimaryButton(
                text = "Start Reading",
                onClick = { navController.navigate(Screen.Home.route) }
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
