package io.newm.feature.login.screen

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.createaccount.CreateAccountUiState
import io.newm.feature.login.screen.createaccount.signupform.SignUpFormUi
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CreateAccountUiTest(
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun `default sign up form`() {
        snapshot {
            SignUpFormUi(
                state = CreateAccountUiState.SignupForm(
                    passwordConfirmationState = TextFieldState(),
                    passwordState = TextFieldState(),
                    emailState = TextFieldState(),
                    eventSink = {},
                )
            )
        }
    }
}
