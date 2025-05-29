package com.example.fugitive.screens.auth.onboarding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.components.effects.CarouselSelectEffect
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.text.HeadingText
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun PfpSelectScreen(navController: NavController, userViewModel: UserViewModel) {

    var selectedPfp by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val user by userViewModel.user

    // Launch a coroutine to fetch the cached user and update the userId

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

            HeadingText("Select Your Profile Picture", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

            Spacer(modifier = Modifier.height(48.dp))

            CarouselSelectEffect{ selectedPfp = it}

            Spacer(modifier = Modifier.height(30.dp))

            FugitivePrimaryButton("Confirm & Continue", onClick = {
                coroutineScope.launch {
                    user?.let { currentUser -> // ✅ Ensure userId is not null
                        val profilePicture = selectedPfp ?: "default_pfp"
                        Log.d("PfpSelectScreen", "Attempting to update PFP: $profilePicture for UID: ${currentUser.uid}") // Add this line
                        userViewModel.updateUserData(currentUser.uid, profilePic = profilePicture)
                    }
                }
                navController.navigate(Screen.OnBoardingFinal.route)
            })
            Spacer(modifier = Modifier.height(16.dp)) // Added spacing
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}
