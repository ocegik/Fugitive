package com.example.fugitive.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.models.BookDetails
import com.example.fugitive.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _bookDetails = MutableLiveData<BookDetails?>()
    val bookDetails: LiveData<BookDetails?> get() = _bookDetails

    private val _books = MutableLiveData<List<BookDetails>>()  // ✅ Added this
    val books: LiveData<List<BookDetails>> get() = _books      // ✅ Exposed LiveData

    fun loadBookData(bookId: String) {
        _bookDetails.value = null

        viewModelScope.launch {
            try {
                val result = bookRepository.getBookDetails(bookId) // ✅ Fetch Result<BookMetadata>

                result.fold(
                    onSuccess = { book ->
                        _bookDetails.postValue(book)
                    },
                    onFailure = { exception ->  // ✅ Handle failure case
                        println("Error fetching book details: ${exception.message}")
                        _bookDetails.postValue(null)
                    }
                )
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                _bookDetails.postValue(null)
            }
        }
    }
    fun loadMultipleBooks(bookIds: List<String>) {
        _books.value = emptyList()  // Clear previous books

        viewModelScope.launch {
            val fetchedBooks = mutableListOf<BookDetails>()
            bookIds.forEach { bookId ->
                val result = bookRepository.getBookDetails(bookId)
                result.fold(
                    onSuccess = { book -> fetchedBooks.add(book) },
                    onFailure = { Log.e("BookViewModel", "Error fetching book: $bookId") }
                )
            }
            _books.postValue(fetchedBooks)  // ✅ Update the UI with multiple books
        }
    }
}

