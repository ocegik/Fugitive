package com.example.fugitive.data.models

import android.net.Uri

data class BookDetails(
    val bookId: String,
    val title: String,
    val author: String,
    val description: String,
    val coverImageUri: Uri?,  // ✅ Converted from URL
    val language: String,
    val publishYear: Int,
    val genres: List<String>,
    val totalChapters: Int
)