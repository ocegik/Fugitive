package com.example.fugitive.screens.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun OnBoardingIntroScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Top-right glow
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-40).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Step dots
            StepDots(currentStep = 0, totalSteps = 3)

            Spacer(modifier = Modifier.height(28.dp))

            // Header
            Text(
                text = "Welcome",
                color = Color(0xFF999999),
                fontSize = 12.sp,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildAnnotatedString {
                    append("Discover your\nnext ")
                    withStyle(SpanStyle(color = Color(0xFF6755FF), fontStyle = FontStyle.Italic)) {
                        append("favourite book")
                    }
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Curated for your unique reading journey — wherever you go.",
                color = Color(0xFF999999),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Hero stat card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1E1E2E))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF6755FF).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(text = "📚", fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "10,000+ Books",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Across every genre imaginable",
                        color = Color(0xFF999999),
                        fontSize = 13.sp
                    )
                }
                // Decorative ring inside card
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp, y = (-20).dp)
                        .border(1.dp, Color(0xFF6755FF).copy(alpha = 0.15f), CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallFeatureCard(
                    emoji = "🌙",
                    title = "Read Offline",
                    subtitle = "No wifi needed",
                    modifier = Modifier.weight(1f)
                )
                SmallFeatureCard(
                    emoji = "✨",
                    title = "Personalised",
                    subtitle = "Just for you",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            FugitivePrimaryButton(
                text = "Get Started →",
                onClick = { navController.navigate(Screen.OnBoardingFeatures.route) }
            )
        }
    }
}

@Composable
private fun SmallFeatureCard(
    emoji: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = subtitle, color = Color(0xFF999999), fontSize = 11.sp)
    }
}

@Composable
fun StepDots(currentStep: Int, totalSteps: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(if (index == currentStep) 28.dp else 18.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (index == currentStep) Color(0xFF6755FF)
                        else Color(0xFF2A2A2A)
                    )
            )
        }
    }
}