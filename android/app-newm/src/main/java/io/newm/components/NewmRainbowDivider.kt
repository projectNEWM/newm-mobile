package io.newm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.newm.core.ui.utils.NewmRainbowBrush

@Composable
fun NewmRainbowDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = NewmRainbowBrush()
            )
    )
}
