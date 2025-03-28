package com.example.fugitive.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.R
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.cards.ProfileEditItem
import com.example.fugitive.components.cards.StatCard
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.components.button.FugitivePrimaryButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val authViewModel: AuthViewModel = koinViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.statusBars.asPaddingValues())
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            BackButton(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .zIndex(2f) // Ensures it's above other content
            ) {
                navController.popBackStack()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.
            padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.user_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)

            )
            HeadingText("Tarun Choudhary")
        }


        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.
            padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            StatCard(icon = Icons.Default.Star, value = "10", label = "Books Finished")
            StatCard(icon = Icons.Filled.DateRange, value = "12", label = "Days Streak")
            StatCard(icon = Icons.Filled.PlayArrow, value = "30", label = "Pages Read")

        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            ProfileEditItem(
                title = "Edit Profile",
                icon = Icons.Filled.AccountCircle,
                onClick = { navController.navigate(Screen.EditProfile.route) })

            Spacer(modifier = Modifier.height(15.dp))

            ProfileEditItem(
                title = "Saved Quotes",
                icon = Icons.Filled.Favorite,
                onClick = { navController.navigate(Screen.SavedQuotes.route) })

            Spacer(modifier = Modifier.height(15.dp))

            ProfileEditItem(
                title = "My Stats",
                icon = Icons.Filled.Star,
                onClick = { navController.navigate(Screen.MyStats.route) })

            Spacer(modifier = Modifier.height(15.dp))

            ProfileEditItem(
                title = "Preferences",
                icon = Icons.Filled.Settings,
                onClick = { navController.navigate(Screen.Preferences.route) })

            Spacer(modifier = Modifier.height(15.dp))

            ProfileEditItem(
                title = "About Us",
                icon = Icons.Filled.Info,
                onClick = { navController.navigate(Screen.AboutUs.route) })

            Spacer(modifier = Modifier.height(15.dp))

            ProfileEditItem(
                title = "Help & Support",
                icon = Icons.Filled.Warning,
                onClick = { navController.navigate(Screen.Help.route) })

            Spacer(modifier = Modifier.height(40.dp))

            FugitivePrimaryButton(
                text = "Log Out",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = FugitiveColors.buttonText
                ),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .width(150.dp)
                    .height(45.dp),
                onClick = {
                    authViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true } // Clears navigation stack
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
        }

    }
}