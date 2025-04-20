package com.example.fugitive.data.models

import androidx.compose.runtime.mutableStateListOf

object NotificationStore {
    private val _notifications = mutableStateListOf<AppNotification>()
    val notifications: List<AppNotification> get() = _notifications

    fun add(notification: AppNotification) {
        _notifications.add(0, notification) // Newest first
    }

    fun delete(notification: AppNotification) {
        _notifications.remove(notification)
    }

    fun clear() {
        _notifications.clear()
    }
}
