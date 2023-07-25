package io.newm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitConfig
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.*
import io.newm.feature.login.screen.createaccount.CreateAccountScreen
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import io.newm.feature.login.screen.createaccount.CreateAccountUi
import io.newm.feature.login.screen.createaccount.CreateAccountUiState
import io.newm.screens.Screen
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : ComponentActivity() {

    // TODO inject
    private val circuitConfig: CircuitConfig = CircuitConfig.Builder()
        .addPresenterFactory { screen, navigator, _ ->
            when (screen) {
                is CreateAccountScreen -> inject<CreateAccountScreenPresenter> { parametersOf(::launchHomeActivity) }.value
                else -> null
            }
        }
        .addUiFactory { screen, _ ->
            when (screen) {
                is CreateAccountScreen -> ui<CreateAccountUiState> { state, modifier ->
                    CreateAccountUi(state, modifier)
                }

                else -> null
            }
        }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewmTheme(darkTheme = true) {
                CircuitDependencies {
                    WelcomeToNewm(::launchHomeActivity)
                }
            }
        }
    }

    @Composable
    private fun CircuitDependencies(
        content: @Composable () -> Unit
    ) {
        CircuitCompositionLocals(circuitConfig) {
            CompositionLocalProvider(LocalRetainedStateRegistry provides continuityRetainedStateRegistry()) {
                content()
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
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onUserLoggedIn = onStartHomeActivity
            )
        }
        composable(Screen.Signup.route) {
            CircuitContent(screen = CreateAccountScreen)
        }
    }
}
