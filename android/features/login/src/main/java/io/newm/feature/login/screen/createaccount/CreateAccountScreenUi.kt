package io.newm.feature.login.screen.createaccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.newm.core.ui.LoadingScreen
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailVerificationUiState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.SetNameUiState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailAndPasswordUiState

@Composable
fun CreateAccountUi(state: CreateAccountUiState, modifier: Modifier) {
    when (state) {
        is EmailAndPasswordUiState -> {
            EmailAndPasswordUi(modifier, state)
        }

        is EmailVerificationUiState -> {
            EmailVerificationUi(modifier, state)
        }

        is SetNameUiState -> {
            WhatShouldWeCallYouUi(modifier, state)
        }

        CreateAccountUiState.Loading -> LoadingScreen()
    }
}

