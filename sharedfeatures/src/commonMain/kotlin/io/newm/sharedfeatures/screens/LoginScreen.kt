package io.newm.sharedfeatures.screens

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.parceling.CommonParcelize
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import org.jetbrains.compose.resources.StringResource

@CommonParcelize
object LoginScreen : Screen {
    data class UiState(
        val emailState: EmailState,
        val passwordState: PasswordState,
        val submitButtonEnabled: Boolean,
        val errorMessage: StringResource?,
        val isLoading: Boolean,
        val eventSink: (UiEvent) -> Unit,
    ) : CircuitUiState

    sealed interface UiEvent : CircuitUiEvent {
        data object OnLoginClick : UiEvent

        data object ForgotPasswordClick : UiEvent
    }
}
