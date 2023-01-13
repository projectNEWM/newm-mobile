package io.newm.feature.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.utils.shortToast

@Composable
fun EnterVerificationCodeScreen(
    viewModel: CreateAccountViewModel,
    onVerificationComplete: () -> Unit,
) {
    val context = LocalContext.current
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
                text = "Check your email",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1
            )
            Text(
                text = "Enter your \nverification code \nbelow",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Didn't receive an email? Click here to resend",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(16.dp))
            var text by remember { mutableStateOf("") }
            val state = viewModel.state.collectAsState()

            LaunchedEffect(key1 = state.value.isUserRegistered, key2 = state.value.errorMessage) {
                if (state.value.isUserRegistered) {
                    onVerificationComplete()
                }
                if (!state.value.errorMessage.isNullOrBlank()) {
                    context.shortToast(state.value.errorMessage.orEmpty())
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    viewModel.setEmailVerificationCode(it)
                },
                label = {
                    Text("Enter Verification Code:")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Continue") {
                viewModel.verifyAccount()
            }
        }
    }
}