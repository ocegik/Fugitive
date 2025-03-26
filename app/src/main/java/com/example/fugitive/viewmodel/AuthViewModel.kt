package com.example.fugitive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.local.CachedUser
import com.example.fugitive.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel( private val userRepository: UserRepository) : ViewModel(){

    private val _authState = MutableStateFlow<CachedUser?>(null)
    val authState: StateFlow<CachedUser?> = _authState
    init{
        viewModelScope.launch {
            _authState.value = userRepository.getCurrentUser()
        }
    }

    fun signUp(name: String, email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.signUp(name, email, password)
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
                val user = userRepository.signIn(email, password)
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
        viewModelScope.launch {
            userRepository.logout()  // ✅ Use repository instead of authService
            _authState.value = null
        }
    }
}