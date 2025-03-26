package com.example.fugitive.session

import android.content.Context
import android.content.SharedPreferences
import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.data.local.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSessionManager(
    context: Context,
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun isUserLoggedIn(): Boolean {
        return getUserId() != null
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null) ?: auth.currentUser?.uid
    }

    suspend fun saveUser(user: FirebaseUser) {
        prefs.edit().putString("user_id", user.uid).apply()

        val cachedUser = CachedUser(
            userId = user.uid,
            name = user.displayName ?: "Unknown",
            email = user.email ?: "",
            profilePicUrl = user.photoUrl?.toString()
        )

        withContext(Dispatchers.IO) {
            userDao.saveUser(cachedUser)  // ✅ Save in Room DB
        }
    }

    suspend fun getCachedUser(): CachedUser? {
        return withContext(Dispatchers.IO) {
            getUserId()?.let { userDao.getUser(it) }
        }
    }

    suspend fun logout() {
        auth.signOut()
        prefs.edit().remove("user_id").apply()
        withContext(Dispatchers.IO) { userDao.clearUser() }  // ✅ Clear cached user
    }
}