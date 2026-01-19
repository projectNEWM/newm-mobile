package io.newm.sharedfeatures.welcome

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.fakes.*
import io.newm.sharedfeatures.screens.CreateAccountScreen
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class WelcomePresenterTest {

    private val navigator: FakeNavigator = FakeNavigator()
    private val loginUseCase: FakeLoginUseCase = FakeLoginUseCase()
    private val socialLoginManager: FakeSocialLoginManager = FakeSocialLoginManager()
    private val recaptchaManager: FakeRecaptchaManager = FakeRecaptchaManager()
    private val fakeEventLogger: FakeEventLogger = FakeEventLogger()
    private val analyticsTracker: NewmAppEventLogger = NewmAppEventLogger().apply {
        setClientAnalyticsTracker(fakeEventLogger)
    }
    private val fakeAppLogger: NewmAppLogger = NewmAppLogger().apply {
        setClientLogger(FakeAppLogger())
    }

    private fun createPresenter(): Presenter<WelcomeScreen.UiState> {
        val realPresenter = WelcomePresenter(
            navigator = navigator,
            loginUseCase = loginUseCase,
            socialLoginManager = socialLoginManager,
            recaptchaManager = recaptchaManager,
            analyticsTracker = analyticsTracker,
            logger = fakeAppLogger
        )
        return object : Presenter<WelcomeScreen.UiState> {
            @Composable
            override fun present(): WelcomeScreen.UiState {
                var state: WelcomeScreen.UiState by remember { mutableStateOf(WelcomeScreen.UiState.Loading) }
                CompositionLocalProvider(LocalUriHandler provides FakeUriHandler()) {
                    state = realPresenter.present()
                }
                return state
            }
        }
    }

    @Test
    fun `CreateAccountClicked navigates to CreateAccountScreen`() = runTest {
        val presenter = createPresenter()

        presenter.test {
            val state = awaitItem() as WelcomeScreen.UiState.Content
            state.onEvent(WelcomeScreen.UiEvent.CreateAccountClicked)

            assertThat(navigator.goToHistory.last()).isEqualTo(CreateAccountScreen)
        }
    }

    @Test
    fun `OnLogin navigates to LoginScreen`() = runTest {
        val presenter = createPresenter()

        presenter.test {
            val state = awaitItem() as WelcomeScreen.UiState.Content
            state.onEvent(WelcomeScreen.UiEvent.OnLogin)

            assertThat(navigator.goToHistory.last()).isEqualTo(LoginScreen)
        }
    }

    @Test
    fun `OnGoogleSignInClicked flows through social login and verification`() = runTest {
        val presenter = createPresenter()

        socialLoginManager.resultToEmit = GoogleSignInResult.Success("fake-id-token")

        presenter.test {
            val state = awaitItem() as WelcomeScreen.UiState.Content
            state.onEvent(WelcomeScreen.UiEvent.OnGoogleSignInClicked)

            assertThat(socialLoginManager.launchCalled).isTrue()
        }

        assertThat(recaptchaManager.executeLoginCalled).isTrue()
        assertThat(loginUseCase.logInWithGoogleCalled).isTrue()
        assertThat(loginUseCase.lastIdToken).isEqualTo("fake-id-token")
        assertThat(loginUseCase.lastHumanVerificationCode).isEqualTo("fake-token")
        assertThat(navigator.goToHistory.last()).isEqualTo(HomeScreen)
    }
}

class FakeUriHandler : UriHandler {
    override fun openUri(uri: String) {}
}
