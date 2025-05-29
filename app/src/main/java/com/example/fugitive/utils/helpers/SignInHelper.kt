package com.example.fugitive.utils.helpers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.lifecycleScope
import com.example.fugitive.R
import com.example.fugitive.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch


fun ComponentActivity.startGoogleSignIn(
    launcher: ActivityResultLauncher<android.content.Intent>,
    authViewModel: AuthViewModel
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34+
        val credentialManager = CredentialManager.create(this)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential( this@startGoogleSignIn, request)
                handleGoogleSignInResult(result, authViewModel)
            } catch (e: Exception) {
                Log.e("Auth", "Google Sign-In failed: ${e.message}")
            }
        }
    } else {
        // Fallback for older Android versions (API < 34)
        val googleSignInClient = getGoogleSignInClient(this)
        launcher.launch(googleSignInClient.signInIntent)
    }
}

fun handleGoogleSignInResult(
    result: Any, // Can be either GetCredentialResponse (API 34+) or Intent (older versions)
    authViewModel: AuthViewModel
) {
    when (result) {
        is GetCredentialResponse -> { // API 34+
            val credential = result.credential
            if (credential is GoogleIdTokenCredential) {
                val idToken = credential.idToken
                authViewModel.signInWithGoogle(
                    idToken = idToken,
                    onSuccess = { Log.d("Auth", "Google Sign-In Successful") },
                    onError = { errorMessage -> Log.e("Auth", "Google Sign-In Error: $errorMessage") }
                )
            } else {
                Log.e("Auth", "Google Sign-In failed: No valid credential found")
            }
        }

        is android.content.Intent -> { // API < 34
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result)
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

        else -> Log.e("Auth", "Google Sign-In failed: Unsupported result type")
    }
}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

