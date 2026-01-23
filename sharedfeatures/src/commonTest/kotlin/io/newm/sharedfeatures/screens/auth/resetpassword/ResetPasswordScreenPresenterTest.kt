package io.newm.sharedfeatures.screens.auth.resetpassword

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.fakes.FakeAppLogger
import io.newm.sharedfeatures.fakes.FakeEventLogger
import io.newm.sharedfeatures.fakes.FakeLoginUseCase
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.fakes.FakeRecaptchaManager
import io.newm.sharedfeatures.fakes.FakeResetPasswordUseCase
import io.newm.sharedfeatures.fakes.FakeSignupUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ResetPasswordScreenPresenterTest {
    private lateinit var navigator: FakeNavigator
    private lateinit var signupUseCase: FakeSignupUseCase
    private lateinit var loginUseCase: FakeLoginUseCase
    private lateinit var resetPasswordUseCase: FakeResetPasswordUseCase
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
        resetPasswordUseCase = FakeResetPasswordUseCase()
        recaptchaManager = FakeRecaptchaManager()
        fakeAppLogger = FakeAppLogger()
        logger = NewmAppLogger().apply { setClientLogger(fakeAppLogger) }
        fakeEventLogger = FakeEventLogger()
        analyticsTracker = NewmAppEventLogger().apply { setClientAnalyticsTracker(fakeEventLogger) }
    }

    @Test
    fun `initial state uses email from screen`() =
        runTest {
            val screen = ResetPasswordScreen(email = "test@newm.io")
            val presenter =
                ResetPasswordScreenPresenter(
                    screen,
                    navigator,
                    signupUseCase,
                    loginUseCase,
                    resetPasswordUseCase,
                    recaptchaManager,
                    logger,
                    analyticsTracker,
                )

            presenter.test<ResetPasswordScreenUiState> {
                val state = awaitItem() as ResetPasswordScreenUiState.EnterEmail
                assertThat(state.email.text).isEqualTo("test@newm.io")
                assertThat(state.submitButtonEnabled).isTrue()
            }
        }
}
