package io.newm.sharedfeatures.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette =
    lightColors(
        primary = Purple,
        primaryVariant = Pinkish,
        background = White,
        surface = White,
        onPrimary = White,
        onBackground = Black,
        onSurface = Black,
        onError = White,
    )

private val DarkColorPalette =
    darkColors(
        primary = Purple,
        primaryVariant = Pinkish,
        background = Black,
        surface = Gray600,
        onPrimary = White,
        onBackground = White,
        onSurface = White,
        onError = White,
    )

@Composable
fun NewmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors =
        if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

    MaterialTheme(colors = colors, typography = Typography, shapes = Shapes, content = content)
}
