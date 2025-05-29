package com.example.fugitive.data.remote.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun getAllBookIds(): Result<List<String>> {
        return try {
            val snapshot = firestore.collection("books").get().await()
            val bookIds = snapshot.documents.map { it.id }
            Result.success(bookIds)
        } catch (e: Exception) {
            println("Failed to fetch book IDs: ${e.message}")
            Result.failure(e)
        }
    }


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

    fun saveReadingProgress(userId: String, bookId: String, chapter: Int, scroll: Int) {

        val isRead = scroll >= 98

        val chapterData = mapOf(
            "scrollValue" to scroll,
            "isRead" to isRead,
            "timestamp" to FieldValue.serverTimestamp()
        )

        val bookRef = Firebase.firestore.collection("users")
            .document(userId)
            .collection("reading_status")
            .document(bookId)

        bookRef.collection("chapters")
            .document("$chapter")
            .set(chapterData, SetOptions.merge())

        // Save book-level state
        val bookData = mapOf(
            "currentChapter" to chapter,
            "timestamp" to FieldValue.serverTimestamp()
        )

        bookRef.set(bookData, SetOptions.merge())
    }

    suspend fun getReadingProgress(userId: String, bookId: String): Result<Pair<Int, Int>> {
        return try {
            val bookRef = Firebase.firestore.collection("users")
                .document(userId)
                .collection("reading_status")
                .document(bookId)
                .get()
                .await()

            val currentChapter = bookRef.getLong("currentChapter")?.toInt() ?: 1
            val chapterRef = Firebase.firestore.collection("users")
                .document(userId)
                .collection("reading_status")
                .document(bookId)
                .collection("chapters")
                .document("$currentChapter")
                .get()
                .await()

            val scroll = chapterRef.getLong("scrollValue")?.toInt() ?: 0

            Result.success(currentChapter to scroll)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchBooks(query: String, limit: Int = 20): Result<List<BookMetadata>> {
        return try {
            if (query.isBlank()) {
                return Result.success(emptyList())
            }

            val normalizedQuery = query.trim().lowercase()

            // Create multiple queries for different search approaches
            val titleQuery = firestore.collection("books")
                .whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + '\uf8ff')
                .limit(limit.toLong())
                .get()
                .await()

            val authorQuery = firestore.collection("books")
                .whereGreaterThanOrEqualTo("author", query)
                .whereLessThanOrEqualTo("author", query + '\uf8ff')
                .limit(limit.toLong())
                .get()
                .await()

            // Combine results from both queries
            val allDocuments = (titleQuery.documents + authorQuery.documents).distinctBy { it.id }

            val books = allDocuments.mapNotNull { document ->
                document.toObject(BookMetadata::class.java)?.copy(bookId = document.id)
            }

            // Additional client-side filtering for better search results
            val filteredBooks = books.filter { book ->
                book.title.contains(normalizedQuery, ignoreCase = true) ||
                        book.author.contains(normalizedQuery, ignoreCase = true) ||
                        book.genres.any { genre -> genre.contains(normalizedQuery, ignoreCase = true) } ||
                        book.description.contains(normalizedQuery, ignoreCase = true)
            }.take(limit)

            Result.success(filteredBooks)

        } catch (e: Exception) {
            println("Failed to search books: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun searchBooksByGenre(genre: String, limit: Int = 20): Result<List<BookMetadata>> {
        return try {
            val snapshot = firestore.collection("books")
                .whereArrayContains("genres", genre)
                .limit(limit.toLong())
                .get()
                .await()

            val books = snapshot.documents.mapNotNull { document ->
                document.toObject(BookMetadata::class.java)?.copy(bookId = document.id)
            }

            Result.success(books)
        } catch (e: Exception) {
            println("Failed to search books by genre: ${e.message}")
            Result.failure(e)
        }
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