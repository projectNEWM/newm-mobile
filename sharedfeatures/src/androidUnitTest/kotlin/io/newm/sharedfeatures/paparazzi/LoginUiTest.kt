package io.newm.sharedfeatures.paparazzi

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.sharedfeatures.login.EmailState
import io.newm.sharedfeatures.login.LoginUi
import io.newm.sharedfeatures.login.PasswordState
import io.newm.sharedfeatures.screens.LoginScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class LoginUiTest(
    @TestParameter configuration: SnapshotTestConfiguration,
) : SnapshotTest(configuration) {
    @Test
    fun defaultLoginUi() {
        snapshot {
            LoginUi(
                state =
                    LoginScreen.UiState(
                        emailState = EmailState(),
                        passwordState = PasswordState(),
                        submitButtonEnabled = true,
                        errorMessage = null,
                        isLoading = false,
                        eventSink = {},
                    ),
                modifier = Modifier,
            )
        }
    }

    @Test
    fun filledOutLoginUi() {
        snapshot {
            LoginUi(
                state =
                    LoginScreen.UiState(
                        emailState =
                            EmailState().apply {
                                text = "william.henry.harrison@example-pet-store.com"
                            },
                        passwordState = PasswordState().apply { text = "password" },
                        submitButtonEnabled = true,
                        //                    errorMessage =
                        // Res.string.password_validation_error_message,
                        errorMessage = null,
                        isLoading = true,
                        eventSink = {},
                    ),
                modifier = Modifier,
            )
        }
    }
}
