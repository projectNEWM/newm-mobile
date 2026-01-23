package io.newm.sharedfeatures.paparazzi

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.VerificationCodeState
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreenUi
import io.newm.sharedfeatures.screens.auth.resetpassword.ResetPasswordScreenUiState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ResetPasswordUiTest(
    @TestParameter configuration: SnapshotTestConfiguration,
) : SnapshotTest(configuration) {
    private val eventLogger = NewmAppEventLogger()

    @Test
    fun enterEmailStep() {
        snapshot {
            ResetPasswordScreenUi(
                state =
                    ResetPasswordScreenUiState.EnterEmail(
                        email = EmailState(),
                        isLoading = false,
                        errorMessage = null,
                        submitButtonEnabled = false,
                        eventSink = {},
                    ),
                eventLogger = eventLogger,
                modifier = Modifier,
            )
        }
    }

    @Test
    fun enterVerificationCodeStep() {
        snapshot {
            ResetPasswordScreenUi(
                state =
                    ResetPasswordScreenUiState.EnterVerificationCode(
                        code = VerificationCodeState(),
                        submitButtonEnabled = false,
                        isLoading = false,
                        errorMessage = null,
                        eventSink = {},
                    ),
                eventLogger = eventLogger,
                modifier = Modifier,
            )
        }
    }

    @Test
    fun enterNewPasswordStep() {
        snapshot {
            ResetPasswordScreenUi(
                state =
                    ResetPasswordScreenUiState.EnterNewPassword(
                        password = PasswordState(),
                        confirmPasswordState = PasswordState(),
                        submitButtonEnabled = false,
                        isLoading = false,
                        errorMessage = null,
                        eventSink = {},
                    ),
                eventLogger = eventLogger,
                modifier = Modifier,
            )
        }
    }
}
