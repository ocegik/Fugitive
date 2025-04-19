package com.example.fugitive.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fugitive.ui.theme.FugitiveColors
import kotlinx.coroutines.launch


@Composable
fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    thumbMinHeight: Dp = 40.dp // for long content, don't let the thumb become too small
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.TopEnd
    ) {
        val thumbMargin = with(density) { 18.dp.toPx() }

        val containerHeightPx = with(density) { maxHeight.toPx() }
        val maxScroll = scrollState.maxValue.toFloat()

        if (scrollState.maxValue > 0) {
            // how much of the content is visible
            val usableHeight = containerHeightPx - 2 * thumbMargin

            val visibleRatio = usableHeight / (usableHeight + maxScroll)

            val thumbHeightPx = (containerHeightPx * visibleRatio)
                .coerceAtLeast(with(density) { thumbMinHeight.toPx() })
            val thumbHeight = with(density) { thumbHeightPx.toDp() }

            val scrollRatio = scrollState.value.toFloat() / scrollState.maxValue.toFloat()
            val yOffsetPx = thumbMargin + (scrollRatio * (usableHeight - thumbHeightPx))
                .coerceIn(0f, containerHeightPx - thumbHeightPx)

            Box(
                modifier = Modifier
                    .offset { IntOffset(x = 0, y = yOffsetPx.toInt()) }
                    .width(8.dp)
                    .height(thumbHeight)
                    .clip(RoundedCornerShape(4.dp))
                    .background(FugitiveColors.button.copy(alpha = 0.9f))
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            val proportion = delta / (usableHeight - thumbHeightPx)
                            val scrollDelta = proportion * maxScroll
                            coroutineScope.launch {
                                val newValue = (scrollState.value + scrollDelta).toInt()
                                    .coerceIn(0, scrollState.maxValue)
                                scrollState.scrollTo(newValue)
                            }
                        }
                    )
            )
        }
    }
}

