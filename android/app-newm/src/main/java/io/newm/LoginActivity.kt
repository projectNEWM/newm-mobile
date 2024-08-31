package io.newm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import io.newm.screens.Screen.Welcome
import io.newm.screens.forceupdate.ForceAppUpdateState
import io.newm.screens.forceupdate.ForceAppUpdateUi
import io.newm.screens.forceupdate.openAppPlayStore
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.utils.ForceAppUpdateViewModel
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : ComponentActivity() {

    private val logger: NewmAppLogger by inject()
    private val eventLogger: NewmAppEventLogger by inject()
    private val forceAppUpdateViewModel: ForceAppUpdateViewModel by inject()

    // TODO inject
    private val circuit: Circuit = Circuit.Builder()
        .addPresenterFactory(buildPresenterFactory())
        .addUiFactory(buildUiFactory())
        .build()

    private fun buildPresenterFactory(): Presenter.Factory =
        Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is CreateAccountScreen -> inject<CreateAccountScreenPresenter> { parametersOf(::launchHomeActivity) }.value
                is Welcome -> inject<WelcomeScreenPresenter> { parametersOf(navigator) }.value
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

                is Welcome -> ui<WelcomeScreenUiState> { state, modifier ->
                    WelcomeScreenUi(modifier, state, eventLogger)
                }

                is LoginScreen -> ui<LoginScreenUiState> { state, modifier ->
                    LoginScreenUi().Content(state, modifier)
                }

                is ResetPasswordScreen -> ui<ResetPasswordScreenUiState> { state, modifier ->
                    ResetPasswordScreenUi(eventLogger).Content(state = state, modifier = modifier)
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
                    val updateRequired by forceAppUpdateViewModel.updateRequiredState.collectAsState()

                    if (updateRequired) {
                        ForceAppUpdateUi(
                            ForceAppUpdateState.Content(eventSink = {
                                eventLogger.logClickEvent("Update Now")
                                openAppPlayStore()
                            }),
                            eventLogger
                        )
                    } else {
                        WelcomeToNewm(logger, eventLogger,  ::launchHomeActivity)
                    }
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
}

@Composable
fun WelcomeToNewm(
    logger: NewmAppLogger,
    eventLogger: NewmAppEventLogger,
    onStartHomeActivity: () -> Unit,
) {
    val context = LocalContext.current

    val backstack = rememberSaveableBackStack { push(Welcome) }
    val circuitNavigator = rememberCircuitNavigator(backstack)
    val newmNavigator =
        rememberNewmNavigator(
            circuitNavigator,
            logger,
            onStartHomeActivity,
            launchBrowser = { url ->
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            },
            eventLogger = eventLogger)

    NavigableCircuitContent(newmNavigator, backstack)
}

