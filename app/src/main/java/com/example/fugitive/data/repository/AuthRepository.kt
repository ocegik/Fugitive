package com.example.fugitive.data.repository

import com.example.fugitive.data.local.database.LocalUser
import com.example.fugitive.data.local.database.UserDao
import com.example.fugitive.data.local.preferences.AuthPreferences
import com.example.fugitive.data.remote.firebase.FirebaseAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val userDao: UserDao,
    private val authService: FirebaseAuthService,
    private val userRepository: UserRepository,
    private val authPreferences: AuthPreferences
) {

    suspend fun signIn(email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signIn(email, password)
                    ?: throw Exception("Invalid email or password.")
                val localUser = userRepository.initializeNewUser(firebaseUser)
                authPreferences.setLoggedIn(localUser.uid) // single source of truth — set here only
                localUser
            }
        }

    suspend fun signUp(name: String, email: String, password: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signUp(name, email, password)
                    ?: throw Exception("Sign-up failed. Please try again.")
                val localUser = userRepository.initializeNewUser(firebaseUser)
                authPreferences.setLoggedIn(localUser.uid) // single source of truth — set here only
                localUser
            }
        }

    suspend fun logout(onSignOutComplete: () -> Unit) = withContext(Dispatchers.IO) {
        // Clear local state first, then sign out of Firebase
        // No race condition — all sequential on IO dispatcher
        authPreferences.clearLoginState()
        userDao.clearUser()
        authService.signOut() // fire-and-forget, no callback needed
        withContext(Dispatchers.Main) {
            onSignOutComplete() // callback runs on Main so nav/UI updates are safe
        }
    }

    suspend fun signInWithGoogle(idToken: String): Result<LocalUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.firebaseAuthWithGoogle(idToken)
                    ?: throw Exception("Google sign-in failed.")
                val localUser = userRepository.initializeNewUser(firebaseUser)
                authPreferences.setLoggedIn(localUser.uid) // single source of truth — set here only
                localUser
            }
        }
}