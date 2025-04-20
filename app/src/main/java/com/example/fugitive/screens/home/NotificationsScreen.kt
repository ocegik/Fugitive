package com.example.fugitive.screens.home

import android.icu.text.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.data.models.NotificationStore
import com.example.fugitive.ui.theme.FugitiveColors
import java.util.Date
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import com.example.fugitive.components.cards.NotificationCard

@Composable
fun NotificationsScreen(navController: NavController) {
    val notifications = NotificationStore.notifications
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents status bar overlap
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(
                    modifier = Modifier
                        .zIndex(2f) // Ensures it's above other content
                ) {
                    navController.popBackStack()
                }

                if (notifications.isNotEmpty()) {
                    Button(onClick = { NotificationStore.clear() }) {
                        Text(
                            text = "Clear All",
                            color = FugitiveColors.buttonText
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(40.dp)) // More spacing after the button

            if (notifications.isEmpty()) {
                Text(
                    "No notifications yet.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 60.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    items(notifications) { notif ->
                        NotificationCard(
                            notification = notif,
                            onDelete = { NotificationStore.delete(notif) }
                        )
                    }
                }
            }
        }
    }
}