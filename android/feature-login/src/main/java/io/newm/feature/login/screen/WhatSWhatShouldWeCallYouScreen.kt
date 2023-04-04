package io.newm.feature.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.core.ui.buttons.PrimaryButton

@Composable
fun WhatShouldWeCallYouScreen(
    viewModel: CreateAccountViewModel,
    done: () -> Unit,
) {
    Box(
        modifier = Modifier
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

            var text by remember { mutableStateOf("") }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    viewModel.setNickName(it)
                },
                label = {
                    Text("")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Next") {
                if(text.isNotEmpty()) {
                    done.invoke()
                }
            }
        }
    }
}