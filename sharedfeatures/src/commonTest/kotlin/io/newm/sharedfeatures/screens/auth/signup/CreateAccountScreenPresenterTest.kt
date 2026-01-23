package io.newm.sharedfeatures.screens.auth.signup

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.fakes.FakeAppLogger
import io.newm.sharedfeatures.fakes.FakeEventLogger
import io.newm.sharedfeatures.fakes.FakeLoginUseCase
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.fakes.FakeRecaptchaManager
import io.newm.sharedfeatures.fakes.FakeSignupUseCase
import io.newm.sharedfeatures.screens.HomeScreen
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailAndPasswordUiState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.EmailVerificationUiState
import io.newm.sharedfeatures.screens.auth.signup.CreateAccountUiState.Loading
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreateAccountScreenPresenterTest {
    private lateinit var navigator: FakeNavigator
    private lateinit var signupUseCase: FakeSignupUseCase
    private lateinit var loginUseCase: FakeLoginUseCase
    private lateinit var recaptchaManager: FakeRecaptchaManager
    private lateinit var logger: NewmAppLogger
    private lateinit var analyticsTracker: NewmAppEventLogger
    private lateinit var fakeAppLogger: FakeAppLogger
    private lateinit var fakeEventLogger: FakeEventLogger

    @BeforeTest
    fun setup() {
        navigator = FakeNavigator()
        signupUseCase = FakeSignupUseCase()
        loginUseCase = FakeLoginUseCase()
        recaptchaManager = FakeRecaptchaManager()
        fakeAppLogger = FakeAppLogger()
        logger = NewmAppLogger().apply { setClientLogger(fakeAppLogger) }
        fakeEventLogger = FakeEventLogger()
        analyticsTracker = NewmAppEventLogger().apply { setClientAnalyticsTracker(fakeEventLogger) }
    }

    @Test
    fun `initial state is EmailAndPasswordUiState`() =
        runTest {
            val presenter =
                CreateAccountScreenPresenter(
                    navigator,
                    signupUseCase,
                    loginUseCase,
                    recaptchaManager,
                    logger,
                    analyticsTracker,
                )

            presenter.test<CreateAccountUiState> {
                val state = awaitItem()
                assertThat(state).isInstanceOf<EmailAndPasswordUiState>()
                val uiState = state as EmailAndPasswordUiState
                assertThat(uiState.submitButtonEnabled).isFalse()
                assertThat(uiState.errorMessage).isNull()
            }
        }

    @Test
    fun `valid email and password enables submit button`() =
        runTest {
            val presenter =
                CreateAccountScreenPresenter(
                    navigator,
                    signupUseCase,
                    loginUseCase,
                    recaptchaManager,
                    logger,
                    analyticsTracker,
                )

            presenter.test<CreateAccountUiState> {
                val initialState = awaitItem() as EmailAndPasswordUiState
                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"
                initialState.passwordConfirmationState.text = "Password123!"

                val updatedState = expectMostRecentItem() as EmailAndPasswordUiState
                assertThat(updatedState.submitButtonEnabled).isTrue()
            }
        }

    @Test
    fun `submit email moves to verification step`() =
        runTest {
            val presenter =
                CreateAccountScreenPresenter(
                    navigator,
                    signupUseCase,
                    loginUseCase,
                    recaptchaManager,
                    logger,
                    analyticsTracker,
                )

            presenter.test<CreateAccountUiState> {
                val initialState = awaitItem() as EmailAndPasswordUiState
                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"
                initialState.passwordConfirmationState.text = "Password123!"

                val updatedState = expectMostRecentItem() as EmailAndPasswordUiState
                updatedState.eventSink(SignupFormUiEvent.Next)

                // Should see loading, then email verification
                // Depending on how fast execution is, we might skip loading or see it.
                // expectMostRecentItem waits for stability.

                val verificationState = expectMostRecentItem()

                // Check if we arrived at verification state
                assertThat(verificationState).isInstanceOf<EmailVerificationUiState>()

                assertThat(recaptchaManager.executeCalled).isTrue()
                assertThat(recaptchaManager.lastExecuteAction).isEqualTo("auth_code")
                assertThat(signupUseCase.requestEmailConfirmationCodeCalled).isTrue()
            }
        }

    @Test
    fun `submit verification code calls register and login`() =
        runTest {
            val presenter =
                CreateAccountScreenPresenter(
                    navigator,
                    signupUseCase,
                    loginUseCase,
                    recaptchaManager,
                    logger,
                    analyticsTracker,
                )

            presenter.test<CreateAccountUiState> {
                val initialState = awaitItem() as EmailAndPasswordUiState
                initialState.emailState.text = "test@newm.io"
                initialState.passwordState.text = "Password123!"
                initialState.passwordConfirmationState.text = "Password123!"

                val formState = expectMostRecentItem() as EmailAndPasswordUiState
                formState.eventSink(SignupFormUiEvent.Next)

                val verificationState = expectMostRecentItem() as EmailVerificationUiState

                verificationState.verificationCode.text = "123456"

                // Re-fetch state as verification code update might trigger recomposition
                val validVerificationState = expectMostRecentItem() as EmailVerificationUiState
                assertThat(validVerificationState.nextButtonEnabled).isTrue()

                validVerificationState.eventSink(EmailVerificationUiEvent.Next)

                // Wait for final result
                assertThat(awaitItem()).isInstanceOf<Loading>()

                assertThat(recaptchaManager.executeCalled).isTrue()
                assertThat(recaptchaManager.lastExecuteAction).isEqualTo("signup") // Second call
                assertThat(signupUseCase.registerUserCalled).isTrue()
                assertThat(recaptchaManager.executeLoginCalled).isTrue()
                assertThat(loginUseCase.logInCalled).isTrue()
                assertThat(navigator.goToHistory.last()).isEqualTo(HomeScreen)
            }
        }
}
