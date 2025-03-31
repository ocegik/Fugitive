package com.example.fugitive.data.remote

import android.content.Context
import android.content.Intent
import com.example.fugitive.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
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
    }.getOrElse { throw mapAuthException(it) } // 🔥 Improved error handling

    suspend fun signIn(email: String, password: String): FirebaseUser? = runCatching {
        auth.signInWithEmailAndPassword(email, password).await().user
    }.getOrElse { throw mapAuthException(it) }

    fun signOut() = auth.signOut()

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    private fun mapAuthException(e: Throwable): Exception {
        val errorMessages = mapOf(
            "ERROR_EMAIL_ALREADY_IN_USE" to "Email already in use.",
            "ERROR_INVALID_EMAIL" to "Invalid email format.",
            "ERROR_WEAK_PASSWORD" to "Password must be at least 6 characters long.",
            "There is no user record" to "Account does not exist. Please sign up.",
            "The password is invalid" to "Incorrect password. Please try again.",
            "The email address is badly formatted" to "Invalid email format."
        )

        return Exception(errorMessages.entries.find { e.message?.contains(it.key) == true }?.value ?: "An error occurred.")
    }



    fun getGoogleSignInIntent(context: Context): Intent {
        val googleSignInClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
        return googleSignInClient.signInIntent
    }

    suspend fun firebaseAuthWithGoogle(idToken: String): FirebaseUser? {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            result.user
        } catch (e: Exception) {
            throw Exception("Google sign-in failed.")
        }
    }
    /*

    suspend fun firebaseAuthWithFacebook(accessToken: AccessToken): FirebaseUser? {
        return try {
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            val result = auth.signInWithCredential(credential).await()
            result.user
        } catch (e: Exception) {
            throw Exception("Facebook sign-in failed.")
        }
    }


    suspend fun firebaseAuthWithX(accessToken: AccessToken): FirebaseUser? {
        return try {
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            val result = auth.signInWithCredential(credential).await()
            result.user
        } catch (e: Exception){
            throw Exception("X sign-in failed.")
        }
    }

     */

}