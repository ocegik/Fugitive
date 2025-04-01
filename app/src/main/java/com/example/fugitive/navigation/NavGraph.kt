package com.example.fugitive.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fugitive.screens.auth.onboarding.WelcomeScreen
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
import com.example.fugitive.screens.auth.onboarding.OnBoardingIntroScreen
import com.example.fugitive.screens.auth.onboarding.OnBoardingFeaturesScreen
import com.example.fugitive.screens.auth.onboarding.OnBoardingFinalScreen
import com.example.fugitive.screens.auth.onboarding.PfpSelectScreen
import com.example.fugitive.screens.settings.AboutUsScreen
import com.example.fugitive.screens.settings.EditProfileScreen
import com.example.fugitive.screens.settings.HelpScreen
import com.example.fugitive.screens.settings.MyStatsScreen
import com.example.fugitive.screens.settings.PreferencesScreen
import com.example.fugitive.screens.settings.SavedQuotesScreen
import com.example.fugitive.viewmodels.AuthViewModel
import com.example.fugitive.viewmodels.BookViewModel
import com.example.fugitive.viewmodels.SettingsViewModel
import com.example.fugitive.viewmodels.UserViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(navController: NavHostController) {
    val authService: FirebaseAuthService = koinInject()
    val startDestination = if (authService.isUserLoggedIn()) Screen.Home.route else Screen.Welcome.route

    val authViewModel: AuthViewModel = koinViewModel()
    val userViewModel: UserViewModel = koinViewModel()
    val bookViewModel: BookViewModel = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()

    NavHost(navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(700)) }, // Fade-in transition
        exitTransition = { fadeOut(animationSpec = tween(300)) }, // Fade-out transition
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { fadeOut(animationSpec = tween(500)) })
    {

        composable(Screen.Welcome.route) { WelcomeScreen(navController) }

        composable(Screen.Login.route) { LoginScreen(navController, authViewModel) }

        composable(Screen.SignUp.route) { SignUpScreen(navController, authViewModel) }

        composable(Screen.Home.route) { HomeScreen(navController, userViewModel, bookViewModel) }

        composable(Screen.BookDetail.route) { BookDetailsScreen(navController) }

        composable(Screen.Settings.route) { SettingsScreen(navController) }

        composable(Screen.BookReader.route) { BookReaderScreen(navController) }

        composable(Screen.Notification.route) { NotificationsScreen(navController) }

        composable(Screen.Profile.route) { ProfileScreen(navController, userViewModel) }

        composable(Screen.Terms.route) { TermsScreen(navController) }

        composable(Screen.ForgotPass.route) { ForgotPassScreen(navController) }

        composable(Screen.ResetPass.route) { ResetPassScreen(navController) }

        composable(Screen.Search.route) { SearchScreen(navController) }

        composable(Screen.MyStats.route) { MyStatsScreen(navController) }

        composable(Screen.AboutUs.route) { AboutUsScreen(navController) }

        composable(Screen.SavedQuotes.route) { SavedQuotesScreen(navController) }

        composable(Screen.EditProfile.route) { EditProfileScreen(navController) }

        composable(Screen.Help.route) { HelpScreen(navController) }

        composable(Screen.Preferences.route) { PreferencesScreen(navController, settingsViewModel) }

        composable(Screen.OnBoardingIntro.route) { OnBoardingIntroScreen(navController) }

        composable(Screen.OnBoardingFeatures.route) { OnBoardingFeaturesScreen(navController) }

        composable(Screen.OnBoardingFinal.route) { OnBoardingFinalScreen(navController) }

        composable(Screen.PfpSelect.route) { PfpSelectScreen(navController, userViewModel) }

    }

}


