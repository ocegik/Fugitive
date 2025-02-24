package com.example.fugitive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun BookItem(
    title: String,
    author: String,
    onClick: () -> Unit,
    imageWidth: Int,
    imageHeight: Int
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .width(imageWidth.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.book_cover_placeholder),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .clip(RoundedCornerShape(10.dp))
        )
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