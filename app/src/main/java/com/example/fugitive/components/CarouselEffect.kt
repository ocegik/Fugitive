package com.example.fugitive.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp


@Composable
fun CarouselEffect(pages: List<String>) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally),
                flingBehavior = rememberSnapFlingBehavior(listState),
                contentPadding = PaddingValues(horizontal = 100.dp) // Better centering
            ) {
                items(Int.MAX_VALUE) { index ->
                    val actualIndex = index % pages.size  // Loop through pages infinitely

                    val viewPortCenter = remember {
                        derivedStateOf {
                            listState.layoutInfo.viewportEndOffset / 2
                        }
                    }

                    val itemOffset = remember {
                        derivedStateOf {
                            listState.layoutInfo.visibleItemsInfo
                                .firstOrNull { it.index == index }
                                ?.offset ?:0
                        }
                    }

                    val scale by animateFloatAsState(
                        targetValue = if (kotlin.math.abs(itemOffset.value - viewPortCenter.value) < 50) 1.4f else 1.1f,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Box(
                        modifier = Modifier
                            .width(240.dp) // 🔥 Increased width for bigger cards
                            .height(240.dp) // 🔥 Ensure enough height
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .background(
                                if (scale > 1.2f) Color.DarkGray else Color.Gray,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pages[actualIndex],
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        DotsIndicator(pages.size, listState)
    }
}

@Composable
fun DotsIndicator(size: Int, listState: LazyListState) {
    val currentPage = remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex % size)
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(size) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp) // Current dot is bigger
                    .background(
                        if (index == currentPage.value) Color.DarkGray else Color.LightGray,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}