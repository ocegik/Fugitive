package com.example.fugitive


sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object Home : Screen("home")
    data object BookDetail : Screen("bookDetail")
    data object Settings : Screen("settings")
    data object BookReader : Screen("bookReader")
    data object Notification : Screen("notification")
    data object Profile: Screen("profile")
    data object Terms : Screen("terms")
    data object ForgotPass : Screen("ForgotPass")
    data object VerifyCode : Screen("VerifyCode")
    data object ResetPass : Screen("ResetPass")
    data object Search : Screen("Search")

}