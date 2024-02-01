package io.newm.feature.login.screen.login

sealed interface LoginUiEvent {
    data object OnLoginClick : LoginUiEvent
}
