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
import com.example.fugitive.screens.SettingsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.BookDetail.route) { BookDetailsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        // Add more screens here as needed
    }
}
