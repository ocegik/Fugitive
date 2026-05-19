package com.example.fugitive.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fugitive.R
import com.example.fugitive.components.book.BookItem
import com.example.fugitive.components.book.BookPlaceholder
import com.example.fugitive.components.book.FeaturedBook
import com.example.fugitive.components.effects.ShimmerContainer
import com.example.fugitive.components.getDrawableResourceId
import com.example.fugitive.components.inputs.SearchBarPlaceholder
import com.example.fugitive.navigation.Screen
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel
import com.example.fugitive.viewmodels.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    bookViewModel: BookViewModel
) {
    val bookIds by bookViewModel.bookIds.observeAsState(emptyList())
    val shuffledBookIds by bookViewModel.shuffledBookIds.observeAsState(emptyList())
    val books by bookViewModel.books.observeAsState(emptyList())
    val user by userViewModel.user

    val userId = user?.uid
    val isLoading = books.isEmpty()

    val profileImageResId = user?.profilePicture?.let {
        getDrawableResourceId(it)
    } ?: R.drawable.owl

    LaunchedEffect(Unit) { bookViewModel.loadBookIds() }

    var isBooksFetched by remember { mutableStateOf(false) }

    LaunchedEffect(bookIds, userId) {
        if (bookIds.isNotEmpty() && userId != null && !isBooksFetched) {
            Log.d("HomeScreen", "Fetching books for IDs: $shuffledBookIds, userId: $userId")
            bookViewModel.loadMultipleBooks(shuffledBookIds)
            Log.d("HomeScreen", "Fetching user data for userId: $userId")
            userViewModel.fetchUserDetails(userId)
            isBooksFetched = true
        }
    }

    LaunchedEffect(user) {
        user?.let {
            Log.d("HomeUserCheck", "User: UID=${it.uid}, Name=${it.name}, PFP=${it.profilePicture}")
        } ?: Log.d("HomeUserCheck", "User is null or not yet fetched")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
    ) {
        // Top ambient glow
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-60).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF6755FF).copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Top bar ──────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                AsyncImage(
                    model = profileImageResId,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate(Screen.Profile.route) }
                )

                // Notification bell with subtle tinted background
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1A1A1A))
                        .clickable { navController.navigate(Screen.Notification.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = buildAnnotatedString {
                    append("Beyond Pages,\n")
                    withStyle(SpanStyle(color = Color(0xFF6755FF))) {
                        append("Into Worlds.")
                    }
                },
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Search bar ───────────────────────────────────────────
            SearchBarPlaceholder(onClick = { navController.navigate(Screen.Search.route) })

            Spacer(modifier = Modifier.height(32.dp))

            // ── Content ──────────────────────────────────────────────
            if (books.size >= 3) {
                val featuredBook = books.getOrNull(0)

                featuredBook?.let { book ->
                    SectionLabel(text = "Featured")
                    Spacer(modifier = Modifier.height(14.dp))
                    FeaturedBook(
                        title = book.title,
                        author = book.author,
                        description = book.description,
                        imageUri = book.coverImageUri?.toString(),
                        onReadClick = {
                            navController.navigate(Screen.BookDetail.createRoute(book.bookId))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                SectionHeader(
                    title = "Popular Books",
                    onSeeAll = { /* TODO */ }
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(books.drop(1).take(4)) { book ->
                        ShimmerContainer(isLoading) {
                            BookItem(
                                title = book.title,
                                author = book.author,
                                imageUri = book.coverImageUri?.toString(),
                                onClick = {
                                    navController.navigate(Screen.BookDetail.createRoute(book.bookId))
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                SectionHeader(
                    title = "New Arrivals",
                    onSeeAll = { /* TODO */ }
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(books.drop(5)) { book ->
                        ShimmerContainer(isLoading) {
                            BookItem(
                                title = book.title,
                                author = book.author,
                                imageUri = book.coverImageUri?.toString(),
                                onClick = {
                                    navController.navigate(Screen.BookDetail.createRoute(book.bookId))
                                }
                            )
                        }
                    }
                }
            } else {
                BookPlaceholder()
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ── Small helpers ────────────────────────────────────────────────────────────

/** Pill label like "Featured" */
@Composable
private fun SectionLabel(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF6755FF).copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = Color(0xFF6755FF)
        )
    }
}

/** Section title row with "See all" link */
@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}