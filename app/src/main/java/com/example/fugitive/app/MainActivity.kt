package com.example.fugitive.app

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fugitive.ui.theme.FugitiveTheme
import com.example.fugitive.utils.NotificationPermissionHelper


class MainActivity : ComponentActivity() {

    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the launcher
        notificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("Permission", "Notification permission granted")
                // Handle success
            } else {
                Log.d("Permission", "Notification permission denied")
                // Handle denial
            }
        }

        requestNotificationPermissionIfNeeded()

        enableEdgeToEdge()
        setContent {
            FugitiveTheme {
                MyApp()
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (NotificationPermissionHelper.shouldRequestPermission() &&
            !NotificationPermissionHelper.isPermissionGranted(this)
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Log.d("Permission", "Notification already granted or not needed")
        }
    }
}


