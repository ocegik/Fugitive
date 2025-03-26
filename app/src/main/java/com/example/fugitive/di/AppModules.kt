package com.example.fugitive.di

import androidx.room.Room
import com.example.fugitive.data.local.AppDatabase
import com.example.fugitive.data.local.UserDao
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.data.remote.FirebaseMessagingService
import com.example.fugitive.data.remote.FirestoreService
import com.example.fugitive.repository.UserRepository
import com.example.fugitive.session.UserSessionManager
import com.example.fugitive.viewmodel.AuthViewModel
import com.example.fugitive.viewmodel.BookViewModel
import com.example.fugitive.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Network Services Module
val networkModule = module {
    single { FirebaseAuth.getInstance() }  // ✅ Provide FirebaseAuth
    single { FirebaseAuthService(get<FirebaseAuth>()) }  // ✅ Pass FirebaseAuth if needed
    single { FirebaseMessagingService() }
    single { FirestoreService(get<FirebaseFirestore>()) }
    single { FirebaseFirestore.getInstance() }  // ✅ Fix: Pass required dependencies
}

// Repository Module
val repositoryModule = module {
    single { UserRepository(get<UserDao>(), get<FirestoreService>(), get<FirebaseAuthService>(), get<UserSessionManager>()) }
}

// ViewModel Module
val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { BookViewModel(get(), get()) }
    viewModel { UserViewModel(get()) }
}



val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "fugitive_db").build()
    }

    single { get<AppDatabase>().userDao() }  // ✅ Provides UserDao
}

val sessionModule = module {
    single { UserSessionManager(get(), get<FirebaseAuth>(), get<UserDao>()) }
}

// Combine all modules into one list
val appModules = listOf(databaseModule, sessionModule, networkModule, repositoryModule, viewModelModule)