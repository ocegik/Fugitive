package com.example.fugitive.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.fugitive.R
import com.example.fugitive.Screen
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.BookDetailsRow
import com.example.fugitive.components.button.FugitivePrimaryButton
import com.example.fugitive.components.HeadingText
import com.example.fugitive.ui.theme.FugitiveColors


@Composable
fun BookDetailsScreen(
    navController: NavController,
) {
    val backgroundColor = FugitiveColors.background
    val isBookmarked = remember { mutableStateOf(false) }
    val title = "Tales Under a Purple Sky"
    val author = "By Samira Hadid"
    val chapters = 12
    val year = 2025
    val wordCount = 89901
    val description = "A historical epic of warring kingdoms, fragile alliances, and an ancient prophecy that shapes the fate of empires, where rulers grapple with betrayal, destiny unfolds in unexpected ways, and the balance of power teeters on the edge of chaos."

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.book_cover_placeholder),
            contentDescription = "Background Book Cover",
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp)
                .blur(50.dp)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent,backgroundColor.copy(alpha = 0.8f), backgroundColor),
                            startY = size.height * 0.3f, // Start gradient halfway down
                            endY = size.height
                        ),
                        size = size
                    )
                },
            contentScale = ContentScale.Crop

        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents overlapping with status bar
            .padding(16.dp)) // General padding)
        {
            BackButton(
                modifier = Modifier
                    .size(50.dp)  // Bigger and easier to tap
                    .align(Alignment.TopStart) // Ensures it's on the top-left
                    .padding(start = 16.dp, top = 16.dp)
                    .zIndex(2f) // Makes sure it stays on top
            ) {
                navController.popBackStack()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.book_cover_placeholder),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))

            )
            Spacer(modifier = Modifier.height(60.dp))

            HeadingText(title, fontSize = 24)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = author, color = FugitiveColors.heading, fontSize = 16.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))

            BookDetailsRow(chapters = chapters, year = year, wordCount = wordCount)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = FugitiveColors.heading,
                    lineHeight = 25.sp,
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                //CustomBookmarkButton(isBookmarked)
                FugitivePrimaryButton("Start Reading", onClick = { navController.navigate(Screen.BookReader.route) })

            }


            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}
