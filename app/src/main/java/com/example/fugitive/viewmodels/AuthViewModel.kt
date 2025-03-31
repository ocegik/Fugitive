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
                userRepository.signUp(name, email, password)
                    .onSuccess { user ->
                        _authState.value = user
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
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            errorMessage = validateInputs(checkName = false)
            if (errorMessage.isNotEmpty()) return

            isLoading = true
            viewModelScope.launch {
                userRepository.signIn(email, password)
                    .onSuccess { user ->
                        _authState.value = user
                        onSuccess()
                    }
                    .onFailure { onError(it.message ?: "Login failed. Please try again.") }
                isLoading = false
            }
        }

        fun signOut() {
            viewModelScope.launch {
                userRepository.logout()
                _authState.value = null
            }
        }

        fun signInWithGoogle(
            idToken: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                userRepository.signInWithGoogle(idToken)
                    .onSuccess { user ->
                        _authState.value = user
                        onSuccess()
                    }
                    .onFailure { e ->
                        onError(e.message ?: "Google sign-in failed.")
                    }
            }
        }
        /*

    fun signInWithFacebook(
        accessToken: AccessToken,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            userRepository.signInWithFacebook(accessToken)
                .onSuccess {
                    _authState.value = it
                    onSuccess()
                }
                .onFailure { e ->
                    onError(e.message ?: "Facebook sign-in failed.")
                }
        }
    }

    fun signInWithX(
        accessToken: AccessToken,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            userRepository.signInWithX(accessToken)
                .onSuccess {
                    _authState.value = it
                    onSuccess()
                }
                .onFailure { e ->
                    onError(e.message ?: "X sign-in failed.")
                }
        }
    }
     */


}
