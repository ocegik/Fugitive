package com.example.fugitive.data.models

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object BookTextFetcher {
    suspend fun fetchBookText(fileURL: String): Result<String> {
        return try {
            val url = URL(fileURL)
            val text = withContext(Dispatchers.IO) {
                url.readText()
            }
            Result.success(text)
        } catch (e: Exception) {
            Log.e("📘 BookTextFetcher", "Failed: ${e.message}")
            Result.failure(e)
        }
    }
}
