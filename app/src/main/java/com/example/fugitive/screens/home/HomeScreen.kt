package com.example.fugitive.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.R
import com.example.fugitive.Screen
import com.example.fugitive.components.BookItem
import com.example.fugitive.components.HeadingText
import com.example.fugitive.components.SearchBar
import com.example.fugitive.components.TopReaderItem
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun HomeScreen(navController: NavController) {

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
            Image(
                painter = painterResource(id = R.drawable.user_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(32.dp)
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
            "Beyond Pages,\nInto Worlds.")

        Spacer(modifier = Modifier.height(32.dp))

        SearchBar(onClick = {navController.navigate(Screen.Profile.route)})

        Spacer(modifier = Modifier.height(32.dp))

        HeadingText("Trending Books", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

        Spacer(modifier = Modifier.height(24.dp))
        // Featured Books
        LazyRow(
            modifier = Modifier.height(360.dp)
        ) {
            items(listOf(
                Pair("Heredity", "Tarun Choudhary"),
                Pair("Atomic Habits", "James Clear"),
                Pair("The Alchemist", "Paulo Coelho")
            )) { (title, author) ->
                BookItem(
                    title = title,
                    author = author,
                    imageWidth = 170,
                    imageHeight = 240,
                    onClick = { navController.navigate(Screen.BookDetail.route) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Continue Reading Section
        HeadingText("Continue Reading", modifier = Modifier.fillMaxWidth().align(Alignment.Start))

        Spacer(modifier = Modifier.height(32.dp))
        LazyRow {
            item {
                BookItem(
                    title = "The Beloved",
                    author = "Harriet Evans",
                    imageWidth = 120,
                    imageHeight = 160,
                    onClick = { navController.navigate(Screen.BookDetail.route) } // Navigate to Book Details
                )
            }
            item {
                BookItem(
                    title = "Educated",
                    author = "Tara Westover",
                    imageWidth = 120,
                    imageHeight = 160,
                    onClick = { navController.navigate(Screen.BookDetail.route) } // Navigate to Book Details
                )
            }
            item {
                BookItem(
                    title = "Blocking",
                    author = "Wes Anderson",
                    imageWidth = 120,
                    imageHeight = 160,
                    onClick = { navController.navigate(Screen.BookDetail.route) } // Navigate to Book Details
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Top Readers Section
        HeadingText("Top Readers")

        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TopReaderItem(name = "Sung Jin Woo", pagesRead = 451, imageRes = R.drawable.user_placeholder)
            TopReaderItem(name = "Son Goku", pagesRead = 231, imageRes = R.drawable.user_placeholder)
            TopReaderItem(name = "Eren Yeager", pagesRead = 201, imageRes = R.drawable.user_placeholder)
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}