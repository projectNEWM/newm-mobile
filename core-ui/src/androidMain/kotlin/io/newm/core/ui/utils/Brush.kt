package io.newm.core.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import io.newm.core.ui.theme.RainbowEight
import io.newm.core.ui.theme.RainbowFive
import io.newm.core.ui.theme.RainbowFour
import io.newm.core.ui.theme.RainbowOne
import io.newm.core.ui.theme.RainbowSeven
import io.newm.core.ui.theme.RainbowSix
import io.newm.core.ui.theme.RainbowThree
import io.newm.core.ui.theme.RainbowTwo

@Composable
fun HotPinkBrush(): Brush = Brush.horizontalGradient(
    colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.primary
    )
)

@Composable
fun DisabledHotPinkBrush(): Brush =
    Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        ),
    )

@Composable
fun SongRingBrush(): Brush =
    Brush.horizontalGradient(
        colors =
        listOf(
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.secondary,
        ),
    )

@Composable
fun ActionButtonBackgroundBrush(): Brush = Brush.verticalGradient(
    colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    )
)

@Composable
fun NewmRainbowBrush(): Brush =
    Brush.horizontalGradient(
        colors =
        listOf(
            RainbowOne,
            RainbowTwo,
            RainbowThree,
            RainbowFour,
            RainbowFive,
            RainbowSix,
            RainbowSeven,
            RainbowEight,
        ),
    )