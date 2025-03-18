package com.fugitive.di

import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirebaseMessagingService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.repository.UserRepository
import com.example.fugitive.viewmodel.AuthViewModel
import com.example.fugitive.viewmodel.BookViewModel
import com.example.fugitive.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { FirebaseAuthService() }
    single { FirebaseMessagingService() }
    single { FirestoreService() }
}

val repositoryModule = module {
    single { UserRepository() } // Pass FirestoreService or required dependencies
}

val viewModelModule = module {
    viewModel { AuthViewModel() }
    viewModel { BookViewModel() }
    viewModel { UserViewModel() }
}

// Combine all modules into one list
val appModules = listOf(networkModule, repositoryModule, viewModelModule)