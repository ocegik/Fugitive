package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.VerticalScrollbar
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel

@Composable
fun BookReaderScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    bookId: String,
    chapterNumber: Int
) {

    val bookDetails by bookViewModel.bookDetails.observeAsState()
    val chapters by bookViewModel.bookChapters.observeAsState(emptyList())
    val chapterText by bookViewModel.selectedChapterText.observeAsState()

    LaunchedEffect(bookId, chapterNumber) {
        bookViewModel.loadBookData(bookId)
        bookViewModel.loadBookChapters(bookId)
    }

    LaunchedEffect(chapters) {
        if (chapters.isNotEmpty()) {
            val targetChapter = chapters.getOrNull(chapterNumber - 1)
            targetChapter?.let { bookViewModel.loadChapterText(it.content) }
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents overlapping with status bar
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .padding(16.dp)
                .zIndex(2f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp)
        ) {
            when {
                bookDetails == null || chapters.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                chapterText == null -> {
                    Text(
                        text = "Loading chapter...",
                        modifier = Modifier.align(Alignment.Center),
                        style = TextStyle( // <- Custom TextStyle
                            fontSize = 20.sp, // <- Bigger size (change as needed)
                            color = Color.White, // <- White color text
                            lineHeight = 28.sp // Optional: Better spacing for paragraphs
                        )
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding( start = 16.dp,
                                end = 28.dp, // extra space for scrollbar
                                top = 8.dp,
                                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                    ) {
                        Text(
                            text = chapterText ?: "No text available",
                            style = TextStyle( // <- Custom TextStyle
                                fontSize = 20.sp, // <- Bigger size (change as needed)
                                color = Color.White, // <- White color text
                                lineHeight = 28.sp // Optional: Better spacing for paragraphs
                            )
                        )
                    }
                }
            }
        }
        VerticalScrollbar(
            scrollState = scrollState,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .padding(end = 4.dp) // ensures it's on the right
        )
        Spacer(modifier = Modifier.height(40.dp))
    }

}
