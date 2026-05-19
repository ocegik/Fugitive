package com.example.fugitive.screens.auth.onboarding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.fugitive.components.effects.CarouselSelectEffect
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun PfpSelectScreen(navController: NavController, userViewModel: UserViewModel) {
    var selectedPfp by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val user by userViewModel.user

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Top-right glow
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-60).dp)
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
                .padding(12.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(bottom = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            StepDots(currentStep = 2, totalSteps = 3)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = buildAnnotatedString {
                    append("Choose your\n")
                    withStyle(SpanStyle(color = Color(0xFF6755FF), fontStyle = FontStyle.Italic)) {
                        append("reading persona")
                    }
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Pick an avatar that tells your story.",
                color = Color(0xFF999999),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ✅ Original CarouselSelectEffect — logic completely untouched
            CarouselSelectEffect { selectedPfp = it }

            Spacer(modifier = Modifier.weight(1f))

            // Hint shown until user picks something
            if (selectedPfp == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF1A1A1A))
                        .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(14.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Swipe to browse, tap to select",
                        color = Color(0xFF999999),
                        fontSize = 13.sp
                    )
                }
            } else {
                // Selection confirmation strip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF6755FF).copy(alpha = 0.08f))
                        .border(
                            1.dp,
                            Color(0xFF6755FF).copy(alpha = 0.25f),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6755FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Avatar selected: $selectedPfp",
                        color = Color(0xFF6755FF),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                FugitivePrimaryButton(
                    text = "Confirm & Continue",
                    onClick = {
                        coroutineScope.launch {
                            user?.let { currentUser ->
                                val profilePicture = selectedPfp ?: "default_pfp"
                                Log.d("PfpSelectScreen", "Updating PFP: $profilePicture for UID: ${currentUser.uid}")
                                userViewModel.updateUserData(currentUser.uid, profilePic = profilePicture)
                            }
                        }
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
        }
    }
}