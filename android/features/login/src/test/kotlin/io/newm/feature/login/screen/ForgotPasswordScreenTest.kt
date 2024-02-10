package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenContent
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordScreenUiState
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
                state = ForgotPasswordScreenUiState.EnterEmail(
                    email = EmailState(),
                    errorMessage = null,
                    isLoading = false,
                    submitButtonEnabled = true,
                    eventSink = {},
                )
            )
        }
    }
}
