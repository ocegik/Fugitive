package com.example.fugitive

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fugitive.screens.WelcomeScreen
import com.example.fugitive.screens.LoginScreen
import com.example.fugitive.screens.SignUpScreen
import com.example.fugitive.screens.HomeScreen
import com.example.fugitive.screens.BookDetailsScreen
import com.example.fugitive.screens.BookReaderScreen
import com.example.fugitive.screens.NotificationsScreen
import com.example.fugitive.screens.ProfileScreen
import com.example.fugitive.screens.SettingsScreen
import com.example.fugitive.screens.TermsScreen
import com.example.fugitive.screens.ForgotPassScreen
import com.example.fugitive.screens.VerifyCodeScreen
import com.example.fugitive.screens.ResetPassScreen

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
    }
}
