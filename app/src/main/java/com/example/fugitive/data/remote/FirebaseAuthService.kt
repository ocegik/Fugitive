package com.example.fugitive.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun signUp(name: String, email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                // Update display name in Firebase Auth
                user.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                ).await()

                // Create user document in Firestore (Single Collection Approach)
                try {
                    // Create Firestore user document
                    val userMap = mapOf(
                        "uid" to user.uid,
                        "name" to name,
                        "email" to email,
                        "createdAt" to System.currentTimeMillis(),
                        "profilePicture" to null,
                        "bio" to null,
                        "phoneNumber" to null
                    )

                    firestore.collection("users").document(user.uid).set(userMap).await()
                } catch (e: Exception) {
                    // If Firestore fails, print error but continue
                    println("Firestore Error: ${e.message}")
                }
            }

            user // Return user object
        } catch (e: Exception) {
            throw when {
                "The email address is already in use" in e.message.toString() -> Exception("Email already in use.")
                "The email address is badly formatted" in e.message.toString() -> Exception("Invalid email format.")
                "Password should be at least 6 characters" in e.message.toString() -> Exception("Password must be at least 6 characters long.")
                else -> Exception("Registration failed. Please try again.")
            }
        }
    }


    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            throw when {
                "There is no user record" in e.message.toString() -> Exception("Account does not exist. Please sign up.")
                "The password is invalid" in e.message.toString() -> Exception("Incorrect password. Please try again.")
                "The email address is badly formatted" in e.message.toString() -> Exception("Invalid email format.")
                else -> Exception("Login failed. Please check your credentials.")
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}