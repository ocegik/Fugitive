package com.example.fugitive.components.text

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

// First, create a custom Text composable with long press detection
@Composable
fun LongPressText(
    text: String,
    onWordLongPress: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(
        fontSize = 18.sp,
        color = Color.White,
        lineHeight = 26.sp
    )
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = text,
        modifier = modifier
            .pointerInput(text) {
                detectTapGestures(
                    onLongPress = { offset ->
                        layoutResult.value?.let { layout ->
                            val position = layout.getOffsetForPosition(offset)
                            val word = extractWordAtPosition(text, position)
                            if (word.isNotBlank()) {
                                onWordLongPress(word)
                            }
                        }
                    }
                )
            },
        style = style,
        onTextLayout = { layoutResult.value = it }
    )
}

// Helper function to extract word at cursor position
private fun extractWordAtPosition(text: String, position: Int): String {
    if (position < 0 || position >= text.length) return ""

    // Find word boundaries
    var start = position
    var end = position

    // Move start backwards to find word beginning
    while (start > 0 && (text[start - 1].isLetter() || text[start - 1] == '\'')) {
        start--
    }

    // Move end forwards to find word ending
    while (end < text.length && (text[end].isLetter() || text[end] == '\'')) {
        end++
    }

    return text.substring(start, end).trim()
        .replace(Regex("[^a-zA-Z']"), "") // Remove punctuation except apostrophes
}