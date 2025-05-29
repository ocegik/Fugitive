package com.example.fugitive.data.repository

import android.util.Log
import com.example.fugitive.data.local.preferences.AuthPreferences
import com.example.fugitive.data.local.database.LocalUser
import com.example.fugitive.data.local.database.UserDao
import com.example.fugitive.data.remote.firebase.FirestoreService
import com.example.fugitive.data.remote.firebase.UserMetadata
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val firestoreService: FirestoreService,  // ✅ Firebase Firestore
    private val authPreferences: AuthPreferences,  // ✅ Shared Preferences
) {

    private fun FirebaseUser.toLocalUser(nameOverride: String? = null, profilePic: String? = null): LocalUser {
        return LocalUser(
            uid = uid,
            name = nameOverride ?: displayName ?: "Unknown",
            email = email ?: "no_email",
            profilePicture = profilePic ?: ""
        )
    }

    private suspend fun saveUserData(user: LocalUser) = withContext(Dispatchers.IO) {
        val existingUser = userDao.getUser(user.uid)
        if (existingUser == null) {
            userDao.saveUser(user)
            authPreferences.setLoggedIn(user.uid)
        }
    }

    fun getUserFlow(): Flow<UserMetadata?> = flow {
        val uid = authPreferences.getUserId() ?: return@flow

        userDao.getUserFlow(uid).collect { localUser ->
            if (localUser != null) {
                emit(
                    UserMetadata(
                        localUser.uid,
                        localUser.name,
                        localUser.email,
                        localUser.profilePicture
                    )
                )
            } else {
                val firestoreResult =
                    runCatching { withContext(Dispatchers.IO) { getUserData(uid).getOrThrow() } }
                firestoreResult.onSuccess { userData ->
                    userDao.saveUser(
                        LocalUser(
                            userData.uid,
                            userData.name,
                            userData.email,
                            userData.profilePicture
                        )
                    )
                    emit(userData)
                }.onFailure {
                    Log.e("UserRepository", "Failed to fetch user from Firestore: ${it.message}")
                    emit(null)
                }
            }
        }
    }

    suspend fun initializeNewUser(
        firebaseUser: FirebaseUser,
        name: String? = null,
        profilePic: String? = null
    ): LocalUser {
        return firebaseUser.toLocalUser(name, profilePic).also { saveUserData(it) }
    }


    suspend fun getUserData(uid: String): Result<UserMetadata> = withContext(Dispatchers.IO) {
        val localUser = userDao.getUser(uid)
        if (localUser != null) {
            Log.d("UserRepository", "Returning user from local DB: ${localUser.name}")
            return@withContext Result.success(
                UserMetadata(
                    uid = localUser.uid,
                    name = localUser.name,
                    email = localUser.email,
                    profilePicture = localUser.profilePicture
                )
            )
        }
        // Fetch from Firestore if local DB is empty
        val firestoreResult = firestoreService.getUserData(uid)
        firestoreResult.onSuccess { userMetadata ->
            Log.d("UserRepository", "Fetched from Firestore: ${userMetadata.name}")
            val updatedUser = LocalUser(
                uid = userMetadata.uid,
                name = userMetadata.name,
                email = userMetadata.email,
                profilePicture = userMetadata.profilePicture
            )
            userDao.saveUser(updatedUser)  // ✅ Keep local database updated
        }

        return@withContext firestoreResult // ✅ Return Firestore result correctly
    }


    // Update the user metadata object directly
    suspend fun updateUserData(uid: String, name: String? = null, profilePic: String? = null) {
        withContext(Dispatchers.IO) {
            val existingUser = userDao.getUser(uid) ?: return@withContext

            // 🔥 Only update the changed fields
            val updatedUser = LocalUser(
                uid = uid,
                name = name ?: existingUser.name,
                email = existingUser.email,
                profilePicture = profilePic ?: existingUser.profilePicture
            )
            userDao.updateUser(uid, updatedUser.name, updatedUser.profilePicture)

            val firestoreResult = runCatching {
                firestoreService.updateUserData(
                    uid,
                    UserMetadata(
                        updatedUser.uid,
                        updatedUser.name,
                        updatedUser.email,
                        updatedUser.profilePicture
                    )
                )
            }

            if (firestoreResult.isFailure) {
                Log.e("UserRepository", "Firestore update failed: ${firestoreResult.exceptionOrNull()?.message}")
            }
        }
    }
    fun saveReadingProgress(userId: String, bookId: String, chapter: Int, scroll: Int) {
        firestoreService.saveReadingProgress(userId, bookId, chapter, scroll)
    }

    suspend fun getReadingProgress(userId: String, bookId: String): Result<Pair<Int, Int>> {
        return firestoreService.getReadingProgress(userId, bookId)
    }

}
