package com.example.fugitive.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: CachedUser)  // ✅ Saves user in DB

    @Query("SELECT * FROM cached_user WHERE userId = :userId LIMIT 1")
    suspend fun getUser(userId: String): CachedUser?  // ✅ Fetches user details

    @Query("DELETE FROM cached_user")
    suspend fun clearUser()  // ✅ Clears cached user on logout
}