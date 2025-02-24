package com.example.fugitive

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fugitive.screens.auth.WelcomeScreen
import com.example.fugitive.screens.auth.LoginScreen
import com.example.fugitive.screens.auth.SignUpScreen
import com.example.fugitive.screens.home.HomeScreen
import com.example.fugitive.screens.home.BookDetailsScreen
import com.example.fugitive.screens.home.BookReaderScreen
import com.example.fugitive.screens.home.NotificationsScreen
import com.example.fugitive.screens.settings.ProfileScreen
import com.example.fugitive.screens.settings.SettingsScreen
import com.example.fugitive.screens.misc.TermsScreen
import com.example.fugitive.screens.auth.ForgotPassScreen
import com.example.fugitive.screens.misc.VerifyCodeScreen
import com.example.fugitive.screens.auth.ResetPassScreen
import com.example.fugitive.screens.find.SearchScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.BookDetail.route) { BookDetailsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.BookReader.route) { BookReaderScreen(navController) }
        composable(Screen.Notification.route) { NotificationsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Terms.route) { TermsScreen(navController) }
        composable(Screen.ForgotPass.route){ ForgotPassScreen(navController) }
        composable(Screen.VerifyCode.route) { VerifyCodeScreen(navController) }
        composable(Screen.ResetPass.route) { ResetPassScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
    }
}
