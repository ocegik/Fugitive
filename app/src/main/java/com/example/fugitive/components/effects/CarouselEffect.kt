package com.example.fugitive.components.effects

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CarouselEffect(pages: List<String>) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = Int.MAX_VALUE / 2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0F0F),
                        Color(0xFF1A1A1A)
                    )
                )
            )
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            val centerIndex = remember {
                derivedStateOf {
                    val layoutInfo = listState.layoutInfo
                    val viewportCenter = layoutInfo.viewportEndOffset / 2
                    layoutInfo.visibleItemsInfo.minByOrNull {
                        val itemCenter = it.offset + it.size / 2
                        kotlin.math.abs(itemCenter - viewportCenter)
                    }?.index
                }
            }
            LazyRow(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                flingBehavior = rememberSnapFlingBehavior(listState),
                contentPadding = PaddingValues(horizontal = 25.dp)
            ) {


                items(Int.MAX_VALUE) { index ->
                    val actualIndex = index % pages.size

                    val isCenter = index == centerIndex.value

                    // Smooth animations
                    val scale by animateFloatAsState(
                        targetValue = if (isCenter) 1.0f else 0.9f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "scale"
                    )

                    val alpha by animateFloatAsState(
                        targetValue = if (isCenter) 1.0f else 0.7f,
                        animationSpec = tween(300, easing = FastOutSlowInEasing),
                        label = "alpha"
                    )

                    Card(
                        modifier = Modifier
                            .width(240.dp)
                            .height(280.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                            },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCenter) {
                                Color(0xFF6755FF)
                            } else {
                                Color(0xFF2A2A2A)
                            }
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isCenter) 12.dp else 4.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Icon/Number circle
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = if (isCenter) {
                                            Color.White.copy(alpha = 0.2f)
                                        } else {
                                            Color(0xFF6755FF).copy(alpha = 0.3f)
                                        },
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${actualIndex + 1}",
                                    color = if (isCenter) Color.White else Color(0xFFBBBBBB),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = pages[actualIndex],
                                color = if (isCenter) Color.White else Color(0xFFBBBBBB),
                                fontSize = 16.sp,
                                fontWeight = if (isCenter) FontWeight.Medium else FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(size) { index ->
            val isSelected = index == currentPage.value

            val width by animateIntAsState(
                targetValue = if (isSelected) 26 else 8,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "width"
            )

            val alpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.4f,
                animationSpec = tween(durationMillis = 300),
                label = "alpha"
            )

            Box(
                modifier = Modifier
                    .width(width.dp)
                    .height(8.dp)
                    .background(
                        if (isSelected) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6755FF), // Your primary color
                                    Color(0xFF5A47E6)  // Slightly darker variant
                                )
                            )
                        } else {
                            SolidColor(Color(0xFF999999).copy(alpha = alpha)) // Your onSurface color
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
                    .graphicsLayer { this.alpha = alpha }
            )
        }
    }
}
