package com.example.fugitive.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class UserRepository(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    private val userId: String? get() = auth.currentUser?.uid
    private val _bookmarks = MutableStateFlow<List<String>>(emptyList())
    val bookmarks: StateFlow<List<String>> = _bookmarks

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    init {
        repositoryScope.launch { fetchBookmarks() }
    }

    suspend fun fetchBookmarks() {
        userId?.let { uid ->
            try {
                val document = db.collection("users").document(uid).get().await()
                val fetchedBookmarks = (document.get("bookmarks") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                _bookmarks.value = fetchedBookmarks
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun addBookmark(bookId: String) {
        userId?.let { uid ->
            try {
                val updatedList = _bookmarks.value.toMutableList().apply { add(bookId) }
                _bookmarks.value = updatedList

                db.collection("users").document(uid)
                    .update("bookmarks", updatedList)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun removeBookmark(bookId: String) {
        userId?.let { uid ->
            try {
                val updatedList = _bookmarks.value.toMutableList().apply { remove(bookId) }
                _bookmarks.value = updatedList

                db.collection("users").document(uid)
                    .update("bookmarks", updatedList)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}