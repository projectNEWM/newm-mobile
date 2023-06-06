package io.newm.feature.login.screen.createaccount

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
    val state by viewModel.state.collectAsState()
    
    EnterVerificationScreenContent(
        state = state,
        onVerificationComplete = onVerificationComplete,
        setEmailVerificationCode = viewModel::setEmailVerificationCode,
        verifyAccount = viewModel::verifyAccount,
    )
}

@Composable
internal fun EnterVerificationScreenContent(
    state: CreateAccountViewModel.SignupUserState,
    onVerificationComplete: () -> Unit,
    setEmailVerificationCode: (String) -> Unit,
    verifyAccount: () -> Unit,
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

            LaunchedEffect(key1 = state.isUserRegistered, key2 = state.errorMessage) {
                if (state.isUserRegistered) {
                    onVerificationComplete()
                }
                if (!state.errorMessage.isNullOrBlank()) {
                    context.shortToast(state.errorMessage.orEmpty())
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    setEmailVerificationCode(it)
                },
                label = {
                    Text("Enter Verification Code:")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Continue") {
                verifyAccount()
            }
        }
    }
}
