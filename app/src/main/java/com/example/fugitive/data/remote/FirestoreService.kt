package com.example.fugitive.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun getBookDetails(bookId: String): Result<BookMetadata> {
        return try {
            val document = firestore.collection("books").document(bookId).get().await()

            if (document.exists()) {
                document.toObject(BookMetadata::class.java)?.let { book ->
                    val bookWithId = book.copy(bookId = document.id)
                    println("Parsed BookMetadata: $bookWithId")
                    Result.success(bookWithId)

                } ?: Result.failure(Exception("Book data is missing or invalid"))

            } else {
                Result.failure(Exception("Book document does not exist"))
            }

        } catch (e: Exception) {
            println("Failed to fetch book data: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getBookChapters(bookId: String): Result<List<Chapter>> {
        return try {
            val chaptersSnapshot = firestore
                .collection("books")
                .document(bookId)
                .collection("chapters")
                .orderBy("title") // Optional: Or use "index" if you have it
                .get()
                .await()

            val chapters = chaptersSnapshot.documents.mapNotNull { it.toObject(Chapter::class.java) }
            Result.success(chapters)
        } catch (e: Exception) {
            Log.d("FirestoreService", "Failed to fetch chapters: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserData(uid: String): Result<UserMetadata> {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.toObject(UserMetadata::class.java)
                ?.let { Result.success(it) }
                ?: Result.failure(Exception("User document does not exist or is invalid"))
        }
        catch (e: Exception) {
            println("Failed to fetch user data: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateUserData(uid: String, userMetadata: UserMetadata) {
        firestore.collection("users").document(uid)
            .set(userMetadata, SetOptions.merge())
            .await()
    }
}


data class Chapter(
    val title: String = "",
    val content: String = ""
)

data class BookMetadata(
    val bookId: String = "",
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val coverImageURL: String = "",
    val language: String = "",
    val publishYear: Int = 0,
    val genres: List<String> = emptyList(),
    val totalChapters: Int = 0
)

data class UserMetadata(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profilePicture: String = "",
)