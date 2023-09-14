package io.newm.feature.login.screen.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.text.TextFieldWithLabel

@Composable
internal fun WhatShouldWeCallYouUi(
    modifier: Modifier,
    state: CreateAccountUiState.SetNameUiState,
) {
    val onEvent = state.eventSink

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "What should we \ncall you?",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(32.dp))

            TextFieldWithLabel(
                value = state.name.text,
                onValueChange = state.name::text::set,
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Next") {
                if (state.name.text.isNotEmpty()) {
                    onEvent(SetNameUiEvent.Next)
                }
            }
        }
    }
}
