package io.newm.feature.login.screen.login

import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState

data class LoginScreenUiState(
    val emailState: TextFieldState,
    val passwordState: TextFieldState,
    val submitButtonEnabled: Boolean,
    val eventSink: (LoginUiEvent) -> Unit,
    val errorMessage: String?,
) : CircuitUiState
