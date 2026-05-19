package com.example.fugitive.data.remote.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun signUp(name: String, email: String, password: String): FirebaseUser? = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        result.user?.apply {
            updateProfile(
                com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
            ).await()

            val userMap = mapOf(
                "uid" to uid,
                "name" to name,
                "email" to email,
                "createdAt" to System.currentTimeMillis(),
                "profilePicture" to null
            )
            firestore.collection("users").document(uid).set(userMap).await()
        }
    }.getOrElse { throw mapAuthException(it) }

    suspend fun signIn(email: String, password: String): FirebaseUser? = runCatching {
        auth.signInWithEmailAndPassword(email, password).await().user
    }.getOrElse { throw mapAuthException(it) }

    // No longer needs Context — CredentialManager sign-out is handled at the call site
    // via CredentialManager.clearCredentialState() if needed; Firebase sign-out is enough here
    fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            Log.e("Auth", "Sign-out failed: ${e.message}")
        }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun firebaseAuthWithGoogle(idToken: String): FirebaseUser? {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user

            user?.let {
                val userDoc = firestore.collection("users").document(it.uid).get().await()
                if (!userDoc.exists()) {
                    val userMap = mapOf(
                        "uid" to it.uid,
                        "name" to (it.displayName ?: "New User"),
                        "email" to (it.email ?: ""),
                        "createdAt" to System.currentTimeMillis()
                    )
                    firestore.collection("users").document(it.uid).set(userMap).await()
                }
            }

            user
        } catch (e: Exception) {
            throw Exception("Google sign-in failed: ${e.message}")
        }
    }

    private fun mapAuthException(e: Throwable): Exception {
        val errorMessages = mapOf(
            "ERROR_EMAIL_ALREADY_IN_USE" to "Email already in use.",
            "ERROR_INVALID_EMAIL" to "Invalid email format.",
            "ERROR_WEAK_PASSWORD" to "Password must be at least 6 characters long.",
            "There is no user record" to "Account does not exist. Please sign up.",
            "The password is invalid" to "Incorrect password. Please try again.",
            "The email address is badly formatted" to "Invalid email format."
        )
        return Exception(
            errorMessages.entries.find { e.message?.contains(it.key) == true }?.value
                ?: "An error occurred."
        )
    }
}