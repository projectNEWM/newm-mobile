package io.newm.sharedfeatures.screens.auth.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalUriHandler
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.sharedfeatures.fakes.FakeAppLogger
import io.newm.sharedfeatures.fakes.FakeEventLogger
import io.newm.sharedfeatures.fakes.FakeLoginUseCase
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.fakes.FakeRecaptchaManager
import io.newm.sharedfeatures.fakes.FakeSocialLoginManager
import io.newm.sharedfeatures.fakes.FakeUriHandler
import io.newm.sharedfeatures.screens.CreateAccountScreen
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen.UiEvent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class WelcomePresenterTest {
    private val navigator: FakeNavigator = FakeNavigator()
    private val loginUseCase: FakeLoginUseCase = FakeLoginUseCase()
    private val socialLoginManager: FakeSocialLoginManager = FakeSocialLoginManager()
    private val recaptchaManager: FakeRecaptchaManager = FakeRecaptchaManager()
    private val fakeEventLogger: FakeEventLogger = FakeEventLogger()
    private val analyticsTracker: NewmAppEventLogger =
        NewmAppEventLogger().apply { setClientAnalyticsTracker(fakeEventLogger) }
    private val fakeAppLogger: FakeAppLogger = FakeAppLogger()
    private val newmAppLogger: NewmAppLogger =
        NewmAppLogger().apply { setClientLogger(fakeAppLogger) }
    private val uriHandler: FakeUriHandler = FakeUriHandler()

    private fun createPresenter(): Presenter<WelcomeScreen.UiState> {
        val realPresenter =
            WelcomePresenter(
                navigator = navigator,
                loginUseCase = loginUseCase,
                socialLoginManager = socialLoginManager,
                recaptchaManager = recaptchaManager,
                analyticsTracker = analyticsTracker,
                logger = newmAppLogger,
            )
        return object : Presenter<WelcomeScreen.UiState> {
            @Composable
            override fun present(): WelcomeScreen.UiState {
                var state: WelcomeScreen.UiState by remember {
                    mutableStateOf(WelcomeScreen.UiState.Content {})
                }
                CompositionLocalProvider(LocalUriHandler provides uriHandler) {
                    state = realPresenter.present()
                }
                return state
            }
        }
    }

    @Test
    fun `Initialization logs page load`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                awaitItem() // Initial state
                assertThat(fakeEventLogger.pageLoads).isNotEmpty()
                assertThat(fakeEventLogger.pageLoads.first().first)
                    .isEqualTo(AppScreens.WelcomeScreen.name)
            }
        }

    @Test
    fun `CreateAccountClicked navigates to CreateAccountScreen`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.CreateAccountClicked)

                assertThat(fakeEventLogger.clickEvents.last().first)
                    .isEqualTo(AppScreens.WelcomeScreen.CREATE_ACCOUNT_BUTTON)
                assertThat(navigator.goToHistory.last()).isEqualTo(CreateAccountScreen)
            }
        }

    @Test
    fun `OnLogin navigates to LoginScreen`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnLogin)

                assertThat(fakeEventLogger.clickEvents.last().first)
                    .isEqualTo(AppScreens.WelcomeScreen.LOGIN_WITH_EMAIL_BUTTON)
                assertThat(navigator.goToHistory.last()).isEqualTo(LoginScreen)
            }
        }

    @Test
    fun `OnTermsOfServiceClicked opens correct URI`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnTermsOfServiceClicked)

                assertThat(fakeEventLogger.clickEvents.last().first)
                    .isEqualTo(AppScreens.AccountScreen.TERMS_AND_CONDITIONS_BUTTON)
                assertThat(uriHandler.openedUris.last()).isEqualTo("https://newm.io/app-tos")
            }
        }

    @Test
    fun `OnPrivacyPolicyClicked opens correct URI`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnPrivacyPolicyClicked)

                assertThat(fakeEventLogger.clickEvents.last().first)
                    .isEqualTo(AppScreens.AccountScreen.PRIVACY_POLICY_BUTTON)
                assertThat(uriHandler.openedUris.last()).isEqualTo("https://newm.io/app-privacy")
            }
        }

    @Test
    fun `OnBack navigates back`() =
        runTest {
            val presenter = createPresenter()

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnBack)

                assertThat(navigator.popHistory).isNotEmpty()
            }
        }

    @Test
    fun `OnGoogleSignInClicked flows through social login and verification`() =
        runTest {
            val presenter = createPresenter()

            socialLoginManager.resultToEmit = GoogleSignInResult.Success("fake-id-token")

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnGoogleSignInClicked)

                assertThat(fakeEventLogger.clickEvents.last().first)
                    .isEqualTo(AppScreens.WelcomeScreen.LOGIN_WITH_GOOGLE_BUTTON)
                assertThat(socialLoginManager.launchCalled).isTrue()
            }

            assertThat(recaptchaManager.executeLoginCalled).isTrue()
            assertThat(loginUseCase.logInWithGoogleCalled).isTrue()
            assertThat(loginUseCase.lastIdToken).isEqualTo("fake-id-token")
            assertThat(loginUseCase.lastHumanVerificationCode).isEqualTo("fake-token")
            assertThat(navigator.goToHistory.last()).isEqualTo(HomeScreen)
        }

    @Test
    fun `OnGoogleSignInClicked logs error when google sign in fails`() =
        runTest {
            val presenter = createPresenter()

            val exception = Exception("Google Sign In Failed")
            socialLoginManager.resultToEmit = GoogleSignInResult.Failure(exception)

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnGoogleSignInClicked)

                assertThat(socialLoginManager.launchCalled).isTrue()
            }

            assertThat(recaptchaManager.executeLoginCalled).isFalse()
            assertThat(loginUseCase.logInWithGoogleCalled).isFalse()

            val errorLog = fakeAppLogger.errors.first()
            assertThat(errorLog.first).isEqualTo("WelcomeScreen")
            assertThat(errorLog.second).isEqualTo("Google sign in failed")
            assertThat(errorLog.third).isEqualTo(exception)
        }

    @Test
    fun `OnGoogleSignInClicked logs error when recaptcha fails`() =
        runTest {
            val presenter = createPresenter()

            socialLoginManager.resultToEmit = GoogleSignInResult.Success("fake-id-token")
            val exception = Exception("Recaptcha Failed")
            recaptchaManager.executeLoginResult = Result.failure(exception)

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnGoogleSignInClicked)

                // Wait for coroutines to finish
                assertThat(socialLoginManager.launchCalled).isTrue()
            }

            assertThat(recaptchaManager.executeLoginCalled).isTrue()
            assertThat(loginUseCase.logInWithGoogleCalled).isFalse()

            val errorLog = fakeAppLogger.errors.first()
            assertThat(errorLog.first).isEqualTo("WelcomeScreen")
            assertThat(errorLog.second).isEqualTo("Recaptcha failed")
            assertThat(errorLog.third).isEqualTo(exception)
        }

    @Test
    fun `OnGoogleSignInClicked logs error when login use case fails`() =
        runTest {
            val presenter = createPresenter()

            socialLoginManager.resultToEmit = GoogleSignInResult.Success("fake-id-token")
            val exception = Exception("Login Failed")
            loginUseCase.logInWithGoogleResult = Result.failure(exception)

            presenter.test {
                val state = awaitItem() as WelcomeScreen.UiState.Content
                state.onEvent(UiEvent.OnGoogleSignInClicked)

                // Wait for coroutines to finish
                assertThat(socialLoginManager.launchCalled).isTrue()
            }

            assertThat(recaptchaManager.executeLoginCalled).isTrue()
            assertThat(loginUseCase.logInWithGoogleCalled).isTrue()
            assertThat(navigator.goToHistory).doesNotContain(HomeScreen)

            val errorLog = fakeAppLogger.errors.first()
            assertThat(errorLog.first).isEqualTo("WelcomeScreen")
            assertThat(errorLog.second).isEqualTo("Sign in failed")
            assertThat(errorLog.third).isEqualTo(exception)
        }
}
