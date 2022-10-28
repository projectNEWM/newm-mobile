package io.projectnewm

import android.content.Intent
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
    signupViewModel: SignupViewModel = org.koin.androidx.compose.get()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(onUserLoggedIn = onStartHomeActivity, onSignupClick = {
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