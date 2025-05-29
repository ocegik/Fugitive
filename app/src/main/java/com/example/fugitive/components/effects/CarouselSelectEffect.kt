package com.example.fugitive.components.effects

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.example.fugitive.R
import com.example.fugitive.ui.theme.FugitiveColors

@Composable
fun CarouselSelectEffect(
    onSelectionChanged: (String) -> Unit
) {
    val images = listOf(
        R.drawable.lion, R.drawable.owl,
        R.drawable.sale, R.drawable.koala,
        R.drawable.zebra, R.drawable.dog,
        R.drawable.camel, R.drawable.hippo
    )
    val labels = listOf("Lion", "Owl", "Sale", "Koala", "Zebra", "Dog", "Camel", "Hippo")
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)

    val centerItemIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val viewportCenter = layoutInfo.viewportEndOffset / 2

            layoutInfo.visibleItemsInfo.minByOrNull {
                kotlin.math.abs(it.offset + it.size / 2 - viewportCenter)
            }?.index ?: 0
        }
    }
    LaunchedEffect(centerItemIndex) {
        onSelectionChanged(labels[centerItemIndex % labels.size])
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally),
            flingBehavior = rememberSnapFlingBehavior(listState),
            contentPadding = PaddingValues(horizontal = 100.dp)
        ) {
            items(Int.MAX_VALUE) { index ->
                val actualIndex = index % images.size
                val isSelected = index == centerItemIndex

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.4f else 1.1f,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .clip(CircleShape)
                            .border(
                                width = 4.dp,
                                color = if (isSelected) FugitiveColors.button else Color.Gray, // Highlight center item
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = images[actualIndex]),
                            contentDescription = labels[actualIndex],
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = labels[actualIndex], fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
        DotsIndicator(images.size, listState)
    }
}
