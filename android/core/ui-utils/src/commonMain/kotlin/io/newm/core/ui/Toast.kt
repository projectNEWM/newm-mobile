package io.newm.core.ui

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ToastSideEffect(
    message: String?
) {
    val snackbarHostState = LocalSnackBarHostState.current

    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = null,
                duration = SnackbarDuration.Short
            )
        }
    }
}
