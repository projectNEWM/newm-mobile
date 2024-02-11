package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenContent
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenUiState
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenUiState.EnterEmail
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenUiState.EnterVerificationCode
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenUiState.SetNewPassword
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ForgotPasswordScreenTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun `enter email`() {
        snapshot {
            ForgotPasswordScreenContent(
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
            ForgotPasswordScreenContent(
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
            ForgotPasswordScreenContent(
                state = SetNewPassword(
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
