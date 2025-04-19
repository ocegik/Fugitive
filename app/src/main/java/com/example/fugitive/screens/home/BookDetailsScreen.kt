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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fugitive.R
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.BookDetailsRow
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.cards.ChapterList
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel


@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    bookId: String
) {
    val bookDetails by bookViewModel.bookDetails.observeAsState()
    val backgroundColor = FugitiveColors.background
    val chapters = 12
    val wordCount = 89901
    LaunchedEffect(bookId) {
        bookViewModel.loadBookData(bookId)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        bookDetails?.let { book ->
            AsyncImage(
                model = book.coverImageUri ?: R.drawable.book_cover_placeholder,
                contentDescription = "Background Book Cover",
                modifier = Modifier
                    .fillMaxSize()
                    .height(400.dp)
                    .blur(30.dp)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    backgroundColor.copy(alpha = 0.8f),
                                    backgroundColor
                                ),
                                startY = size.height * 0.8f, // Start gradient halfway down
                                endY = size.height
                            ),
                            size = size
                        )
                    },
                contentScale = ContentScale.Crop

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents overlapping with status bar
                    .padding(start = 15.dp, top = 15.dp)
            ) // General padding)
            {
                BackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart) // Ensures it's on the top-left
                        .zIndex(2f) // Makes sure it stays on top
                ) {
                    navController.popBackStack()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(80.dp))
                AsyncImage(
                    model = book.coverImageUri ?: R.drawable.book_cover_placeholder,
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))

                )
                Spacer(modifier = Modifier.height(40.dp))

                HeadingText(book.title, fontSize = 24)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = book.author,
                    color = FugitiveColors.heading,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(28.dp))

                BookDetailsRow(chapters = chapters, year = book.publishYear, wordCount = wordCount)

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = book.description,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = FugitiveColors.heading,
                        lineHeight = 25.sp,
                        letterSpacing = 0.5.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                //CustomBookmarkButton(isBookmarked)
                FugitivePrimaryButton(
                    "Start Reading",
                    onClick = { navController.navigate(Screen.BookReader.createRoute(book.bookId, chapterNumber = 1)) })

                Spacer(modifier = Modifier.height(60.dp))

                HeadingText("Read by Chapters")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Total Chapters: ${book.totalChapters}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = FugitiveColors.heading,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                // Chapter List Composable
                ChapterList(
                    chapterCount = book.totalChapters,
                    onChapterClick = { chapterNumber ->
                        // You can route to a screen like: BookReader/{bookId}/{chapterNumber}
                        navController.navigate(Screen.BookReader.createRoute(book.bookId, chapterNumber))
                    }
                )

                Spacer(modifier = Modifier.height(100.dp))


            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}
