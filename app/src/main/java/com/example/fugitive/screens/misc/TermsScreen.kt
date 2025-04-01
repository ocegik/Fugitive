package com.example.fugitive.screens.misc

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun TermsScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues())

    ) {
        // Back Button - Ensuring it's visible and on top
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .padding(start = 15.dp, top = 15.dp)
                .zIndex(2f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FugitiveColors.background)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()) // Allows scrolling if content is long
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            HeadingText("Terms and Conditions", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(16.dp))

            val termsText = Text(
                text ="""
                Last updated: 24 February 2025
                
                Welcome to Fugitive! By signing up and using our app, you agree to these Terms and Conditions. Please read them carefully.

                1. **Acceptance of Terms**  
                   By creating an account or using Fugitive, you acknowledge that you have read, understood, and agreed to these terms. If you do not agree, please do not use the app.

                2. **Account and User Responsibilities**  
                   - You must provide accurate and complete information when creating an account.  
                   - You are responsible for maintaining the confidentiality of your login credentials.  
                   - Any activity under your account is your responsibility. If you suspect unauthorized access, contact us immediately.

                3. **Use of the App**  
                   - Fugitive is designed for reading and managing books. You agree to use the app only for its intended purpose.  
                   - Do not attempt to hack, modify, or exploit the app in any way.

                4. **Privacy Policy**  
                   - We respect your privacy. Please review our **Privacy Policy** to understand how we handle your data.  
                   - By using the app, you consent to the collection and processing of your data as outlined in the Privacy Policy.

                5. **Content Ownership**  
                   - All books, quotes, and materials within the app are either user-generated or sourced legally.  
                   - You are responsible for ensuring that any content you upload does not infringe copyrights or violate laws.

                6. **Restrictions and Prohibited Activities**  
                   You agree not to:  
                   - Share or distribute harmful content (e.g., spam, hate speech, illegal materials).  
                   - Reverse engineer, decompile, or tamper with the app's code.  
                   - Use the app in a way that disrupts or harms other users.

                7. **Modifications to the Terms**  
                   We may update these Terms and Conditions from time to time. You will be notified of major changes, and continued use of the app means you accept the revised terms.

                8. **Termination of Use**  
                   We reserve the right to suspend or terminate accounts that violate these terms.

                9. **Contact Us**  
                   For any questions or concerns regarding these Terms, reach out to us at **fugitivereads@gmail.com**.
            """.trimIndent(),
                fontSize = 14.sp,
                color = FugitiveColors.subheading
            )
            Log.d("TermsScreen", termsText.toString())

            Spacer(modifier = Modifier.height(24.dp))

            FugitivePrimaryButton(
                text = "Back to Sign Up",
                onClick = { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
