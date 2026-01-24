package io.newm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.lifecycleRetainedStateRegistry
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.core.ui.LocalSnackBarHostState
import io.newm.core.ui.theme.NewmTheme
import io.newm.screens.forceupdate.ForceAppUpdateState
import io.newm.screens.forceupdate.ForceAppUpdateUi
import io.newm.screens.forceupdate.openAppPlayStore
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.auth.login.LoginPresenter
import io.newm.sharedfeatures.screens.auth.login.LoginUi
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreen
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreenPresenter
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreenUi
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreenUiState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountScreen
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountScreenPresenter
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUi
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState
import io.newm.sharedfeatures.screens.auth.welcome.WelcomePresenter
import io.newm.sharedfeatures.screens.auth.welcome.WelcomeUi
import io.newm.sharedfeatures.screens.devmenu.DevMenuPresenter
import io.newm.sharedfeatures.screens.devmenu.DevMenuUi
import io.newm.sharedfeatures.screens.devmenu.featureflaglist.FeatureFlagsListPresenter
import io.newm.sharedfeatures.screens.devmenu.featureflaglist.FeatureFlagsListUi
import io.newm.utils.DynamicStatusBarSideEffect
import io.newm.utils.ForceAppUpdateViewModel
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : ComponentActivity() {
    private val logger: NewmAppLogger by inject()
    private val config: io.newm.shared.config.NewmSharedBuildConfig by inject()
    private val eventLogger: NewmAppEventLogger by inject()
    private val forceAppUpdateViewModel: ForceAppUpdateViewModel by inject()

    // TODO inject
    private val circuit: Circuit =
        Circuit
            .Builder()
            .addPresenterFactory(buildPresenterFactory())
            .addUiFactory(buildUiFactory())
            .build()

    private fun buildPresenterFactory(): Presenter.Factory =
        Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is CreateAccountScreen -> {
                    inject<CreateAccountScreenPresenter> { parametersOf(navigator) }.value
                }

                is WelcomeScreen -> {
                    inject<WelcomePresenter> { parametersOf(navigator) }.value
                }

                is LoginScreen -> {
                    inject<LoginPresenter> { parametersOf(navigator) }.value
                }

                is ResetPasswordScreen -> {
                    inject<ResetPasswordScreenPresenter> { parametersOf(screen, navigator) }.value
                }

                is DevMenuMainScreen -> {
                    inject<DevMenuPresenter> { parametersOf(navigator) }.value
                }

                is FeatureFlagsListScreen -> {
                    inject<FeatureFlagsListPresenter> { parametersOf(navigator) }.value
                }

                else -> {
                    null
                }
            }
        }

    private fun buildUiFactory(): Ui.Factory =
        Ui.Factory { screen, _ ->
            when (screen) {
                is CreateAccountScreen -> {
                    ui<CreateAccountUiState> { state, modifier -> CreateAccountUi(state, modifier) }
                }

                is WelcomeScreen -> {
                    ui<WelcomeScreen.UiState> { state, modifier -> WelcomeUi(state, modifier) }
                }

                is LoginScreen -> {
                    ui<LoginScreen.UiState> { state, modifier -> LoginUi(state, modifier) }
                }

                is ResetPasswordScreen -> {
                    ui<ResetPasswordScreenUiState> { state, modifier ->
                        ResetPasswordScreenUi(state, eventLogger, modifier)
                    }
                }

                is DevMenuMainScreen -> {
                    ui<DevMenuMainScreen.UiState> { state, modifier -> DevMenuUi(state, modifier) }
                }

                is FeatureFlagsListScreen -> {
                    ui<FeatureFlagsListScreen.UiState> { state, modifier ->
                        FeatureFlagsListUi(state, modifier)
                    }
                }

                else -> {
                    null
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewmTheme(darkTheme = true) {
                DynamicStatusBarSideEffect(darkTheme = true)
                CircuitDependencies {
                    val updateRequired by
                        forceAppUpdateViewModel.updateRequiredState.collectAsState()

                    if (updateRequired) {
                        ForceAppUpdateUi(
                            ForceAppUpdateState.Content(
                                eventSink = {
                                    eventLogger.logClickEvent(
                                        AppScreens.ForceUpdateScreen.UPDATE_BUTTON,
                                    )
                                    openAppPlayStore()
                                },
                            ),
                            eventLogger,
                        )
                    } else {
                        WelcomeToNewm(config, logger, eventLogger, ::launchHomeActivity)
                    }
                }
            }
        }
    }

    @Composable
    fun CircuitDependencies(content: @Composable () -> Unit) {
        CircuitCompositionLocals(circuit) {
            CompositionLocalProvider(
                LocalRetainedStateRegistry provides lifecycleRetainedStateRegistry(),
            ) {
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
    config: io.newm.shared.config.NewmSharedBuildConfig,
    logger: NewmAppLogger,
    eventLogger: NewmAppEventLogger,
    onStartHomeActivity: () -> Unit,
) {
    val context = LocalContext.current

    val backstack = rememberSaveableBackStack(WelcomeScreen)
    val circuitNavigator = rememberCircuitNavigator(backstack)
    val newmNavigator =
        rememberNewmNavigator(
            circuitNavigator,
            logger,
            onStartHomeActivity,
            launchBrowser = { url ->
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            },
            eventLogger = eventLogger,
        )

    val snackbarHostState = remember { SnackbarHostState() }
    io.newm.sharedfeatures.screens.devmenu.DebugOverlay(
        buildConfig = config,
        onOpenDebugMenu = { circuitNavigator.goTo(DevMenuMainScreen) },
    ) {
        Scaffold(scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)) { contentPadding ->
            CompositionLocalProvider(LocalSnackBarHostState provides snackbarHostState) {
                NavigableCircuitContent(
                    modifier = Modifier.padding(contentPadding),
                    navigator = newmNavigator,
                    backStack = backstack,
                )
            }
        }
    }
}
