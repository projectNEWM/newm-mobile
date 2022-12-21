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
import io.newm.core.theme.NewmMobileTheme
import io.newm.feature.login.screen.*
import io.newm.screens.Screen

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewmMobileTheme {
                WelcomeToNewm(::launchHomeActivity)
            }
        }
    }

    private fun launchHomeActivity() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }
}

@Composable
fun WelcomeToNewm(
    onStartHomeActivity: () -> Unit,
    signupViewModel: SignupViewModel = org.koin.androidx.compose.get(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onUserLoggedIn = onStartHomeActivity,
                onSignupClick = {
                    navController.navigate(Screen.Signup.route)
                })
        }
        composable(Screen.Signup.route) {
            SignUpScreen(
                onUserLoggedIn = onStartHomeActivity, onVerification = {
                    navController.navigate(Screen.VerificationCode.route)
                }, signupViewModel
            )
        }
        composable(Screen.VerificationCode.route) {
            VerificationScreen(signupViewModel, onVerificationComplete = onStartHomeActivity)
        }
    }
}