package com.example.fugitive.repository

import android.content.Context
import com.example.fugitive.data.local.LocalUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.local.session.UserSessionManager
import com.example.fugitive.data.remote.FirebaseAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val authService: FirebaseAuthService,  // ✅ Firebase Authentication
    private val sessionManager: UserSessionManager,
    private val userRepository: UserRepository
) {
    suspend fun signIn(email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signIn(email, password)
                    ?: throw Exception("Invalid email or password.")

                userRepository.createAndSaveLocalUser(firebaseUser)
            }
        }

    suspend fun signUp(name: String, email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signUp(name, email, password)
                    ?: throw Exception("Sign-up failed. Please try again.")

                userRepository.createAndSaveLocalUser(firebaseUser)
            }
        }

    /**
     * ✅ Logs out the user, clears session and local cache.
     */
    suspend fun logout(context: Context, onSignOutComplete: () -> Unit) = withContext(Dispatchers.IO) {
        authService.signOut(context, onSignOutComplete) // Pass context & callback
        sessionManager.logout()
        userDao.clearUser()
    }

    suspend fun signInWithGoogle(idToken: String): Result<LocalUser> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.firebaseAuthWithGoogle(idToken)
                    ?: throw Exception("Google sign-in failed.")

                userRepository.createAndSaveLocalUser(firebaseUser)
            }
        }
    }
}