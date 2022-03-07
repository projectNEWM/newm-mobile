package io.projectnewm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.projectnewm.theme.NewmMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import io.projectnewm.screens.Screen
import io.projectnewm.screens.login.LoginScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewmMobileTheme {
                WelcomeToNewm()
            }
        }
    }
}

@Composable
fun WelcomeToNewm() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onSignInSubmitted = { _, _ ->
                navController.popBackStack()
                navController.navigate(Screen.HomeRoot.route)
            })
        }
        composable(Screen.HomeRoot.route) {
            NewmApp()
        }
    }
}