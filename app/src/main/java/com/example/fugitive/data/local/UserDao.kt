package com.example.fugitive.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: LocalUser)  // ✅ Saves user in DB

    @Query("SELECT * FROM local_user WHERE userId = :userId LIMIT 1")
    suspend fun getUser(userId: String): LocalUser?  // ✅ Fetches user details

    @Query("DELETE FROM local_user")
    suspend fun clearUser()  // ✅ Clears cached user on logout

    @Query("UPDATE local_user SET name = :name, profilePicture = :profilePicture WHERE userId = :userId")
    suspend fun updateUser(userId: String, name: String, profilePicture: String?)

}