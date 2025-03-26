package com.example.fugitive.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_user")
data class CachedUser(
    @PrimaryKey val userId: String,  // ✅ Unique ID (same as Firebase UID)
    val name: String,
    val email: String,
    val profilePicUrl: String?
)