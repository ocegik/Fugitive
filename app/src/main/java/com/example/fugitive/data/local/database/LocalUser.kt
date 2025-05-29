package com.example.fugitive.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user")
data class LocalUser(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val profilePicture: String
)