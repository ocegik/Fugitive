package com.example.fugitive.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fugitive.R


@Composable
fun SocialLoginRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_google),
            contentDescription = "Google",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Icon(
            painterResource(id = R.drawable.icons_facebook),
            contentDescription = "Facebook",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Icon(
            painterResource(id = R.drawable.icons_x),
            contentDescription = "X",
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
    }
}