package io.newm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.android.recaptcha.Recaptcha
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.LoginScreen
import io.newm.feature.login.screen.LoginScreenUi
import io.newm.feature.login.screen.ResetPasswordScreen
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.feature.login.screen.createaccount.CreateAccountScreen
import io.newm.feature.login.screen.createaccount.CreateAccountScreenPresenter
import io.newm.feature.login.screen.createaccount.CreateAccountUi
import io.newm.feature.login.screen.createaccount.CreateAccountUiState
import io.newm.feature.login.screen.login.LoginScreenPresenter
import io.newm.feature.login.screen.login.LoginScreenUiState
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenPresenter
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenUi
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenUiState
import io.newm.feature.login.screen.welcome.WelcomeScreenPresenter
import io.newm.feature.login.screen.welcome.WelcomeScreenUi
import io.newm.feature.login.screen.welcome.WelcomeScreenUiState
import io.newm.screens.Screen.LoginLandingScreen
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.utils.ui
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : ComponentActivity() {

    private val recaptchaClientProvider: RecaptchaClientProvider by inject()
    private val buildConfig: NewmSharedBuildConfig by inject()

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
                is LoginScreen -> inject<LoginScreenPresenter> { parametersOf(navigator) }.value
                is ResetPasswordScreen -> inject<ResetPasswordScreenPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

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

                is LoginScreen -> ui<LoginScreenUiState> { state, modifier ->
                    LoginScreenUi().Content(state, modifier)
                }

                is ResetPasswordScreen -> ui<ResetPasswordScreenUiState> { state, modifier ->
                    ResetPasswordScreenUi().Content(state = state, modifier = modifier)
                }

                else -> null
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        initializeRecaptchaClient()
        setContent {
            NewmTheme(darkTheme = true) {
                CircuitDependencies {
                    WelcomeToNewm(::launchHomeActivity)
                }
            }
        }
    }

    @Composable
    fun CircuitDependencies(
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

    private fun initializeRecaptchaClient() {
        lifecycleScope.launch {
            Recaptcha.getClient(application, buildConfig.recaptchaSiteKey, timeout = 20000L)
                .onSuccess { client ->
                    recaptchaClientProvider.setRecaptchaClient(client)
                }
                .onFailure { exception ->
                    // Handle communication errors ...
                    // See "Handle communication errors" section
                    Log.e("LoginActivity", "Setup failed", exception)
                }
        }
    }
}

@Composable
fun WelcomeToNewm(
    onStartHomeActivity: () -> Unit
) {
    val context = LocalContext.current

    val backstack = rememberSaveableBackStack { push(LoginLandingScreen) }
    val circuitNavigator = rememberCircuitNavigator(backstack)
    val newmNavigator =
        rememberNewmNavigator(circuitNavigator, onStartHomeActivity, launchBrowser = { url ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        })

    NavigableCircuitContent(newmNavigator, backstack)
}

