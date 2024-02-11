package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenContent
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenUiState.EnterEmail
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenUiState.EnterVerificationCode
import io.newm.feature.login.screen.resetpassword.ResetPasswordScreenUiState.EnterNewPassword
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ResetPasswordScreenTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun `enter email`() {
        snapshot {
            ResetPasswordScreenContent(
                state = EnterEmail(
                    email = EmailState(),
                    errorMessage = null,
                    isLoading = false,
                    submitButtonEnabled = true,
                    eventSink = {},
                )
            )
        }
    }

    @Test
    fun `enter code`() {
        snapshot {
            ResetPasswordScreenContent(
                state = EnterVerificationCode(
                    code = TextFieldState(),
                    errorMessage = null,
                    isLoading = false,
                    submitButtonEnabled = true,
                    eventSink = {},
                )
            )
        }
    }

    @Test
    fun `enter new password`() {
        snapshot {
            ResetPasswordScreenContent(
                state = EnterNewPassword(
                    password = TextFieldState(),
                    confirmPasswordState = TextFieldState(),
                    errorMessage = null,
                    isLoading = false,
                    submitButtonEnabled = true,
                    eventSink = {},
                )
            )
        }
    }
}
