package com.example.fugitive.utils.helpers

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.fugitive.R
import com.example.fugitive.viewmodels.AuthViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import androidx.navigation.NavController

fun ComponentActivity.startGoogleSignIn(authViewModel: AuthViewModel, navController: NavController) {
    val credentialManager = CredentialManager.create(this)

    // First try: silently return already-authorized accounts (no UI if account exists)
    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(getString(R.string.default_web_client_id))
        .setFilterByAuthorizedAccounts(true)   // silent re-auth for returning users
        .setAutoSelectEnabled(true)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    lifecycleScope.launch {
        try {
            val result = credentialManager.getCredential(this@startGoogleSignIn, request)
            handleCredentialResponse(result, authViewModel, navController)
        } catch (e: NoCredentialException) {
            // No saved account found — fall through to full account picker
            startGoogleSignInFull(credentialManager, authViewModel, navController)
        } catch (e: GetCredentialCancellationException) {
            Log.d("Auth", "Sign-in cancelled by user")
        } catch (e: GetCredentialException) {
            Log.e("Auth", "Credential error: ${e.message}")
        }
    }
}

// Full account picker — shown to new users or when no saved account exists
private fun ComponentActivity.startGoogleSignInFull(
    credentialManager: CredentialManager,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val signInWithGoogleOption = GetSignInWithGoogleOption
        .Builder(getString(R.string.default_web_client_id))
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    lifecycleScope.launch {
        try {
            val result = credentialManager.getCredential(this@startGoogleSignInFull, request)
            handleCredentialResponse(result, authViewModel, navController)
        } catch (e: GetCredentialCancellationException) {
            Log.d("Auth", "Sign-in cancelled by user")
        } catch (e: GetCredentialException) {
            Log.e("Auth", "Sign-in failed: ${e.message}")
        }
    }
}

private fun handleCredentialResponse(
    result: GetCredentialResponse,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    try {
        val credential = result.credential

        val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)

        authViewModel.signInWithGoogle(
            idToken = googleIdTokenCredential.idToken,
            navController = navController
        )

    } catch (e: Exception) {
        Log.e("Auth", "Credential parsing failed: ${e.message}")
    }
}