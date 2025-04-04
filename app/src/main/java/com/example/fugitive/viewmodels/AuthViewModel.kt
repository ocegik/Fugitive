package com.example.fugitive.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fugitive.data.local.AuthPreferences
import com.example.fugitive.navigation.Screen
import com.example.fugitive.data.local.LocalUser
import com.example.fugitive.data.repository.AuthRepository
import com.example.fugitive.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel(private val authRepository: AuthRepository,
                    private val userRepository: UserRepository,
                    private val authPreferences: AuthPreferences, ) : ViewModel()
    {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)
    var termsCond by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private val _authState = MutableStateFlow<LocalUser?>(null)

        init {
            viewModelScope.launch {
                val userId = authPreferences.getUserId()
                if (userId != null) {
                    userRepository.getUserData(userId).onSuccess {
                        _authState.value = LocalUser(it.uid, it.name, it.email, it.profilePicture)
                    }.onFailure {
                        authPreferences.clearLoginState() // 🔥 Clear if invalid
                    }
                }
            }
        }

    private fun validateInputs(checkName: Boolean = true): String {
        return when {
            checkName && name.isBlank() -> "Name cannot be empty."
            email.isBlank() -> "Email cannot be empty."
            password.isBlank() -> "Password cannot be empty."
            password.length < 6 -> "Password must be at least 6 characters long."
            checkName && !termsCond -> "You must agree to the Terms & Conditions."
            else -> ""
        }
    }

        fun signUp(
            navController: NavController,
            context: Context
        ) {
            errorMessage = validateInputs()
            if (errorMessage.isNotEmpty()) return

            isLoading = true
            viewModelScope.launch {
                authRepository.signUp(name, email, password)
                    .onSuccess { user ->
                        _authState.value = user
                        authPreferences.setLoggedIn(user.uid)
                        Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.OnBoardingIntro.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                    .onFailure { errorMessage = it.message ?: "Sign-up failed. Please try again." }
                isLoading = false
            }
        }

        fun signIn(
            navController: NavController,
            context: Context
        ) {
            errorMessage = validateInputs(checkName = false)
            if (errorMessage.isNotEmpty()) return

            isLoading = true
            viewModelScope.launch {
                authRepository.signIn(email, password)
                    .onSuccess { user ->
                        _authState.value = user
                        authPreferences.setLoggedIn(user.uid)
                        Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.OnBoardingIntro.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                    .onFailure {errorMessage = it.message ?: "Login failed. Please try again." }
                isLoading = false
            }
        }

    fun signOut(context: Context) {
        viewModelScope.launch {
            authRepository.logout(context) {
                _authState.value = null // Clear user state after logout
                authPreferences.clearLoginState()
            }
        }
    }

    fun signInWithGoogle(
            idToken: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                authRepository.signInWithGoogle(idToken)
                    .onSuccess { user ->
                        _authState.value = user
                        authPreferences.setLoggedIn(user.uid)
                        onSuccess()
                    }
                    .onFailure { e ->
                        onError(e.message ?: "Google sign-in failed.")
                    }
            }
        }
}
