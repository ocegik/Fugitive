package com.example.fugitive.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.components.DictionaryPopup
import com.example.fugitive.components.text.LongPressText
import com.example.fugitive.components.VerticalScrollbar
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.BookViewModel
import com.example.fugitive.viewmodels.DictionaryViewModel
import com.example.fugitive.viewmodels.UserViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@OptIn(FlowPreview::class)
@Composable
fun BookReaderScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    userViewModel: UserViewModel,
    bookId: String,
    chapterNumber: Int,
    dictionaryViewModel: DictionaryViewModel
) {
    val bookDetails by bookViewModel.bookDetails.observeAsState()
    val chapters by bookViewModel.bookChapters.observeAsState(emptyList())
    val chapterText by bookViewModel.selectedChapterText.observeAsState()
    val dictionaryResult by dictionaryViewModel.result

    val scrollState = rememberScrollState()
    var restoredScroll by remember { mutableStateOf(false) }
    var currentChapter by remember { mutableIntStateOf(chapterNumber) }

    var showDictionaryPopup by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf("") }

    fun handleWordLongPress(word: String) {
        selectedWord = word
        dictionaryViewModel.search(word)
        showDictionaryPopup = true
    }

    LaunchedEffect(bookId) {
        val (savedChapter, savedScroll) = userViewModel.getReadingProgress(bookId)
        currentChapter = savedChapter
        bookViewModel.loadBookData(bookId)
        bookViewModel.loadBookChapters(bookId)
        snapshotFlow { chapters }
            .filter { it.isNotEmpty() }
            .first()
            .let {
                val targetChapter = chapters.getOrNull(currentChapter - 1)
                targetChapter?.let { bookViewModel.loadChapterText(it.content) }
                scrollState.scrollTo(savedScroll)
                restoredScroll = true
            }
    }

    LaunchedEffect(scrollState, restoredScroll) {
        if (!restoredScroll) return@LaunchedEffect
        var lastSavedScroll = scrollState.value
        snapshotFlow { scrollState.value }
            .distinctUntilChanged()
            .debounce(1000)
            .collect { scrollY ->
                val scrollDelta = kotlin.math.abs(scrollY - lastSavedScroll)
                if (scrollDelta > 100) {
                    lastSavedScroll = scrollY
                    userViewModel.saveReadingProgress(bookId, currentChapter, scrollY)
                }
            }
    }

    // Primary brand color
    val primary = Color(0xFF6755FF)
    val background = Color(0xFF121212)

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Subtle radial glow from top for depth
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1730), // very dark purple tint at center-top
                        background
                    ),
                    center = Offset(Float.POSITIVE_INFINITY / 2, 0f),
                    radius = 1200f
                )
            )
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        // Frosted top bar gradient overlay (gives the back button area a polished feel)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            background.copy(alpha = 0.95f),
                            Color.Transparent
                        )
                    )
                )
                .zIndex(1f)
        )

        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(2f)
        ) {
            navController.popBackStack()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
        ) {
            when {
                bookDetails == null || chapters.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = primary,
                        strokeWidth = 2.dp
                    )
                }

                chapterText == null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(
                            color = primary,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Loading chapter...",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color(0xFF999999), // onSurface
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(
                                start = 20.dp,
                                end = 32.dp, // extra space for scrollbar
                                top = 8.dp,
                                bottom = WindowInsets.navigationBars
                                    .asPaddingValues()
                                    .calculateBottomPadding()
                            )
                    ) {
                        // Chapter label with accent line
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 20.dp, top = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(16.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(primary, primary.copy(alpha = 0.3f))
                                        ),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Chapter $currentChapter",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = primary,
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 1.5.sp
                                )
                            )
                        }

                        LongPressText(
                            text = chapterText ?: "No text available",
                            onWordLongPress = ::handleWordLongPress
                        )

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }

        VerticalScrollbar(
            scrollState = scrollState,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .padding(end = 4.dp)
        )

        if (showDictionaryPopup) {
            DictionaryPopup(
                word = selectedWord,
                dictionaryResult = dictionaryResult,
                onDismiss = { showDictionaryPopup = false }
            )
        }
    }
}
