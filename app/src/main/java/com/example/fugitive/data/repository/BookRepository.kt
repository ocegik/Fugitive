package com.example.fugitive.data.repository

import android.net.Uri
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.models.BookDetails

class BookRepository(private val firestoreService: FirestoreService) {

    suspend fun getBookDetails(bookId: String): Result<BookDetails> {
        return try {
            val result = firestoreService.getBookDetails(bookId) // Fetch from FirestoreService

            result.fold(
                onSuccess = { bookMetadata ->
                    val bookDetails = BookDetails(
                        bookId = bookMetadata.bookId,
                        title = bookMetadata.title,
                        author = bookMetadata.author,
                        description = bookMetadata.description,
                        fileURL = bookMetadata.fileURL,
                        coverImageUri = bookMetadata.coverImageURL.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                        language = bookMetadata.language,
                        publishYear = bookMetadata.publishYear,
                        genres = bookMetadata.genres
                    )
                    Result.success(bookDetails) // Convert to BookDetails
                },
                onFailure = { exception ->
                    Result.failure(exception) // Pass the error along
                }
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
