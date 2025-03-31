package com.example.fugitive.repository

import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.local.session.UserSessionManager
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val firestoreService: FirestoreService,  // ✅ Firebase Firestore
    private val authService: FirebaseAuthService,  // ✅ Firebase Authentication
    private val sessionManager: UserSessionManager
) {
    private fun FirebaseUser.toCachedUser(nameOverride: String? = null): CachedUser {
        return CachedUser(
            userId = uid,
            name = nameOverride ?: displayName ?: "Unknown",
            email = email ?: "",
            profilePicUrl = photoUrl?.toString()
        )
    }

    suspend fun getCurrentUser(): CachedUser? = withContext(Dispatchers.IO) {
        authService.getCurrentUser()?.toCachedUser()
            ?: sessionManager.getUserId()?.let { userDao.getUser(it) }
    }

    suspend fun signIn(email: String, password: String): Result<CachedUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signIn(email, password)
                    ?: throw Exception("Invalid email or password.")

                firebaseUser.toCachedUser().also {
                    userDao.saveUser(it)
                    sessionManager.saveUser(firebaseUser)
                }
            }
        }

    suspend fun signUp(name: String, email: String, password: String): Result<CachedUser> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.signUp(name, email, password)
                    ?: throw Exception("Sign-up failed. Please try again.")

                firebaseUser.toCachedUser(name).also {
                    userDao.saveUser(it)
                    sessionManager.saveUser(firebaseUser)
                }
            }
        }

    /**
     * ✅ Logs out the user, clears session and local cache.
     */
    suspend fun logout() = withContext(Dispatchers.IO) {
        authService.signOut()
        sessionManager.logout()
        userDao.clearUser()
    }

    suspend fun signInWithGoogle(idToken: String): Result<CachedUser> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authService.firebaseAuthWithGoogle(idToken)
                    ?: throw Exception("Google sign-in failed.")

                firebaseUser.toCachedUser().also {
                    userDao.saveUser(it)
                    sessionManager.saveUser(firebaseUser)
                }
            }
        }
    }
        /*

    suspend fun signInWithFacebook(accessToken: AccessToken): Result<FirebaseUser> {
        return try {
            val user = firebaseAuthService.firebaseAuthWithFacebook(accessToken)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithX(accessToken: AccessToken): Result<FirebaseUser> {
        return try {
            val user = firebaseAuthService.firebaseAuthWithX(accessToken)
            Result.success(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
     */


}