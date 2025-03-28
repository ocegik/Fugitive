package com.example.fugitive.components.effects

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    height: Dp = 0.dp,
    cornerRadius: Dp = 6.dp
) {
    val transition = rememberInfiniteTransition()

    val shimmerColor by transition.animateColor(
        initialValue = Color(0xFF2A2A2A),
        targetValue = Color(0xFF3A3A3A),
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .then(if (height > 0.dp) Modifier.height(height) else Modifier.fillMaxSize()) // Supports both text & full-size shimmer
            .clip(RoundedCornerShape(cornerRadius))
            .background(shimmerColor)
    )
}