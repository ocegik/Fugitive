package com.example.fugitive.repository

import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.local.session.UserSessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val firestoreService: FirestoreService,  // ✅ Firebase Firestore
    private val authService: FirebaseAuthService,  // ✅ Firebase Authentication
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

    suspend fun signIn(email: String, password: String): Result<CachedUser> {
        return withContext(Dispatchers.IO) {
            try {
                val firebaseUser = authService.signIn(email, password)
                    ?: return@withContext Result.failure(Exception("Invalid email or password."))

                val cachedUser = CachedUser(
                    userId = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "Unknown",
                    email = firebaseUser.email ?: "",
                    profilePicUrl = firebaseUser.photoUrl?.toString()
                )
                userDao.saveUser(cachedUser)  // Save user locally
                sessionManager.saveUser(firebaseUser)  // Save session data

                return@withContext Result.success(cachedUser)
            } catch (e: Exception) {
                return@withContext Result.failure(e)  // Pass error message
            }
        }
    }

    suspend fun signUp(name: String, email: String, password: String): Result<CachedUser> {
        return withContext(Dispatchers.IO) {
            try {
                val firebaseUser = authService.signUp(name, email, password)
                    ?: return@withContext Result.failure(Exception("Sign-up failed. Please try again."))

                val cachedUser = CachedUser(
                    userId = firebaseUser.uid,
                    name = name,
                    email = email,
                    profilePicUrl = firebaseUser.photoUrl?.toString()
                )
                userDao.saveUser(cachedUser)  // ✅ Save user locally
                sessionManager.saveUser(firebaseUser)  // ✅ Store in session manager

                return@withContext Result.success(cachedUser)
            } catch (e: Exception) {
                return@withContext Result.failure(e)  // Pass error message
            }
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