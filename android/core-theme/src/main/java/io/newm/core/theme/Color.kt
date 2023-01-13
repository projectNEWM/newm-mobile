package io.newm.core.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.airbnb.android.showkase.annotation.ShowkaseColor


val White = Color.White
val Black = Color.Black
val Purple = Color(0xFFDC3CAA)
val Pinkish = Color(0xFFF53C69)
val Gray = Color(0xFF1C1C1E)
val Red = Color(0xFFFF0000)


val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = Pinkish,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = Black,
    onSurface = Black,
    onError = White
)

val DarkColorPalette = lightColors(
    primary = Purple,
    primaryVariant = Pinkish,
    background = Black,
    surface = Black,
    onPrimary = White,
    onBackground = White,
    onSurface = White,
    onError = White
)


object NewmColors {

    // ShowkaseColor
    @ShowkaseColor(name = "Primary", group = ColorGroups.lightColors)
    val Primary = DarkColorPalette.primary

    @ShowkaseColor(name = "PrimaryVariant", group = ColorGroups.lightColors)
    val PrimaryVariant = DarkColorPalette.primaryVariant

    @ShowkaseColor(name = "Secondary", group = ColorGroups.lightColors)
    val Secondary = DarkColorPalette.secondary

    @ShowkaseColor(name = "SecondaryVariant", group = ColorGroups.lightColors)
    val SecondaryVariant = DarkColorPalette.secondaryVariant

    @ShowkaseColor(name = "Background", group = ColorGroups.lightColors)
    val Background = DarkColorPalette.background

    @ShowkaseColor(name = "Surface", group = ColorGroups.lightColors)
    val Surface = DarkColorPalette.surface

    @ShowkaseColor(name = "Error", group = ColorGroups.lightColors)
    val Error = Red

    @ShowkaseColor(name = "OnPrimary", group = ColorGroups.lightColors)
    val OnPrimary = DarkColorPalette.onPrimary

    @ShowkaseColor(name = "OnSecondary", group = ColorGroups.lightColors)
    val OnSecondary = DarkColorPalette.onSecondary

    @ShowkaseColor(name = "OnBackground", group = ColorGroups.lightColors)
    val OnBackground = DarkColorPalette.onBackground

    @ShowkaseColor(name = "OnSurface", group = ColorGroups.lightColors)
    val OnSurface = DarkColorPalette.onSurface

    @ShowkaseColor(name = "OnError", group = ColorGroups.lightColors)
    val OnError = DarkColorPalette.onError
}

private object ColorGroups {
    const val darkColors = "Dark Colors"
    const val lightColors = "Light Colors"
    const val commonColors = "Common Colors"
}