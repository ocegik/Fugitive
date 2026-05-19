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

private data class Feature(
    val emoji: String,
    val title: String,
    val description: String
)

@Composable
fun OnBoardingFeaturesScreen(navController: NavController) {
    val features = listOf(
        Feature("🔖", "Smart Bookmarks", "Pick up exactly where you left off — across any device, any time."),
        Feature("🎯", "Curated Picks", "AI-powered recommendations that learn your taste over time."),
        Feature("📊", "Reading Stats", "Track pages, streaks, and milestones with beautiful insights."),
        Feature("🌙", "Night Mode", "Warm tones and adjustable fonts for late-night reading sessions.")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Bottom glow
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 80.dp)
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
                .padding(horizontal = 24.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            StepDots(currentStep = 1, totalSteps = 3)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = buildAnnotatedString {
                    append("Everything you\nneed to ")
                    withStyle(SpanStyle(color = Color(0xFF6755FF), fontStyle = FontStyle.Italic)) {
                        append("love reading")
                    }
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Built for readers who take their stories seriously.",
                color = Color(0xFF999999),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                features.forEach { feature ->
                    FeatureCard(feature = feature)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            FugitivePrimaryButton(
                text = "Continue →",
                onClick = { navController.navigate(Screen.PfpSelect.route) }
            )
        }
    }
}

@Composable
private fun FeatureCard(feature: Feature) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Icon box
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF6755FF).copy(alpha = 0.12f))
                .border(1.dp, Color(0xFF6755FF).copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = feature.emoji, fontSize = 18.sp)
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = feature.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = feature.description,
                color = Color(0xFF999999),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}