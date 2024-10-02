package io.newm.feature.login.screen.welcome

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface WelcomeScreenUiEvent : CircuitUiEvent{
    data object CreateAccountClicked : WelcomeScreenUiEvent
    data object LoginClicked : WelcomeScreenUiEvent
    data object OnGoogleSignInClicked : WelcomeScreenUiEvent
    data object OnPrivacyPolicyClicked : WelcomeScreenUiEvent
    data object OnTermsOfServiceClicked : WelcomeScreenUiEvent
}
