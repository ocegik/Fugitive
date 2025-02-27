package com.example.fugitive

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object BookDetail : Screen("bookDetail")
    object Settings : Screen("settings")
    object BookReader : Screen("bookReader")
    object Notification : Screen("notification")
    object Profile: Screen("profile")
    object Terms : Screen("terms")
    object ForgotPass : Screen("ForgotPass")
    object VerifyCode : Screen("VerifyCode")
    object ResetPass : Screen("ResetPass")
    object Search : Screen("Search")
    object VerifyEmail : Screen("VerifyEmail")

}