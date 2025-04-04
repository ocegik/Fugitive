package com.example.fugitive.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.remote.UserMetadata
import com.example.fugitive.data.repository.UserRepository
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = mutableStateOf<UserMetadata?>(null)
    val user: State<UserMetadata?> get() = _user

    init {
        viewModelScope.launch {
            _user.value = null
            userRepository.getUserFlow().collect { localUser ->
                _user.value = localUser
            }
        }
    }

    fun fetchUserDetails(uid: String) {
        if (_user.value != null) return
        viewModelScope.launch {
            val result = userRepository.getUserData(uid)  // 🔥 Fetch from Room or Firestore
            result.onSuccess { userMetadata ->
                _user.value = userMetadata  // ✅ Update UI immediately
                Log.d("UserViewModel", "User data updated: ${userMetadata.name}")
            }.onFailure {
                Log.e("UserViewModel", "Failed to fetch user data: ${it.message}")
            }
        }
    }

    // 🔹 Update both Name & Profile Picture (Firestore)
    fun updateUserData(uid: String, name: String? = null, profilePic: String? = null) {
        Log.d("UserViewModel", "Updating user data: uid=$uid, Name=$name, PFP=$profilePic")
        viewModelScope.launch {
            userRepository.updateUserData(uid, name, profilePic)

            // ✅ Update UI immediately for smoother UX
            userRepository.getUserData(uid).onSuccess { userMetadata ->
                _user.value = userMetadata
                Log.d("UserViewModel", "User data updated successfully in UI")
            }
        }
    }
}