package com.example.fugitive.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fugitive.components.selectors.FontSelector
import com.example.fugitive.components.selectors.FontSizeSelector
import com.example.fugitive.components.selectors.ReaderThemeSelector
import com.example.fugitive.components.selectors.ThemeSelector
import com.example.fugitive.components.button.BackButton
import com.example.fugitive.components.layout.ScreenTitle
import com.example.fugitive.components.layout.SectionHeader
import com.example.fugitive.ui.theme.FugitiveColors
import com.example.fugitive.viewmodels.SettingsViewModel

@Composable
fun PreferencesScreen(navController: NavController, settingsViewModel: SettingsViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FugitiveColors.background)
            .padding(WindowInsets.statusBars.asPaddingValues()) // Prevents status bar overlap
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton { navController.popBackStack() }
                Spacer(modifier = Modifier.width(15.dp))
                ScreenTitle("Preferences")
            }

            SectionHeader("App Customization")
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Theme",
                style = MaterialTheme.typography.bodyMedium,
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(20.dp))

            ThemeSelector(settingsViewModel)
            Spacer(modifier = Modifier.height(30.dp))

            SectionHeader("Reading Experience")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Font Size",
                style = MaterialTheme.typography.bodyMedium,
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(20.dp))
            FontSizeSelector(settingsViewModel)

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Font",
                style = MaterialTheme.typography.bodyMedium,
                color = FugitiveColors.heading
            )
            Spacer(modifier = Modifier.height(20.dp))

            FontSelector(settingsViewModel)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Reader Theme",
                style = MaterialTheme.typography.bodyMedium,
                color = FugitiveColors.heading
            )

            Spacer(modifier = Modifier.height(20.dp))

            ReaderThemeSelector(settingsViewModel)

            Spacer(modifier = Modifier.height(30.dp))

            SectionHeader("General Settings")
            Spacer(modifier = Modifier.height(20.dp))



            Button(onClick = { /* Do something */ }) {
                Text(text = "Refresh")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

