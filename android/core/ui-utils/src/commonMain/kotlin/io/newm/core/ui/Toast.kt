package io.newm.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import io.newm.core.ui.utils.shortToast

@Composable
fun ToastSideEffect(message: String?) {
    val context = LocalContext.current

    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            context.shortToast(message)
        }
    }
}
