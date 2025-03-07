package com.example.fugitive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fugitive.repository.UserRepository
import com.example.fugitive.viewmodel.UserViewModel

class ViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}