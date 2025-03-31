package com.example.fugitive.utils

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.BuildConfig


fun startGoogleSignIn(
    context: Context,
    launcher: ActivityResultLauncher<android.content.Intent>,
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.WEB_CLIENT_ID) // ⚠️ Replace with your actual Web Client ID
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
    launcher.launch(googleSignInClient.signInIntent)
}

fun handleGoogleSignInResult(
    resultData: android.content.Intent?,
    authViewModel: AuthViewModel
) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(resultData)
    try {
        val account = task.getResult(ApiException::class.java)
        val idToken = account?.idToken ?: throw Exception("No ID Token found")

        authViewModel.signInWithGoogle(
            idToken = idToken,
            onSuccess = { Log.d("Auth", "Google Sign-In Successful") },
            onError = { errorMessage -> Log.e("Auth", "Google Sign-In Error: $errorMessage") }
        )
    } catch (e: Exception) {
        Log.e("Auth", "Google Sign-In failed: ${e.message}")
    }
}
