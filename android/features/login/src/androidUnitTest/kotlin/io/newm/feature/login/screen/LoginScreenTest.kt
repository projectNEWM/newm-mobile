package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.login.LoginScreenUiState
import io.newm.feature.login.screen.password.PasswordState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class LoginScreenTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun default() {
        snapshot {
            LoginScreenContent(
                state = LoginScreenUiState(
                    eventSink = {},
                    errorMessage = null,
                    isLoading = false,
                    emailState = EmailState(),
                    passwordState = PasswordState(),
                    submitButtonEnabled = true,
                )
            )
        }
    }
}
