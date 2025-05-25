package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.inputs.BookSearchResultItem
import com.example.fugitive.components.inputs.SearchBarInput
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel

@Composable
fun SearchScreen(navController: NavController, bookViewModel: BookViewModel) {

    var searchQuery by remember { mutableStateOf("") }

    // Observe search results and loading state
    val searchResults by bookViewModel.searchResults.observeAsState(emptyList())
    val isSearching by bookViewModel.isSearching.observeAsState(false)
    val errorMessage by bookViewModel.errorMessage.observeAsState()

    // Clear search results when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            bookViewModel.clearSearchResults()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            BackButton(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .zIndex(2f)
            ) {
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        SearchBarInput(
            query = searchQuery,
            onQueryChange = { newQuery ->
                searchQuery = newQuery
                if (newQuery.isBlank()) {
                    bookViewModel.clearSearchResults()
                } else {
                    // Search will be debounced in ViewModel
                    bookViewModel.searchBooks(newQuery)
                }
            },
            onSearch = { query ->
                if (query.isNotBlank()) {
                    bookViewModel.searchBooks(query)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        when {
            isSearching -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = FugitiveColors.button)
                }
            }

            searchResults.isNotEmpty() -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults) { book ->
                        BookSearchResultItem(
                            book = book,
                            onClick = {
                                Screen.BookDetail.createRoute(book.bookId)
                            }
                        )
                    }
                }
            }

            searchQuery.isNotBlank() && searchResults.isEmpty() && !isSearching -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No books found for \"$searchQuery\"",
                            color = FugitiveColors.subheading,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Try different keywords or browse by genre",
                            color = FugitiveColors.subheading.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
