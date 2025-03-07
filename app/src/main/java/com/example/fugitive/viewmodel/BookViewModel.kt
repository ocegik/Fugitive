package com.example.fugitive.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fugitive.data.remote.BookMetadata
import com.example.fugitive.data.remote.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val firestoreService = FirestoreService(FirebaseFirestore.getInstance())

    private val _bookDetails = MutableLiveData<BookDetails?>()
    val bookDetails: LiveData<BookDetails?> get() = _bookDetails

    fun loadBookData(bookId: String) {
        viewModelScope.launch {
            try {
                val result = firestoreService.getBookDetails(bookId) // ✅ Fetch Result<BookMetadata>

                result.fold(
                    onSuccess = { book ->  // ✅ Unwrap success case
                        val coverUri = Uri.parse(book.coverImageURL).takeIf { it.toString().isNotBlank() }
                        _bookDetails.postValue(BookDetails(book, coverUri))
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
}

data class BookDetails(
    val metadata: BookMetadata,
    val coverImageUri: Uri?
)

