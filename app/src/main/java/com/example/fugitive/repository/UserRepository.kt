package com.example.fugitive.repository

import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.session.UserSessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val firestoreService: FirestoreService,  // ✅ Firebase
    private val authService: FirebaseAuthService,  // ✅ FirebaseAuth for current user
    private val sessionManager: UserSessionManager
) {

    suspend fun getCurrentUser(): CachedUser? {
        return withContext(Dispatchers.IO) {
            // 1️⃣ Check Firebase Authentication first
            val firebaseUser = authService.getCurrentUser()
            if (firebaseUser != null) {
                return@withContext CachedUser(
                    userId = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "Unknown",
                    email = firebaseUser.email ?: "",
                    profilePicUrl = firebaseUser.photoUrl?.toString()
                )
            }

            // 2️⃣ If no Firebase user, check local storage (offline mode)
            val userId = sessionManager.getUserId() ?: return@withContext null
            return@withContext userDao.getUser(userId)
        }
    }

    suspend fun signIn(email: String, password: String): CachedUser? {
        return withContext(Dispatchers.IO) {
            val firebaseUser = authService.signIn(email, password)
            firebaseUser?.let { user ->
                val cachedUser = CachedUser(
                    userId = user.uid,
                    name = user.displayName ?: "Unknown",
                    email = user.email ?: "",
                    profilePicUrl = user.photoUrl?.toString()
                )
                userDao.saveUser(cachedUser)  // Save user locally
                sessionManager.saveUser(user)  // Save session data
                return@withContext cachedUser
            }
            return@withContext null
        }
    }
    suspend fun signUp(name: String, email: String, password: String): CachedUser? {
        return withContext(Dispatchers.IO) {
            val firebaseUser = authService.signUp(name, email, password)  // ✅ Ensure authService has signUp()
            firebaseUser?.let { user ->
                val cachedUser = CachedUser(
                    userId = user.uid,
                    name = name,
                    email = email,
                    profilePicUrl = user.photoUrl?.toString()
                )
                userDao.saveUser(cachedUser)  // ✅ Save user locally
                sessionManager.saveUser(user)  // ✅ Store in session manager
                return@withContext cachedUser
            }
            return@withContext null
        }
    }

    /**
     * ✅ Logs out the user, clears session and local cache.
     */
    suspend fun logout() {
        withContext(Dispatchers.IO) {
            authService.signOut()
            sessionManager.logout()
            userDao.clearUser()  // Remove locally stored user
        }
    }
}