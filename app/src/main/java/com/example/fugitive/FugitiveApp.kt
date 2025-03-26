package com.example.fugitive

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.fugitive.di.appModules

class FugitiveApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FugitiveApp)
            modules(appModules)
        }
    }
}