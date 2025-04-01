package com.example.fugitive.repository

import com.example.fugitive.data.local.LocalUser
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.local.session.UserSessionManager
import com.example.fugitive.data.remote.UserMetadata
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,  // ✅ Local Database
    private val firestoreService: FirestoreService,  // ✅ Firebase Firestore
    private val authService: FirebaseAuthService,  // ✅ Firebase Authentication
    private val sessionManager: UserSessionManager,
) {


    private fun FirebaseUser.toLocalUser(nameOverride: String? = null): LocalUser {
        return LocalUser(
            userId = uid,
            name = nameOverride ?: displayName ?: "Unknown",
            email = email ?: "no_email",
            profilePicture = "lion"
        )
    }

    private suspend fun saveUserData(user: LocalUser) = withContext(Dispatchers.IO) {
        userDao.saveUser(user)  // ✅ Saves user in Room DB
        sessionManager.setUserId(user.userId)  // ✅ Updates session
    }

    suspend fun getLocalUser(): LocalUser? = withContext(Dispatchers.IO) {
        sessionManager.getUserId()?.let { userDao.getUser(it) }
    }

    suspend fun createAndSaveLocalUser(
        firebaseUser: FirebaseUser,
        name: String? = null
    ): LocalUser {
        val localUser = firebaseUser.toLocalUser(name)
        saveUserData(localUser)
        return localUser
    }

    suspend fun getCurrentUser(): LocalUser? = withContext(Dispatchers.IO) {
        val firebaseUser = authService.getCurrentUser()
        if (firebaseUser != null) {
            return@withContext firebaseUser.toLocalUser()
        }

        // Check local database
        val userId = sessionManager.getUserId()
        if (userId != null) {
            return@withContext userDao.getUser(userId)
        }

        return@withContext null
    }


    suspend fun getUserData(userId: String): Result<UserMetadata> = withContext(Dispatchers.IO) {
        val localUser = userDao.getUser(userId)
        if (localUser != null) {
            return@withContext Result.success(
                UserMetadata(
                    uid = localUser.userId,
                    name = localUser.name,
                    email = localUser.email,
                    profilePicture = localUser.profilePicture
                )
            )
        }

        // Fetch from Firestore if local DB is empty
        return@withContext firestoreService.getUserData(userId).onSuccess { userMetadata ->
            val updatedUser = LocalUser(
                userId = userMetadata.uid,
                name = userMetadata.name,
                email = userMetadata.email,
                profilePicture = userMetadata.profilePicture
            )
            userDao.saveUser(updatedUser)  // ✅ Keep local database updated
        }
    }


    // Update the user metadata object directly
    suspend fun updateUserData(userId: String, name: String? = null, profilePic: String? = null) {
        withContext(Dispatchers.IO) {
            val existingUser = userDao.getUser(userId) ?: return@withContext

            // 🔥 Only update the changed fields
            val updatedUser = LocalUser(
                userId = userId,
                name = name ?: existingUser.name,
                email = existingUser.email,
                profilePicture = profilePic ?: existingUser.profilePicture
            )

            userDao.updateUser(
                userId,
                updatedUser.name,
                updatedUser.profilePicture
            )  // ✅ Update local DB
            firestoreService.updateUserData(
                userId,
                UserMetadata(
                    updatedUser.userId,
                    updatedUser.name,
                    updatedUser.email,
                    updatedUser.profilePicture
                )
            )  // ✅ Update Firestore
        }
    }
}
