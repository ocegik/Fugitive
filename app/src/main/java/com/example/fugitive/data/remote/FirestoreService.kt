package com.example.fugitive.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
    suspend fun getUserData(userId: String): Result<UserMetadata> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                document.toObject(UserMetadata::class.java)?.let { user ->
                    println("Parsed UserMetadata: $user")
                    Result.success(user)
                } ?: Result.failure(Exception("User data is missing or invalid"))
            } else {
                Result.failure(Exception("User document does not exist"))
            }
        } catch (e: Exception) {
            println("Failed to fetch user data: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateUserData(userId: String, userMetadata: UserMetadata) {
        firestore.collection("users").document(userId)
            .set(userMetadata, SetOptions.merge())
            .await()
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

data class UserMetadata(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profilePicture: String = "",
)