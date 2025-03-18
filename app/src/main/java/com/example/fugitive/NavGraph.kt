package com.example.fugitive

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fugitive.screens.auth.WelcomeScreen
import com.example.fugitive.screens.auth.login.LoginScreen
import com.example.fugitive.screens.auth.signup.SignUpScreen
import com.example.fugitive.screens.home.HomeScreen
import com.example.fugitive.screens.home.BookDetailsScreen
import com.example.fugitive.screens.home.BookReaderScreen
import com.example.fugitive.screens.home.NotificationsScreen
import com.example.fugitive.screens.settings.ProfileScreen
import com.example.fugitive.screens.settings.SettingsScreen
import com.example.fugitive.screens.misc.TermsScreen
import com.example.fugitive.screens.auth.login.ForgotPassScreen
import com.example.fugitive.screens.auth.login.ResetPassScreen
import com.example.fugitive.screens.find.SearchScreen
import com.example.fugitive.data.remote.FirebaseAuthService
import com.example.fugitive.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(navController: NavHostController) {
    val authService: FirebaseAuthService = koinInject()
    val startDestination = if (authService.isUserLoggedIn()) Screen.Home.route else Screen.Welcome.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            LoginScreen(navController, authViewModel)
        }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.BookDetail.route) { BookDetailsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.BookReader.route) { BookReaderScreen(navController) }
        composable(Screen.Notification.route) { NotificationsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Terms.route) { TermsScreen(navController) }
        composable(Screen.ForgotPass.route){ ForgotPassScreen(navController) }

        composable(Screen.ResetPass.route) { ResetPassScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }

    }
}
