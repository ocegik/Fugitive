package com.example.fugitive.data.repository

import android.content.Context
import com.example.fugitive.data.local.preferences.AuthPreferences
import com.example.fugitive.data.local.database.LocalUser
import com.example.fugitive.data.local.database.UserDao
import com.example.fugitive.data.remote.firebase.FirebaseAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val authService: FirebaseAuthService,  // ✅ Firebase Authentication
    private val userRepository: UserRepository,
    private val authPreferences: AuthPreferences
) {
    suspend fun signIn(email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signIn(email, password)
                    ?: throw Exception("Invalid email or password.")

                val localUser = userRepository.initializeNewUser(firebaseUser)
                authPreferences.setLoggedIn(localUser.uid)
                localUser
            }
        }

    suspend fun signUp(name: String, email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signUp(name, email, password)
                    ?: throw Exception("Sign-up failed. Please try again.")

                val localUser = userRepository.initializeNewUser(firebaseUser)

                // ✅ Save session state
                authPreferences.setLoggedIn(localUser.uid)

                localUser
            }
        }

    /**
     * ✅ Logs out the user, clears session and local cache.
     */
    suspend fun logout(context: Context, onSignOutComplete: () -> Unit) = withContext(Dispatchers.IO) {
        authService.signOut(context, onSignOutComplete) // Pass context & callback
        authPreferences.clearLoginState()
        userDao.clearUser()
    }

    suspend fun signInWithGoogle(idToken: String): Result<LocalUser> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.firebaseAuthWithGoogle(idToken)
                    ?: throw Exception("Google sign-in failed.")

                val localUser = userRepository.initializeNewUser(firebaseUser)

                // ✅ Save session state
                authPreferences.setLoggedIn(localUser.uid)

                localUser
            }
        }
    }
}