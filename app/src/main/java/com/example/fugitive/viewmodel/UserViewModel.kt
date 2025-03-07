package com.example.fugitive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val bookmarks: StateFlow<List<String>> = userRepository.bookmarks

    init {
        viewModelScope.launch {
            userRepository.fetchBookmarks()
        }
    }

    fun addBookmark(bookId: String) {
        viewModelScope.launch {
            userRepository.addBookmark(bookId)
        }
    }

    fun removeBookmark(bookId: String) {
        viewModelScope.launch {
            userRepository.removeBookmark(bookId)
        }
    }
}