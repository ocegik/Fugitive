package com.example.fugitive.data.models

data class AppNotification(
    val id: Long = System.currentTimeMillis(),
    val title: String?,
    val body: String?,
    val timestamp: Long = System.currentTimeMillis()
)
