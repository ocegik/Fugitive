package com.example.fugitive.data.remote

import com.example.fugitive.data.local.CachedUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun getBookDetails(bookId: String): Result<BookMetadata> {
        return try {
            val document = firestore.collection("books").document(bookId).get().await()
            if (document.exists()) {
                document.toObject(BookMetadata::class.java)?.let { book ->
                    println("Parsed BookMetadata: $book")
                    Result.success(book)
                } ?: Result.failure(Exception("Book data is missing or invalid"))
            } else {
                Result.failure(Exception("Book document does not exist"))
            }
        } catch (e: Exception) {
            println("Failed to fetch book data: ${e.message}")
            Result.failure(e)
        }
    }
    suspend fun getUserData(userId: String): Result<CachedUser> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val user = document.toObject(CachedUser::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class BookMetadata(
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val fileURL: String = "",
    val coverImageURL: String = "",
    val language: String = "",
    val publishYear: Int = 0,
    val genres: List<String> = emptyList()
)