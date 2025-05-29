package com.example.fugitive.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.fugitive.data.local.database.AppDatabase
import com.example.fugitive.data.local.preferences.AuthPreferences
import com.example.fugitive.data.local.database.UserDao
import com.example.fugitive.data.local.preferences.UserPreferences
import com.example.fugitive.data.remote.firebase.FirebaseAuthService
import com.example.fugitive.data.remote.firebase.FirestoreService
import com.example.fugitive.data.repository.UserRepository
import com.example.fugitive.data.repository.AuthRepository
import com.example.fugitive.data.repository.BookRepository
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.viewmodels.BookViewModel
import com.example.fugitive.viewmodels.DictionaryViewModel
import com.example.fugitive.viewmodels.SettingsViewModel
import com.example.fugitive.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val firebaseModule = module {
    single { FirebaseAuthService(get<FirebaseAuth>()) } // ✅ Provide FirebaseAuthService
    single { FirebaseAuth.getInstance() }  // ✅ Ensure FirebaseAuth is provided
    single { FirestoreService(get<FirebaseFirestore>()) } // ✅ Ensure FirestoreService is provided
    single { FirebaseFirestore.getInstance() }
}


val preferenceModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }
    single { UserPreferences(get()) }
    single { AuthPreferences(get()) }
}


// Repository Module
val repositoryModule = module {
    single {
        UserRepository(
            get<UserDao>(),
            get<FirestoreService>(),
            get<AuthPreferences>()
        )
    }
    single {
        AuthRepository(
            get<UserDao>(),
            get<FirebaseAuthService>(),
            get<UserRepository>(),
            get<AuthPreferences>()
        )
    }
    single {
        BookRepository(get<FirestoreService>())
    }
}

// ViewModel Module
val viewModelModule = module {
    viewModel { AuthViewModel(get(), get(), get()) } // ViewModel for handling authentication
    viewModel { BookViewModel(get()) } // ViewModel for handling book-related functionality
    viewModel { UserViewModel(get()) }  // ViewModel for handling user profile data (user data like name, email, etc.)
    viewModel { SettingsViewModel(get()) }  // ViewModel for handling user preferences (themes, fonts, etc.)
    viewModel { DictionaryViewModel() }
}



val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "fugitive_db")
            .fallbackToDestructiveMigration()  // WARNING: Deletes all user data!
            .build()
    }

    single { get<AppDatabase>().userDao() }  // ✅ Provides UserDao
}


// Combine all modules into one list
val appModules = listOf(
    databaseModule,
    repositoryModule,
    viewModelModule,
    preferenceModule,
    firebaseModule
)