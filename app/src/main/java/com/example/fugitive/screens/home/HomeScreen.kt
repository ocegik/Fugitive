package com.example.fugitive.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fugitive.R
import com.example.fugitive.navigation.Screen
import com.example.fugitive.components.book.BookItem
import com.example.fugitive.components.book.BookPlaceholder
import com.example.fugitive.components.book.FeaturedBook
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.cards.TopReaderItem
import com.example.fugitive.components.effects.ShimmerContainer
import com.example.fugitive.components.getDrawableResourceId
import com.example.fugitive.components.inputs.SearchBarPlaceholder
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel
import com.example.fugitive.viewmodels.UserViewModel

@Composable
fun HomeScreen( navController: NavController,
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


    LaunchedEffect(Unit) {
        bookViewModel.loadBookIds()
    }
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
            Log.d("HomeUserCheck", "User data fetched: UID=${it.uid}, Name=${it.name}, Email=${it.email}, PFP=${it.profilePicture}")
        } ?: Log.d("HomeUserCheck", "User is null or not yet fetched")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = profileImageResId,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { navController.navigate(Screen.Profile.route) }, // Navigate to Profile Screen

            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.navigate(Screen.Notification.route) }, // Navigate to Notifications Screen
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Heading
        HeadingText(
            "Beyond Pages,\nInto Worlds."
        )

        Spacer(modifier = Modifier.height(32.dp))

        SearchBarPlaceholder(onClick = { navController.navigate(Screen.Profile.route) })

        Spacer(modifier = Modifier.height(32.dp))

        if (books.size >= 3) {  // ✅ Check if we have at least 3 books

            val featuredBook = books.getOrNull(0) // 🛡️ Safe access

            featuredBook?.let { book ->
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

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Reading Section
            HeadingText(
                "Popular Books",
                modifier = Modifier.fillMaxWidth().align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books.drop(1).take(4)) { book ->  // <- ✨ items() not item()
                    ShimmerContainer(isLoading) {
                        BookItem(
                            title = book.title,
                            author = book.author,
                            imageUri = book.coverImageUri?.toString(),
                            onClick = {
                                navController.navigate(
                                    Screen.BookDetail.createRoute(book.bookId)
                                )
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))

            HeadingText("New Arrivals")
            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books.drop(5)) { book ->  // <- ✨ items() not item()
                    ShimmerContainer(isLoading) {
                        BookItem(
                            title = book.title,
                            author = book.author,
                            imageUri = book.coverImageUri?.toString(),
                            onClick = {
                                navController.navigate(
                                    Screen.BookDetail.createRoute(book.bookId)
                                )
                            }
                        )
                    }
                }
            }
        } else {
            BookPlaceholder() // Show a placeholder until books load
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Top Readers Section
        HeadingText("Top Readers")

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TopReaderItem(
                name = "Naruto",
                pagesRead = 451,
                imageRes = R.drawable.dog
            )
            TopReaderItem(
                name = "Goku",
                pagesRead = 231,
                imageRes = R.drawable.camel
            )
            TopReaderItem(
                name = "Ichigo",
                pagesRead = 201,
                imageRes = R.drawable.sale
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}


