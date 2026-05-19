package com.example.fugitive.screens.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fugitive.components.button.FugitiveOutlineButton
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Decorative blurred glow circles
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-60).dp, y = (-80).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.18f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo mark
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF6755FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "F",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Main content
            Column {
                Text(
                    text = "Your reading\ncompanion",
                    color = Color(0xFF999999),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Headline with purple italic accent
                Text(
                    text = buildAnnotatedString {
                        append("Escape into\n")
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF6755FF),
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("great stories")
                        }
                    },
                    color = Color.White,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 44.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "One app. Endless adventures.\nTurn the page to something new.",
                    color = Color(0xFF999999),
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                FugitivePrimaryButton(
                    text = "Login",
                    onClick = { navController.navigate(Screen.Login.route) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                FugitiveOutlineButton(
                    text = "Sign Up",
                    onClick = { navController.navigate(Screen.SignUp.route) }
                )
            }
        }
    }
}