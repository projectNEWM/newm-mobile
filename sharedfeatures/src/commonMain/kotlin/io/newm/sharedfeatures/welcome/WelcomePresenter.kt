package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.sharedfeatures.login.GoogleSignInLauncher
import io.newm.sharedfeatures.login.GoogleUser
import io.newm.sharedfeatures.login.RecaptchaManager
import io.newm.sharedfeatures.login.rememberGoogleSignInLauncher
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias GoogleSignInLauncherFactory = @Composable (onResult: (Result<GoogleUser>) -> Unit) -> GoogleSignInLauncher

@Inject
class WelcomePresenter(
    @Assisted private val navigator: Navigator,
    private val loginUseCase: () -> LoginUseCase,
    private val recaptchaManager: RecaptchaManager,
    private val logger: NewmAppLogger,
    private val analyticsTracker: NewmAppEventLogger,
    private val googleSignInLauncherFactory: GoogleSignInLauncherFactory = { rememberGoogleSignInLauncher(it) }
) : Presenter<UiState> {

    @Composable
    override fun present(): UiState {
        val coroutineScope = rememberCoroutineScope()
        var errorMessage by remember { mutableStateOf<String?>(null) }

        val googleSignInLauncher = googleSignInLauncherFactory { result ->
            result.onSuccess { googleUser ->
                coroutineScope.launch {
                    try {
                        recaptchaManager.executeLogin().onSuccess { recaptchaToken ->
                            loginUseCase().logInWithGoogle(
                                idToken = googleUser.idToken,
                                humanVerificationCode = recaptchaToken
                            )
                            navigator.goTo(HomeScreen)
                        }.onFailure { e ->
                            logger.error("WelcomePresenter", "Recaptcha failed during Google Sign In", e)
                            errorMessage = e.message ?: "Recaptcha failed"
                        }
                    } catch (e: Exception) {
                        logger.error("WelcomePresenter", "Google Sign In failed", e)
                        errorMessage = e.message ?: "Login failed"
                    }
                }
            }.onFailure { e ->
                logger.error("WelcomePresenter", "Google Sign In failed", e)
                errorMessage = e.message ?: "Google Sign In failed"
            }
        }

        return UiState.Content(
            errorMessage = errorMessage,
            onEvent = { event ->
                errorMessage = null // Clear error on new event
                when (event) {
                    WelcomeScreen.UiEvent.OnBack -> {
                        navigator.pop()
                    }

                    WelcomeScreen.UiEvent.OnDevMenu -> {
                        navigator.goTo(DevMenuMainScreen)
                    }

                    WelcomeScreen.UiEvent.OnLogin -> {
                        analyticsTracker.logClickEvent(AppScreens.WelcomeScreen.LOGIN_WITH_EMAIL_BUTTON)
                        navigator.goTo(LoginScreen)
                    }

                    WelcomeScreen.UiEvent.OnGoogleSignIn -> {
                        analyticsTracker.logClickEvent(AppScreens.WelcomeScreen.LOGIN_WITH_GOOGLE_BUTTON)
                        googleSignInLauncher.launch()
                    }
                }
            },
        )
    }
}

@Inject
class WelcomePresenterFactory(
    private val presenter: (Navigator) -> WelcomePresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is WelcomeScreen -> presenter(navigator)
            else -> null
        }
    }
}