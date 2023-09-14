package io.newm.feature.login.screen

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.feature.login.screen.createaccount.CreateAccountUi
import io.newm.feature.login.screen.createaccount.CreateAccountUiState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CreateAccountUiTest(
    @TestParameter private val testCase: TestCase,
    @TestParameter private val testConfiguration: SnapshotTestConfiguration,
) : SnapshotTest(testConfiguration) {

    @Test
    fun `test state`() {
        snapshot {
            CreateAccountUi(state = testCase.state, modifier = Modifier)
        }
    }
}

enum class TestCase(
    val state: CreateAccountUiState
) {
    EMAIL_AND_PASSWORD(
        state = CreateAccountUiState.EmailAndPasswordUiState(
            passwordConfirmationState = TextFieldState(),
            passwordState = TextFieldState(),
            emailState = TextFieldState(),
            eventSink = {},
        )
    ),
    EMAIL_VERIFICATION(
        state = CreateAccountUiState.EmailVerificationUiState(
            verificationCode = TextFieldState(),
            errorMessage = null,
            eventSink = {},
        )
    ),
    WHAT_SHOULD_WE_CALL_YOU(
        state = CreateAccountUiState.SetNameUiState(
            name = TextFieldState(),
            eventSink = {},
        )
    ),
}
