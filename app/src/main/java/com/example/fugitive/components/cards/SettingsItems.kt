package com.example.fugitive.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun ProfileEditItem(
    title: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconRes: Int? = null,
    actionIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowForward,
    tint: Color = FugitiveColors.button
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Customizable Icon
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Profile Icon",
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        } else if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Profile Icon",
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.weight(1f))

        // Action Icon
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0x40FFFFFF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = actionIcon,
                contentDescription = "Action Icon",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
