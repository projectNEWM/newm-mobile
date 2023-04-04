package io.newm.core.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.airbnb.android.showkase.annotation.ShowkaseColor


val White = Color.White
val Black = Color.Black
val Purple = Color(0xFFDC3CAA)
val Pinkish = Color(0xFFF53C69)
val Gray = Color(0xFF1C1C1E)
val Gray600 = Color(0xFF121214)
val Red = Color(0xFFFF0000)
val Gray100 = Color(0xFF8E8E93)
val LightSkyBlue = Color(0xFF5091EB)
val DarkViolet = Color(0xFFC341F0)
val DarkPink = Color(0xFFF53C69)
val OceanGreen = Color(0xFF41BE91)
val BrightOrange = Color(0xFFFF6E32)
val YellowJacket = Color(0xFFFFC33C)



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