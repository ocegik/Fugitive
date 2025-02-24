package com.example.fugitive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun TopReaderItem(name: String, pagesRead: Int, imageRes: Int) {
    Column(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = name, fontSize = 16.sp, color = FugitiveColors.heading)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$pagesRead Pages", fontSize = 12.sp, color = FugitiveColors.subheading)
    }
}