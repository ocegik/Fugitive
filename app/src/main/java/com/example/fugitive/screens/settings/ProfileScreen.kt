package com.example.fugitive.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fugitive.R
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.viewmodels.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {

    val user by userViewModel.user
    val authViewModel: AuthViewModel = koinViewModel()

    val animalToDrawableMap = mapOf(
        "Lion"  to R.drawable.lion,
        "Owl"   to R.drawable.owl,
        "Sale"  to R.drawable.sale,
        "Koala" to R.drawable.koala,
        "Zebra" to R.drawable.zebra,
        "Dog"   to R.drawable.dog,
        "Camel" to R.drawable.camel,
        "Hippo" to R.drawable.hippo
    )

    val profileImage = user?.let { animalToDrawableMap[it.profilePicture] } ?: R.drawable.owl

    LaunchedEffect(user) {
        user?.let {
            Log.d("ProfileUserCheck", "UID=${it.uid}, Name=${it.name}, PFP=${it.profilePicture}")
        } ?: Log.d("ProfileUserCheck", "User is null or not yet fetched")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Ambient glow behind the avatar area
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-60).dp, y = (-40).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.13f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.statusBars.asPaddingValues())
                .navigationBarsPadding()
        ) {

            // ── Back button ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp)
            ) {
                BackButton(modifier = Modifier.zIndex(2f)) {
                    navController.popBackStack()
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Avatar + info card ───────────────────────────────────
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1A1A2E))
                    .border(
                        1.dp,
                        Color(0xFF6755FF).copy(alpha = 0.2f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                // Subtle inner glow
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 40.dp, y = (-40).dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF6755FF).copy(alpha = 0.12f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF6755FF).copy(alpha = 0.7f), CircleShape)
                    ) {
                        AsyncImage(
                            model = profileImage,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = "Hello,",
                            color = Color(0xFF999999),
                            fontSize = 12.sp
                        )
                        Text(
                            text = user?.name?.split(" ")?.firstOrNull() ?: "Guest",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp
                        )
                        user?.email?.let { email ->
                            Text(
                                text = email,
                                color = Color(0xFF999999),
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── ACCOUNT ──────────────────────────────────────────────
            SectionLabel("ACCOUNT")
            Spacer(modifier = Modifier.height(8.dp))

            MenuGroup {
                ProfileMenuItem(
                    title = "Edit Profile",
                    subtitle = "Name, avatar, bio",
                    icon = Icons.Filled.AccountCircle,
                    iconTint = Color(0xFF6755FF),
                    onClick = { navController.navigate(Screen.EditProfile.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "Saved Quotes",
                    subtitle = "Your highlighted passages",
                    icon = Icons.Filled.Favorite,
                    iconTint = Color(0xFFE05C5C),
                    onClick = { navController.navigate(Screen.SavedQuotes.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "Reading Streak",
                    subtitle = "Keep your daily streak alive",
                    icon = Icons.Filled.Star,
                    iconTint = Color(0xFFF5A623),
                    onClick = { navController.navigate(Screen.MyStats.route) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── READING ───────────────────────────────────────────────
            SectionLabel("READING")
            Spacer(modifier = Modifier.height(8.dp))

            MenuGroup {
                ProfileMenuItem(
                    title = "My Stats",
                    subtitle = "Books finished, pages, time read",
                    icon = Icons.Filled.DateRange,
                    iconTint = Color(0xFF4ECDC4),
                    onClick = { navController.navigate(Screen.MyStats.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "Preferences",
                    subtitle = "Font size, theme, language",
                    icon = Icons.Filled.Settings,
                    iconTint = Color(0xFF6755FF),
                    onClick = { navController.navigate(Screen.Preferences.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "Download Manager",
                    subtitle = "Offline books & storage used",
                    icon = Icons.Filled.Build,
                    iconTint = Color(0xFF999999),
                    onClick = { /* TODO */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── MORE ─────────────────────────────────────────────────
            SectionLabel("MORE")
            Spacer(modifier = Modifier.height(8.dp))

            MenuGroup {
                ProfileMenuItem(
                    title = "Notifications",
                    subtitle = "Alerts, reminders & updates",
                    icon = Icons.Filled.Notifications,
                    iconTint = Color(0xFFF5A623),
                    onClick = { navController.navigate(Screen.Notification.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "About Fugitive",
                    subtitle = "Version, credits, licenses",
                    icon = Icons.Filled.Info,
                    iconTint = Color(0xFF4ECDC4),
                    onClick = { navController.navigate(Screen.AboutUs.route) }
                )
                MenuDivider()
                ProfileMenuItem(
                    title = "Help & Support",
                    subtitle = "FAQs and contact us",
                    icon = Icons.Filled.Warning,
                    iconTint = Color(0xFF999999),
                    onClick = { navController.navigate(Screen.Help.route) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Log out ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF2A0A0A))
                    .border(1.dp, Color(0xFF8B2020).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .clickable { authViewModel.signOut(navController) }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = null,
                        tint = Color(0xFFE05C5C),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Log Out",
                        color = Color(0xFFE05C5C),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(52.dp))
        }
    }
}

// ── Helpers ──────────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = Color(0xFF999999),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
private fun MenuGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(16.dp)),
        content = content
    )
}

@Composable
private fun MenuDivider() {
    Box(
        modifier = Modifier
            .padding(start = 56.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color(0xFF2A2A2A))
    )
}

@Composable
private fun ProfileMenuItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color(0xFF999999),
                fontSize = 11.sp
            )
        }

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF444444),
            modifier = Modifier.size(18.dp)
        )
    }
}