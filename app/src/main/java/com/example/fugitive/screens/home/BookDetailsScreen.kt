package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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

            // ── Blurred hero background — unchanged ──────────────────
            AsyncImage(
                model = book.coverImageUri ?: R.drawable.book_cover_placeholder,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .blur(28.dp)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    backgroundColor.copy(alpha = 0.7f),
                                    backgroundColor
                                ),
                                startY = size.height * 0.4f,
                                endY = size.height
                            ),
                            size = size
                        )
                    },
                contentScale = ContentScale.Crop
            )

            // ── Back button ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(start = 15.dp, top = 15.dp)
            ) {
                BackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .zIndex(2f)
                ) {
                    navController.popBackStack()
                }
            }

            // ── Main content column ──────────────────────────────────
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))

                // Cover image with purple border accent
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    AsyncImage(
                        model = book.coverImageUri ?: R.drawable.book_cover_placeholder,
                        contentDescription = "Book Cover",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Title
                Text(
                    text = book.title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    modifier = Modifier.padding(horizontal = 28.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Author — italic + purple accent
                Text(
                    text = buildAnnotatedString {
                        append("by ")
                        withStyle(SpanStyle(color = Color(0xFF6755FF), fontStyle = FontStyle.Italic)) {
                            append(book.author)
                        }
                    },
                    color = Color(0xFF999999),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Stats row
                BookDetailsRow(chapters = chapters, year = book.publishYear, wordCount = wordCount)

                Spacer(modifier = Modifier.height(28.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .padding(horizontal = 28.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF2A2A2A))
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                Text(
                    text = book.description,
                    color = Color(0xFF999999),
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.3.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Start reading CTA
                Box(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
                    FugitivePrimaryButton(
                        text = "Start Reading",
                        onClick = {
                            navController.navigate(
                                Screen.BookReader.createRoute(book.bookId, chapterNumber = 1)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // ── Chapter section ──────────────────────────────────
                // Header card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chapters",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // Total chapters pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF6755FF).copy(alpha = 0.12f))
                            .border(
                                0.5.dp,
                                Color(0xFF6755FF).copy(alpha = 0.35f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${book.totalChapters} total",
                            color = Color(0xFF6755FF),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ChapterList(
                    chapterCount = book.totalChapters,
                    onChapterClick = { chapterNumber ->
                        navController.navigate(
                            Screen.BookReader.createRoute(book.bookId, chapterNumber)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(100.dp))
            }

        } ?: run {
            // Loading state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF6755FF))
            }
        }
    }
}