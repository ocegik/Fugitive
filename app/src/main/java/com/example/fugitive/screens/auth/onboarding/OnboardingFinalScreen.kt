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
fun OnBoardingFinalScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Glow effects
        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-100).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.14f),
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
            Spacer(modifier = Modifier.height(20.dp))

            // Label
            Text(
                text = "YOU'RE ALL SET",
                color = Color(0xFF6755FF),
                fontSize = 11.sp,
                letterSpacing = 1.8.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    append("Ready to begin\nyour ")
                    withStyle(SpanStyle(color = Color(0xFF6755FF), fontStyle = FontStyle.Italic)) {
                        append("journey")
                    }
                },
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Hero quote card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1E1A38),
                                Color(0xFF18162A)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color(0xFF6755FF).copy(alpha = 0.3f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {
                // Decorative ring
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 30.dp, y = (-30).dp)
                        .border(1.dp, Color(0xFF6755FF).copy(alpha = 0.15f), CircleShape)
                )
                Column {
                    Text(
                        text = "\"Your next",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = "great story",
                        color = Color(0xFF6755FF),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = "awaits.\"",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stats grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatBox(number = "10k+", label = "Books", modifier = Modifier.weight(1f))
                StatBox(number = "50+", label = "Genres", modifier = Modifier.weight(1f))
                StatBox(number = "∞", label = "Adventures", modifier = Modifier.weight(1f))
                StatBox(number = "Free", label = "To start", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Checklist
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(
                    "Save your favourite books effortlessly",
                    "Get personalised recommendations",
                    "Enjoy a seamless reading experience"
                ).forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF6755FF).copy(alpha = 0.15f))
                                .border(1.dp, Color(0xFF6755FF).copy(alpha = 0.4f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✓", color = Color(0xFF6755FF), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = item, color = Color(0xFF999999), fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            FugitivePrimaryButton(
                text = "Start Reading →",
                onClick = { navController.navigate(Screen.Home.route) }
            )
        }
    }
}

@Composable
private fun StatBox(number: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1A1A))
            .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = Color(0xFF999999),
            fontSize = 10.sp,
            letterSpacing = 0.5.sp
        )
    }
}