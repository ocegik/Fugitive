package com.example.fugitive.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.components.cards.ContactItem
import com.example.fugitive.components.layout.ScreenTitle
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.components.layout.SectionHeader


@Composable
fun AboutUsScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents status bar overlap
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()), // Enables scrolling
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically){

                BackButton{ navController.popBackStack() }
                Spacer(modifier = Modifier.width(15.dp))

                ScreenTitle("About Us")
            }
            Text(
                text = "Welcome to Fugitive – your personalized reading companion.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(20.dp))
            // Description
            Text(
                text = "We believe that books hold the power to transform lives, but finding the right content at the right time isn’t always easy.\n\n"+
                        "That’s where Fugitive comes in.\n\n"+
                        "Our platform is designed for readers who crave a seamless, distraction-free, and customizable reading experience.\n"+
                        "Whether you love classics, contemporary fiction, self-improvement, or niche genres, Fugitive adapts to your preferences, making your reading journey truly personal.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )

            Spacer(modifier = Modifier.height(30.dp))

            // FAQs Section
            SectionHeader(title = "What Makes Us Different?")

            Text(
                text = "Personalized Experience – Tailor your reading environment with themes, fonts, and other customization options.\n" +
                        "\n" +
                        "Offline Access – Enjoy your books anytime, anywhere.\n" +
                        "\n" +
                        "Minimalist Design – A clean interface that keeps your focus on the story.\n" +
                        "\n" +
                        "Future-Ready Features – From AI-driven recommendations to tilt-to-scroll, we are constantly innovating.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )


            Spacer(modifier = Modifier.height(30.dp))

            // Customization Section
            SectionHeader(title = "Meet the Creator")

            Text(
                text = "Fugitive is created by Tarun Choudhary, a passionate book lover and app developer for whom this project is deeply personal.\n\n"+
                        "Books have always been an escape, a source of inspiration, and a way to understand the world.\n"+
                        "With Fugitive, he aims to build something truly remarkable—an app that transforms how people engage with books, making reading more immersive and accessible for everyone.\n\n"+
                        "This is just the beginning, and the vision for Fugitive goes far beyond the present.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Troubleshooting Section
            SectionHeader(title = "Join the Fugitive Community")
            Text(
                text = "Fugitive isn’t just an app; it’s a space for readers who believe in the power of books. Join us and help shape the future of reading.\n" +
                        "\n" +
                        "\uD83D\uDCE9 For inquiries, support, or suggestions, reach out to:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(5.dp))
            ContactItem(label = "Email", value = "fugitivereads@gmail.com")
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Stay curious. Keep reading. Escape into stories with Fugitive.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}