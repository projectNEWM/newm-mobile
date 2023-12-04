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
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.LoginScreenUi
import io.newm.feature.login.screen.createaccount.CreateAccountScreen
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import io.newm.feature.login.screen.createaccount.CreateAccountUi
import io.newm.feature.login.screen.createaccount.CreateAccountUiState
import io.newm.feature.login.screen.welcome.WelcomeScreenPresenter
import io.newm.feature.login.screen.welcome.WelcomeScreenUi
import io.newm.feature.login.screen.welcome.WelcomeScreenUiState
import io.newm.screens.Screen
import io.newm.screens.Screen.LoginLandingScreen
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : ComponentActivity() {

    // TODO inject
    private val circuit: Circuit = Circuit.Builder()
        .addPresenterFactory(buildPresenterFactory())
        .addUiFactory(buildUiFactory())
        .build()

    private fun buildPresenterFactory(): Presenter.Factory =
        Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is CreateAccountScreen -> inject<CreateAccountScreenPresenter> { parametersOf(::launchHomeActivity) }.value
                is LoginLandingScreen -> inject<WelcomeScreenPresenter> { parametersOf(navigator) }.value
                else -> null
            }
        }

    private fun buildUiFactory(): Ui.Factory =
        Ui.Factory { screen, _ ->
            when (screen) {
                is CreateAccountScreen -> ui<CreateAccountUiState> { state, modifier ->
                    CreateAccountUi(state, modifier)
                }

                is LoginLandingScreen -> ui<WelcomeScreenUiState> { state, modifier ->
                    WelcomeScreenUi(modifier, state)
                }

                else -> null
            }
        }

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
        CircuitCompositionLocals(circuit) {
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
    NavHost(navController = navController, startDestination = LoginLandingScreen.route) {
        composable(LoginLandingScreen.route) {
            val backstack = rememberSaveableBackStack { push(LoginLandingScreen) }
            val circuitNavigator = rememberCircuitNavigator(backstack)
            val newmNavigator =
                rememberNewmNavigator(circuitNavigator, navController, onStartHomeActivity)
            NavigableCircuitContent(newmNavigator, backstack)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreenUi(onUserLoggedIn = onStartHomeActivity)
        }
        composable(Screen.Signup.route) {
            CircuitContent(screen = CreateAccountScreen)
        }
    }
}

