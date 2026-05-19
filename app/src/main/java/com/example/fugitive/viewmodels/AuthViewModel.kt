package com.example.fugitive.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fugitive.data.local.database.LocalUser
import com.example.fugitive.data.local.preferences.AuthPreferences
import com.example.fugitive.data.repository.AuthRepository
import com.example.fugitive.data.repository.UserRepository
import com.example.fugitive.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val authPreferences: AuthPreferences,
) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)
    var termsCond by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private val _authState = MutableStateFlow<LocalUser?>(null)
    val authState: StateFlow<LocalUser?> = _authState.asStateFlow() // ← now publicly observable

    init {
        viewModelScope.launch {
            val userId = authPreferences.getUserId()
            if (userId != null) {
                userRepository.getUserData(userId)
                    .onSuccess { user ->
                        _authState.value = LocalUser(user.uid, user.name, user.email, user.profilePicture)
                    }
                    .onFailure {
                        // Stale session — clear it so NavGraph routes to Welcome
                        authPreferences.clearLoginState()
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

    fun signUp(navController: NavController, context: Context) {
        errorMessage = validateInputs()
        if (errorMessage.isNotEmpty()) return

        isLoading = true
        viewModelScope.launch {
            authRepository.signUp(name, email, password)
                .onSuccess { user ->
                    _authState.value = user
                    Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                    // New users → onboarding
                    navController.navigate(Screen.OnBoardingIntro.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                .onFailure { errorMessage = it.message ?: "Sign-up failed. Please try again." }
            isLoading = false
        }
    }

    fun signIn(navController: NavController, context: Context) {
        errorMessage = validateInputs(checkName = false)
        if (errorMessage.isNotEmpty()) return

        isLoading = true
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .onSuccess { user ->
                    _authState.value = user
                    Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show() // ← fixed toast
                    // Returning users → home, not onboarding
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                .onFailure { errorMessage = it.message ?: "Login failed. Please try again." }
            isLoading = false
        }
    }

    fun signOut(navController: NavController) {
        viewModelScope.launch {
            authRepository.logout {
                _authState.value = null
                // Navigate back to Welcome and clear the entire back stack
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    fun signInWithGoogle(
        idToken: String,
        navController: NavController,
        isNewUser: Boolean = false // pass true from sign-up flow if needed
    ) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(idToken)
                .onSuccess { user ->
                    _authState.value = user
                    // New Google users → onboarding; returning → home
                    val destination = if (isNewUser) Screen.OnBoardingIntro.route else Screen.Home.route
                    navController.navigate(destination) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                .onFailure { e ->
                    errorMessage = e.message ?: "Google sign-in failed."
                }
        }
    }
}