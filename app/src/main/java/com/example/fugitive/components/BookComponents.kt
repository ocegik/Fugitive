package com.example.fugitive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun BookItem(
    title: String,
    author: String,
    onClick: () -> Unit,
    imageWidth: Int,
    imageHeight: Int,
    imageUri : String?
) {
    var isLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .width(imageWidth.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        ){
            AsyncImage(
                model = imageUri ?: R.drawable.book_cover_placeholder,
                contentDescription = title,
                onSuccess = {isLoading = false},
                onError = {isLoading = false},
                modifier = Modifier
                    .matchParentSize()
            )
        }

        println("Loading image: $imageUri")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FugitiveColors.heading
        )
        Text(
            text = author,
            fontSize = 12.sp,
            color = FugitiveColors.subheading
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedBook(
    title: String,
    author: String,
    description: String,
    genres: List<String>,
    imageUri: String?,
    isBookmarked: Boolean,
    onReadClick: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 16.dp, top = 16.dp, bottom = 16.dp), // Shift left slightly
        horizontalArrangement = Arrangement.Start
    ) {
        // Book Cover
        Box(
            modifier = Modifier
                .width(160.dp) // Reduced a bit to give more text space
                .height(230.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A2A2A)) // Darker placeholder background
        ) {
            var imageLoaded by remember { mutableStateOf(false) }

            AsyncImage(
                model = imageUri,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (imageLoaded) 1f else 0f) // Fade in effect
                    .onGloballyPositioned { imageLoaded = true }, // Mark image as loaded
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            if (!imageLoaded) {
                Image(
                    painter = painterResource(R.drawable.book_cover_placeholder),
                    contentDescription = "Loading",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f), // Slight transparency for smoother effect
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Book Details
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp) // More space for text
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "By $author",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Genre Tags using FlowRow
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                genres.forEach { genre ->
                    GenreTag(genre)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bookmark & Read Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomBookmarkButton(isBookmarked = isBookmarked, onBookmarkToggle = onBookmarkToggle)

                Spacer(modifier = Modifier.width(12.dp))

                FugitivePrimaryButton(
                    text = "Start Reading",
                    onClick = onReadClick
                )
            }
        }
    }
}

@Composable
fun GenreTag(title: String) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}
