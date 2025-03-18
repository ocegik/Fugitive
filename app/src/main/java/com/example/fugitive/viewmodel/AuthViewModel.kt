package com.example.fugitive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {

    private val authService: FirebaseAuthService by inject()

    private val _authState = MutableStateFlow<FirebaseUser?>(authService.getCurrentUser())
    val authState: StateFlow<FirebaseUser?> = _authState

    fun signUp(name: String, email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authService.signUp(name, email, password)
                if (user!= null) {
                    _authState.value = user
                    onSuccess()
                } else {
                    onError("User already exists or invalid email.")
                }
            }catch (e: Exception) {
                    onError(e.message ?: "Sign-up failed. Please try again.")
                }
            }
        }


    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authService.signIn(email, password)
                if (user != null) {
                    _authState.value = user
                    onSuccess() // Call success callback
                } else {
                    onError("Invalid email or password.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Login failed. Please try again.")
            }
        }
    }

    fun signOut() {
        authService.signOut()
        _authState.value = null
    }
}