package com.example.fugitive.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.remote.UserMetadata
import com.example.fugitive.repository.UserRepository
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = mutableStateOf<UserMetadata?>(null)
    val user: State<UserMetadata?> get() = _user

    fun fetchUser(userId: String) {
        viewModelScope.launch {
            val userData = userRepository.getUserData(userId).getOrNull()
            _user.value = userData // ✅ Store in ViewModel (avoids re-fetching)
        }
    }

    // 🔹 Update both Name & Profile Picture (Firestore)
    fun updateUserData(userId: String, name: String? = null, profilePic: String? = null) {
        viewModelScope.launch {
            userRepository.updateUserData(userId, name, profilePic)

            // ✅ Update UI immediately for smoother UX
            _user.value = _user.value?.copy(
                name = name ?: _user.value?.name?: "",
                profilePicture = profilePic ?: _user.value?.profilePicture?: ""
            )
        }
    }
    suspend fun getUserId(): String? {
        return userRepository.getCurrentUser()?.userId
    }

    // 🔹 Get Safe Values for UI
    fun getUserName(): String = _user.value?.name ?: "Guest"
    fun getUserPfp(): String = _user.value?.profilePicture ?: "lion"
}