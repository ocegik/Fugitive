package com.example.fugitive.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: LocalUser)  // ✅ Saves user in DB

    @Query("SELECT * FROM local_user WHERE uid = :uid LIMIT 1")
    suspend fun getUser(uid: String): LocalUser?  // ✅ Fetches user details

    // 🔹 Live Data Flow (auto-updates UI when DB changes)
    @Query("SELECT * FROM local_user WHERE uid = :uid LIMIT 1")
    fun getUserFlow(uid: String): Flow<LocalUser?>

    @Query("DELETE FROM local_user")
    suspend fun clearUser()  // ✅ Clears cached user on logout

    @Query("UPDATE local_user SET name = :name, profilePicture = :profilePicture WHERE uid = :uid")
    suspend fun updateUser(uid: String, name: String, profilePicture: String?)

}