package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.AppLogger
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.usecases.LoginUseCase
import io.newm.sharedfeatures.login.RecaptchaManager
import io.newm.sharedfeatures.screens.CreateAccountScreen
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.CreateAccountClicked
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnBack
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnDevMenu
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnGoogleSignInClicked
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnLogin
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnPrivacyPolicyClicked
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent.OnTermsOfServiceClicked
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState.Content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class WelcomePresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
    private val loginUseCase: LoginUseCase,
    private val socialLoginManager: SocialLoginManager,
    private val recaptchaManager: RecaptchaManager,
    private val analyticsTracker: NewmAppEventLogger,
    private val logger: NewmAppLogger,
) : Presenter<UiState> {

    private val logTag = "WelcomeScreen"

    @Composable
    override fun present(): UiState {
        val uriHandler = LocalUriHandler.current

        LaunchedEffect(Unit) {
            analyticsTracker.logPageLoad(AppScreens.WelcomeScreen.name)
        }

        val launchGoogleSignIn = rememberGoogleSignInLauncher()

        return Content(
            onEvent = { event ->
                when (event) {
                    CreateAccountClicked -> {
                        analyticsTracker.logClickEvent(AppScreens.WelcomeScreen.CREATE_ACCOUNT_BUTTON)
                        navigator.goTo(CreateAccountScreen)
                    }
                    OnLogin -> {
                        analyticsTracker.logClickEvent(AppScreens.WelcomeScreen.LOGIN_WITH_EMAIL_BUTTON)
                        navigator.goTo(LoginScreen)
                    }
                    OnGoogleSignInClicked -> {
                        analyticsTracker.logClickEvent(AppScreens.WelcomeScreen.LOGIN_WITH_GOOGLE_BUTTON)
                        launchGoogleSignIn()
                    }
                    OnTermsOfServiceClicked -> {
                        analyticsTracker.logClickEvent(AppScreens.AccountScreen.TERMS_AND_CONDITIONS_BUTTON)
                        uriHandler.openUri("https://newm.io/app-tos")
                    }
                    OnPrivacyPolicyClicked -> {
                        analyticsTracker.logClickEvent(AppScreens.AccountScreen.PRIVACY_POLICY_BUTTON)
                        uriHandler.openUri("https://newm.io/app-privacy")
                    }
                    OnDevMenu -> {
                        navigator.goTo(DevMenuMainScreen)
                    }
                    OnBack -> {
                        navigator.pop()
                    }
                }
            },
        )
    }

    @Composable
    private fun rememberGoogleSignInLauncher(): () -> Unit {
        val scope = rememberCoroutineScope()

        return socialLoginManager.rememberGoogleSignInLauncher { result ->
            when (result) {
                is GoogleSignInResult.Success -> {
                    scope.launch {
                        recaptchaManager.executeLogin()
                            .onSuccess { code ->
                                try {
                                    loginUseCase.logInWithGoogle(
                                        idToken = result.idToken,
                                        humanVerificationCode = code
                                    )
                                    navigator.goTo(HomeScreen)
                                } catch (e: Exception) {
                                    logger.error(logTag, "Sign in failed", e)
                                }
                            }
                            .onFailure {
                                logger.error(logTag, "Recaptcha failed", it)
                            }
                    }
                }

                is GoogleSignInResult.Failure -> {
                    logger.error(logTag, "Google sign in failed", result.error)
                }
            }
        }
    }
}

class WelcomePresenterFactory @Inject constructor(
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
