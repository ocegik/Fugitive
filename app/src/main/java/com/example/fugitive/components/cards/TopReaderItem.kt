package com.example.fugitive.components.cards

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
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = name, fontSize = 18.sp, color = FugitiveColors.heading)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$pagesRead Pages", fontSize = 14.sp, color = FugitiveColors.subheading)
    }
}