package io.newm.sharedfeatures.screens.auth.login

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.fakes.FakeAppLogger
import io.newm.sharedfeatures.fakes.FakeEventLogger
import io.newm.sharedfeatures.fakes.FakeLoginUseCase
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.fakes.FakeRecaptchaManager
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.LoginScreen
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LoginPresenterTest {
    private lateinit var navigator: FakeNavigator
    private lateinit var loginUseCase: FakeLoginUseCase
    private lateinit var recaptchaManager: FakeRecaptchaManager
    private lateinit var logger: NewmAppLogger
    private lateinit var analyticsTracker: NewmAppEventLogger
    private lateinit var fakeAppLogger: FakeAppLogger
    private lateinit var fakeEventLogger: FakeEventLogger

    @BeforeTest
    fun setup() {
        navigator = FakeNavigator()
        loginUseCase = FakeLoginUseCase()
        recaptchaManager = FakeRecaptchaManager()

        fakeAppLogger = FakeAppLogger()
        logger = NewmAppLogger().apply { setClientLogger(fakeAppLogger) }

        fakeEventLogger = FakeEventLogger()
        analyticsTracker = NewmAppEventLogger().apply { setClientAnalyticsTracker(fakeEventLogger) }
    }

    @Test
    fun `initial state has disabled submit button`() =
        runTest {
            val presenter =
                LoginPresenter(navigator, { loginUseCase }, recaptchaManager, logger, analyticsTracker)

            presenter.test<LoginScreen.UiState> {
                val state = awaitItem()
                assertThat(state.submitButtonEnabled).isFalse()
                assertThat(state.isLoading).isFalse()
                assertThat(state.errorMessage).isNull()
            }
        }

    @Test
    fun `submit button enabled when form is valid`() =
        runTest {
            val presenter =
                LoginPresenter(navigator, { loginUseCase }, recaptchaManager, logger, analyticsTracker)

            presenter.test<LoginScreen.UiState> {
                val initialState = awaitItem()

                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"

                val updatedState = expectMostRecentItem()
                assertThat(updatedState.submitButtonEnabled).isTrue()
            }
        }

    @Test
    fun `successful login flow`() =
        runTest {
            val presenter =
                LoginPresenter(navigator, { loginUseCase }, recaptchaManager, logger, analyticsTracker)

            presenter.test<LoginScreen.UiState> {
                val initialState = awaitItem()

                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"

                val updatedState = expectMostRecentItem()

                updatedState.eventSink(LoginScreen.UiEvent.OnLoginClick)

                // Should show loading
                assertThat(awaitItem().isLoading).isTrue()

                // Verify interactions and navigation
                assertThat(recaptchaManager.executeLoginCalled).isTrue()
                assertThat(loginUseCase.logInCalled).isTrue()
                assertThat(navigator.goToHistory.last()).isEqualTo(HomeScreen)
            }
        }

    @Test
    fun `recaptcha failure shows error`() =
        runTest {
            recaptchaManager.executeLoginResult = Result.failure(Exception("Recaptcha failed"))
            val presenter =
                LoginPresenter(navigator, { loginUseCase }, recaptchaManager, logger, analyticsTracker)

            presenter.test<LoginScreen.UiState> {
                val initialState = awaitItem()

                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"

                val updatedState = expectMostRecentItem()

                updatedState.eventSink(LoginScreen.UiEvent.OnLoginClick)

                // We expect at least one state change (to loading), and then to error.
                // Using expectMostRecentItem will give us the latest state after the coroutine
                // finishes.
                val errorState = expectMostRecentItem()
                assertThat(errorState.isLoading).isFalse()
                assertThat(errorState.errorMessage).isNotNull()
                assertThat(loginUseCase.logInCalled).isFalse()
            }
        }

    @Test
    fun `login failure sets isLoading to false`() =
        runTest {
            loginUseCase.logInResult = Result.failure(Exception("Login failed"))
            val presenter =
                LoginPresenter(navigator, { loginUseCase }, recaptchaManager, logger, analyticsTracker)

            presenter.test<LoginScreen.UiState> {
                val initialState = awaitItem()

                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"

                val updatedState = expectMostRecentItem()

                updatedState.eventSink(LoginScreen.UiEvent.OnLoginClick)

                // Expect error state
                val finalState = expectMostRecentItem()
                assertThat(finalState.isLoading).isFalse()
                assertThat(finalState.errorMessage).isNotNull()
            }
        }
}
