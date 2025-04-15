package com.example.fugitive.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.models.BookDetails
import com.example.fugitive.data.models.BookTextFetcher
import com.example.fugitive.data.remote.Chapter
import com.example.fugitive.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _bookDetails = MutableLiveData<BookDetails?>()
    val bookDetails: LiveData<BookDetails?> get() = _bookDetails

    private val _books = MutableLiveData<List<BookDetails>>()  // ✅ Added this
    val books: LiveData<List<BookDetails>> get() = _books      // ✅ Exposed LiveData

    private val _bookChapters = MutableLiveData<List<Chapter>>()
    val bookChapters: LiveData<List<Chapter>> get() = _bookChapters

    private val _selectedChapterText = MutableLiveData<String?>()
    val selectedChapterText: LiveData<String?> get() = _selectedChapterText


    fun loadBookData(bookId: String) {
        _bookDetails.value = null

        viewModelScope.launch {
            try {
                val result = bookRepository.getBookDetails(bookId) // ✅ Fetch Result<BookMetadata>

                result.fold(
                    onSuccess = { book ->
                        Log.d("📘 loadBookData", "Book details fetched: $book")
                        _bookDetails.postValue(book)
                    },
                    onFailure = { exception ->  // ✅ Handle failure case
                        Log.e(
                            "📘 loadBookData",
                            "Failed to fetch book details: ${exception.message}"
                        ) // ✅ ADD THIS
                        _bookDetails.postValue(null)
                    }
                )
            } catch (e: Exception) {
                Log.e("📘 loadBookData", "Unexpected error: ${e.message}")
                _bookDetails.postValue(null)
            }
        }
    }

    fun loadBookChapters(bookId: String) {
        _bookChapters.value = emptyList()
        viewModelScope.launch {
            val result = bookRepository.getBookChapters(bookId)
            result.fold(
                onSuccess = { chapters ->
                    Log.d("loadBookChapters", "Chapters loaded: ${chapters.size}")
                    _bookChapters.postValue(chapters)
                },
                onFailure = { exception ->
                    Log.e("loadBookChapters", "Failed to fetch chapters: ${exception.message}")
                    _bookChapters.postValue(emptyList())
                }
            )
        }
    }

    fun loadChapterText(url: String) {
        viewModelScope.launch {
            val result = BookTextFetcher.fetchBookText(url)
            result.fold(
                onSuccess = { text ->
                    _selectedChapterText.postValue(text)
                },
                onFailure = {
                    Log.e("BookViewModel", "❌ Failed to load chapter text: ${it.message}")
                    _selectedChapterText.postValue(null)
                }
            )
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

