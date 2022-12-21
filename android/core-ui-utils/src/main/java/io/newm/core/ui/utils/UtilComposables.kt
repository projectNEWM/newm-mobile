package io.newm.core.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ToBeImplemented(text: String = "Uh Oh! To be implemented", content: @Composable () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .clickable {
                context.shortToast(text)
            },
    ) {
        content()
    }
}