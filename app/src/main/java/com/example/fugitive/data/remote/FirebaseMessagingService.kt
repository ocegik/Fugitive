package com.example.fugitive.data.remote

import com.example.fugitive.data.models.AppNotification
import com.example.fugitive.data.models.NotificationStore
import com.example.fugitive.utils.NotificationManagerUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if the message contains a notification payload
        remoteMessage.notification?.let {
            // Handle notification (e.g., show a local notification)
            NotificationManagerUtil.showNotification(applicationContext, it.title, it.body)
            NotificationStore.add(
                AppNotification(
                    title = it.title,
                    body = it.body
                )
            )
        }

        // Optionally check if the message contains data payload and process it
        remoteMessage.data.isNotEmpty().let {
            // Process custom data payload here (if needed)
        }
    }
}
