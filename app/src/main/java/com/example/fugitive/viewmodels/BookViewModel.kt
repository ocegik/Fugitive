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
import com.example.fugitive.utils.getShuffledBooks
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

    private val _bookIds = MutableLiveData<List<String>>()  // ✅ Added this
    val bookIds: LiveData<List<String>> get() = _bookIds    // ✅ Exposed LiveData

    private val _errorMessage = MutableLiveData<String>()  // ✅ Added this
    val errorMessage: LiveData<String> get() = _errorMessage  // ✅ Exposed LiveData

    private val _shuffledBookIds = MutableLiveData<List<String>>()
    val shuffledBookIds: LiveData<List<String>> = _shuffledBookIds

    private var hasShuffled = false

    private val _searchResults = MutableLiveData<List<BookDetails>>()
    val searchResults: LiveData<List<BookDetails>> get() = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> get() = _isSearching


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

    fun loadBookIds() {
        viewModelScope.launch {
            val result = bookRepository.getBookIds()
            if (result.isSuccess) {
                // result is successful, get the value (List<String>)
                _bookIds.value = result.getOrNull() ?: emptyList()
                if (!hasShuffled){
                    _shuffledBookIds.value = getShuffledBooks(_bookIds.value ?: emptyList())
                    hasShuffled = true
                }
            } else {
                // result is a failure, handle it
                _errorMessage.value =
                    "Failed to load book IDs: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun searchBooks(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        _isSearching.value = true
        viewModelScope.launch {
            try {
                val result = bookRepository.searchBooks(query)
                result.fold(
                    onSuccess = { books ->
                        _searchResults.postValue(books)
                        _isSearching.postValue(false)
                    },
                    onFailure = { exception ->
                        Log.e("BookViewModel", "Search failed: ${exception.message}")
                        _searchResults.postValue(emptyList())
                        _isSearching.postValue(false)
                        _errorMessage.postValue("Search failed: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                Log.e("BookViewModel", "Unexpected search error: ${e.message}")
                _searchResults.postValue(emptyList())
                _isSearching.postValue(false)
                _errorMessage.postValue("Unexpected error occurred")
            }
        }
    }

    fun searchBooksByGenre(genre: String) {
        _isSearching.value = true
        viewModelScope.launch {
            try {
                val result = bookRepository.searchBooksByGenre(genre)
                result.fold(
                    onSuccess = { books ->
                        _searchResults.postValue(books)
                        _isSearching.postValue(false)
                    },
                    onFailure = { exception ->
                        Log.e("BookViewModel", "Genre search failed: ${exception.message}")
                        _searchResults.postValue(emptyList())
                        _isSearching.postValue(false)
                        _errorMessage.postValue("Genre search failed: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                Log.e("BookViewModel", "Unexpected genre search error: ${e.message}")
                _searchResults.postValue(emptyList())
                _isSearching.postValue(false)
                _errorMessage.postValue("Unexpected error occurred")
            }
        }
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }

}

