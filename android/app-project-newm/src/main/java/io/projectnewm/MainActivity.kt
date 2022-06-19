package io.projectnewm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.projectnewm.core.theme.NewmMobileTheme
import io.projectnewm.feature.login.screen.*
import io.projectnewm.screens.Screen

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
fun WelcomeToNewm(signupViewModel: SignupViewModel = org.koin.androidx.compose.get()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(onUserLoggedIn = { ->
                navController.navigate(Screen.HomeRoot.route) {
                    popUpTo(Screen.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }, onSignupClick = {
                navController.navigate(Screen.Signup.route)
            })
        }
        composable(Screen.Signup.route) {
            SignUpScreen(
                onUserLoggedIn = {
                    navController.navigate(Screen.HomeRoot.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onVerification = {
                    navController.navigate(Screen.VerificationCode.route)
                },
                signupViewModel
            )
        }
        composable(Screen.VerificationCode.route) {
            VerificationScreen(signupViewModel) {
                navController.navigate(Screen.HomeRoot.route) {
                    popUpTo(Screen.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.HomeRoot.route) {
            NewmApp()
        }
    }
}