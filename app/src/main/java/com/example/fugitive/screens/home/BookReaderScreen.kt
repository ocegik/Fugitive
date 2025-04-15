package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
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
            .padding(8.dp)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents overlapping with status bar
            .padding(top = 15.dp)
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart) // Ensures it's on the top-left
                .zIndex(2f) // Makes sure it stays on top
        ) {
            navController.popBackStack()
        }


        Box(modifier = Modifier.fillMaxSize()) {
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
                            .verticalScroll(scrollState)
                            .padding(16.dp)
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
        BoxWithConstraints(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(4.dp) // Width of scrollbar
                .background(Color.Gray.copy(alpha = 0.3f)) // Track background
        ) {
            val containerHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
            val scrollbarHeightPx = with(LocalDensity.current) { 60.dp.toPx() }
            val scrollRatio = scrollState.value.toFloat() / scrollState.maxValue.coerceAtLeast(1)

            val yOffset = with(LocalDensity.current) {
                (scrollRatio * (containerHeightPx - scrollbarHeightPx)).toDp()
            }

            Box(
                modifier = Modifier
                    .offset(y = yOffset)
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.DarkGray, RoundedCornerShape(4.dp))
            )
        }
    }
}
