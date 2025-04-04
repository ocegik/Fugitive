package com.example.fugitive.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fugitive.components.button.CustomBookmarkButton
import com.example.fugitive.components.button.FugitivePrimaryButton

@Composable
fun FeaturedBook(
    title: String,
    author: String,
    description: String,
    imageUri: String?,
    onReadClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        // Book Cover
        Box(
            modifier = Modifier
                .width(160.dp) // Reduced a bit to give more text space
                .height(230.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A2A2A)) // Darker placeholder background
        ) {

            AsyncImage(
                model = imageUri,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Book Details
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "By $author",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.White,
                    minLines = 2,
                    maxLines = 3,
                    style = TextStyle(lineHeight = 20.sp),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))


            //  Read Button
            FugitivePrimaryButton(
                text = "Start Reading",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                ,
                onClick = onReadClick
            )
            }
        }
    }
