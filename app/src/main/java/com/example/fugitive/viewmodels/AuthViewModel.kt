package com.example.fugitive.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fugitive.navigation.Screen
import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)
    var termsCond by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private val _authState = MutableStateFlow<CachedUser?>(null)
    val authState: StateFlow<CachedUser?> = _authState

    init {
        viewModelScope.launch {
            _authState.value = userRepository.getCurrentUser()
        }
    }

    fun signUp(
        navController: NavController,
        context: Context
    ) {
        errorMessage = "" // Reset error

        when {
            name.isBlank() -> errorMessage = "Name cannot be empty."
            email.isBlank() -> errorMessage = "Email cannot be empty."
            password.isBlank() -> errorMessage = "Password cannot be empty."
            password.length < 6 -> errorMessage = "Password must be at least 6 characters long."
            !termsCond -> errorMessage = "You must agree to the Terms & Conditions."
        }
        if (errorMessage.isNotEmpty()) return

        isLoading = true

        viewModelScope.launch {
            userRepository.signUp(name, email, password)
                .onSuccess { user ->
                    _authState.value = user
                    Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.OnBoardingIntro.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                .onFailure { e ->
                    errorMessage = e.message ?: "Sign-up failed. Please try again."
                }
            isLoading = false
        }
    }

    fun signIn(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isLoading = true
        errorMessage = ""

        viewModelScope.launch {
            userRepository.signIn(email, password)
                .onSuccess { user ->
                    _authState.value = user
                    onSuccess()
                }
                .onFailure { e ->
                    onError(e.message ?: "Login failed. Please try again.")
                }
            isLoading = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.logout()
            _authState.value = null
        }
    }
}