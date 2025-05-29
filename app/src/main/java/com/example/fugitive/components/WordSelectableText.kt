package com.example.fugitive.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordSelectableText(
    text: String,
    onWordClick: (String) -> Unit
) {
    val words = text.split(" ")

    FlowRow(modifier = Modifier.fillMaxWidth()) {
        for (word in words) {
            Text(
                text = word,
                color = Color.White,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .clickable { onWordClick(word.trimPunctuation()) }
            )
        }
    }
}

private fun String.trimPunctuation(): String {
    return this.trim().trimEnd('.', ',', ';', ':', '?', '!', ')', '(', '"', '\'')
}
