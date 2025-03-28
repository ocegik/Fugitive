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
import androidx.navigation.NavController
import com.example.fugitive.components.cards.ContactItem
import com.example.fugitive.components.cards.FAQItem
import com.example.fugitive.components.layout.ScreenTitle
import com.example.fugitive.components.layout.SectionHeader
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun HelpScreen(navController: NavController) {
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

                ScreenTitle("Help & Support")
            }

            // Description
            Text(
                text = "Find answers to common questions or contact support for assistance.",
                style = MaterialTheme.typography.bodyMedium,
                color = FugitiveColors.subheading
            )

            Spacer(modifier = Modifier.height(20.dp))

            // FAQs Section
            SectionHeader(title = "Frequently Asked Questions (FAQ)")

            FAQItem("1. What is Fugitive?", "Fugitive is a digital book library that allows users to read books online and offline with customizable settings for a seamless reading experience.")
            FAQItem("2. Is Fugitive free?", "Yes! Some premium features might be added later.")
            FAQItem("3. Do I need an internet connection?", "Fugitive allows you to read both online and offline However, certain features, like cloud sync and online library access, require an internet connection.")

            Spacer(modifier = Modifier.height(20.dp))

            // Customization Section
            SectionHeader(title = "Customization & Reading Experience")
            FAQItem("4. Can I customize my reading experience?", "Absolutely! Fugitive lets you adjust:\n" +
                    "✔ Font size and style\n" +
                    "✔ App themes (Light, Dark, Sepia)\n" +
                    "✔ Scrolling options (Manual, Tilt-to-Scroll coming soon)")
            FAQItem("5. Is there a dark mode?", "Yes! You can choose Light, Dark, or Sepia themes.")

            Spacer(modifier = Modifier.height(20.dp))

            // Troubleshooting Section
            SectionHeader(title = "Troubleshooting & Support")
            FAQItem("6. The app is not working. What should I do?", "Try these steps:\n" +
                    "- Restart the app\n" +
                    "- Clear cache in settings\n" +
                    "- Reinstall the app\n" +
                    "- Ensure you’re using the latest version")

            Spacer(modifier = Modifier.height(20.dp))

            // Contact Section
            SectionHeader(title = "Need More Help? Contact Us")
            ContactItem(label = "Email", value = "fugitivereads@gmail.com")
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}