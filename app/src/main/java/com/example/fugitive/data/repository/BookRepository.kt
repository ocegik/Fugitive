package com.example.fugitive.data.repository

import android.net.Uri
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.data.models.BookDetails
import com.example.fugitive.data.remote.Chapter

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
                        coverImageUri = bookMetadata.coverImageURL.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                        language = bookMetadata.language,
                        publishYear = bookMetadata.publishYear,
                        genres = bookMetadata.genres,
                        totalChapters =  bookMetadata.totalChapters
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

    suspend fun getBookChapters(bookId: String): Result<List<Chapter>> {
        return firestoreService.getBookChapters(bookId)
    }
    suspend fun getBookIds(): Result<List<String>> {
        return firestoreService.getAllBookIds()
    }

    suspend fun searchBooks(query: String, limit: Int = 20): Result<List<BookDetails>> {
        return try {
            val result = firestoreService.searchBooks(query, limit)

            result.fold(
                onSuccess = { bookMetadataList ->
                    val bookDetailsList = bookMetadataList.map { bookMetadata ->
                        BookDetails(
                            bookId = bookMetadata.bookId,
                            title = bookMetadata.title,
                            author = bookMetadata.author,
                            description = bookMetadata.description,
                            coverImageUri = bookMetadata.coverImageURL.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                            language = bookMetadata.language,
                            publishYear = bookMetadata.publishYear,
                            genres = bookMetadata.genres,
                            totalChapters = bookMetadata.totalChapters
                        )
                    }
                    Result.success(bookDetailsList)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchBooksByGenre(genre: String, limit: Int = 20): Result<List<BookDetails>> {
        return try {
            val result = firestoreService.searchBooksByGenre(genre, limit)

            result.fold(
                onSuccess = { bookMetadataList ->
                    val bookDetailsList = bookMetadataList.map { bookMetadata ->
                        BookDetails(
                            bookId = bookMetadata.bookId,
                            title = bookMetadata.title,
                            author = bookMetadata.author,
                            description = bookMetadata.description,
                            coverImageUri = bookMetadata.coverImageURL.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                            language = bookMetadata.language,
                            publishYear = bookMetadata.publishYear,
                            genres = bookMetadata.genres,
                            totalChapters = bookMetadata.totalChapters
                        )
                    }
                    Result.success(bookDetailsList)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
