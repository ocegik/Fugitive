package com.example.fugitive.data.local.session

import android.content.Context
import android.content.SharedPreferences
import com.example.fugitive.data.local.UserDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSessionManager(
    context: Context,
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    @Volatile  // Ensures thread safety
    private var localUserId: String? = null

    fun isUserLoggedIn(): Boolean = getUserId() != null

    fun getUserId(): String? {
        return localUserId ?: prefs.getString("user_id", null) ?: auth.currentUser?.uid.also {
            localUserId = it  // Cache result to avoid repeated lookups
        }
    }

    fun setUserId(userId: String) {
        localUserId = userId
        prefs.edit().putString("user_id", userId).apply()
    }

    suspend fun logout() {
        auth.signOut()
        localUserId = null  // Clear memory cache
        prefs.edit().remove("user_id").apply()
        withContext(Dispatchers.IO) { userDao.clearUser() }  // Clear Room DB
    }
}