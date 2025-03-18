package com.example.fugitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fugitive.ui.theme.FugitiveTheme
import com.fugitive.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin{
            androidContext(this@MainActivity)
            modules(appModules)
        }
        enableEdgeToEdge()
        setContent {
            FugitiveTheme {
                MyApp()
            }
        }
    }
}

