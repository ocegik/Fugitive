package com.example.fugitive.data.repository

import android.util.Log
import com.example.fugitive.data.local.AuthPreferences
import com.example.fugitive.data.local.LocalUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.remote.UserMetadata
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

    suspend fun getUserData(uid: String): Result<UserMetadata> {
        return firestoreService.getUserData(uid)  // Assuming you want to fetch user data from Firestore
    }


    suspend fun saveUserData(user: LocalUser) = withContext(Dispatchers.IO) {
        val existingUser = userDao.getUser(user.uid)
        if (existingUser == null) {
            userDao.saveUser(user)
            authPreferences.setLoggedIn(user.uid)
        }
    }

    private suspend fun syncData(localUser: LocalUser?, firestoreUser: UserMetadata?) {
        if (localUser?.uid != firestoreUser?.uid || localUser?.name != firestoreUser?.name || localUser?.profilePicture != firestoreUser?.profilePicture) {
            firestoreUser?.let { firestoreService.updateUserData(localUser!!.uid, localUser.toUserMetadata()) }
            localUser?.let { userDao.saveUser(it) }
        }
    }

    fun getUserFlow(): Flow<UserMetadata?> = flow {
        val uid = authPreferences.getUserId() ?: return@flow

        val firestoreResult: Result<UserMetadata>?

        // First, try to fetch user from local database
        val localUser = userDao.getUser(uid)
        if (localUser != null) {
            emit(localUser.toUserMetadata())
            firestoreResult = null
        } else {
            // If no local data, fetch from Firestore
            firestoreResult = runCatching { getUserData(uid).getOrThrow() }
            firestoreResult.onSuccess { userData ->
                userDao.saveUser(userData.toLocalUser()) // Update local DB
                emit(userData) // Emit to flow
            }.onFailure {
                Log.e("UserRepository", "Failed to fetch user from Firestore: ${it.message}")
                emit(null)
            }
        }

        // Ensure sync between local and Firestore
        syncData(localUser, firestoreResult?.getOrNull())
    }

    suspend fun initializeNewUser(
        firebaseUser: FirebaseUser,
        name: String? = null,
        profilePic: String? = null
    ): LocalUser { // <-- Return LocalUser
        val localUser = firebaseUser.toLocalUser(name, profilePic)
        userDao.saveUser(localUser) // Save to local DB
        authPreferences.setLoggedIn(localUser.uid) // Save user session
        return localUser // <-- Return LocalUser
    }


    suspend fun updateUserData(uid: String, name: String? = null, profilePic: String? = null) {
        val existingUser = userDao.getUser(uid) ?: return

        val updatedUser = existingUser.copy(
            name = name ?: existingUser.name,
            profilePicture = profilePic ?: existingUser.profilePicture
        )

        userDao.updateUser(uid, updatedUser.name, updatedUser.profilePicture)

        // Firestore update, no waiting on result
        runCatching {
            firestoreService.updateUserData(
                uid,
                updatedUser.toUserMetadata()
            )
        }.onFailure {
            Log.e("UserRepository", "Firestore update failed: ${it.message}")
        }
    }

    fun saveReadingProgress(userId: String, bookId: String, chapter: Int, scroll: Int) {
        firestoreService.saveReadingProgress(userId, bookId, chapter, scroll)
    }

    suspend fun getReadingProgress(userId: String, bookId: String): Result<Pair<Int, Int>> {
        return firestoreService.getReadingProgress(userId, bookId)
    }

    private fun LocalUser.toUserMetadata() = UserMetadata(uid, name, email, profilePicture)
    private fun UserMetadata.toLocalUser() = LocalUser(uid, name, email, profilePicture)

}
