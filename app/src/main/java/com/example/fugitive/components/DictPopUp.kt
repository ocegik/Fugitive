package com.example.fugitive.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.fugitive.data.dictionary.DictionaryResponse
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close


// Dictionary popup component
@Composable
fun DictionaryPopup(
    word: String,
    dictionaryResult: List<DictionaryResponse>?,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = word.uppercase(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                when {
                    dictionaryResult == null -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Looking up definition...",
                                color = Color.White
                            )
                        }
                    }

                    dictionaryResult.isEmpty() -> {
                        Text(
                            text = "No definition found for \"$word\"",
                            color = Color.Gray
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 400.dp)
                        ) {
                            items(dictionaryResult.first().meanings) { meaning ->
                                Text(
                                    text = meaning.partOfSpeech,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Cyan
                                    ),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )

                                meaning.definitions.take(3).forEach { definition ->
                                    Text(
                                        text = "• ${definition.definition}",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = Color.White,
                                            lineHeight = 18.sp
                                        ),
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            bottom = 4.dp
                                        )
                                    )

                                    definition.example?.let { example ->
                                        Text(
                                            text = "Example: $example",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                fontStyle = FontStyle.Italic
                                            ),
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                bottom = 8.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}