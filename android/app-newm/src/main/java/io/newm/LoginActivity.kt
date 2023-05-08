package io.newm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.*
import io.newm.screens.Screen

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewmTheme(darkTheme = true) {
                WelcomeToNewm(::launchHomeActivity)
            }
        }
    }

    private fun launchHomeActivity() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
}

@Composable
fun WelcomeToNewm(
    onStartHomeActivity: () -> Unit,
    signupViewModel: CreateAccountViewModel = org.koin.androidx.compose.get(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.LoginLandingScreen.route) {
        composable(Screen.LoginLandingScreen.route) {
            WelcomeScreen(
                onLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onCreateAccount = {
                    navController.navigate(Screen.Signup.route)
                },
                onContinueAsGuest = onStartHomeActivity
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onUserLoggedIn = onStartHomeActivity
            )
        }
        composable(Screen.Signup.route) {
            CreateAccountScreen(
                viewModel = signupViewModel,
                onUserLoggedIn = onStartHomeActivity,
                onNext = {
                    navController.navigate(Screen.WhatShouldWeCallYou.route)
                },
            )
        }
        composable(Screen.WhatShouldWeCallYou.route) {
            WhatShouldWeCallYouScreen(
                viewModel = signupViewModel,
                done = { navController.navigate(Screen.VerificationCode.route) }
            )
        }
        composable(Screen.VerificationCode.route) {
            EnterVerificationCodeScreen(
                viewModel = signupViewModel,
                onVerificationComplete = onStartHomeActivity
            )
        }
    }
}