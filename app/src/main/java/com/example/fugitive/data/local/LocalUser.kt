package com.example.fugitive.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user")
data class LocalUser(
    @PrimaryKey val userId: String,
    val name: String,
    val email: String,
    val profilePicture: String
)