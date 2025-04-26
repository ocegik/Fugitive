package com.example.fugitive.utils


fun getShuffledBooks(books: List<String>): List<String> {
    return books.shuffled().take(9)
}
