package com.example.fugitive.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
