package io.newm.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


val White = Color.White
val White50 = Color(0x80FFFFFF)

val Black = Color.Black
val Black90 = Color(0xE5000000)
val Purple = Color(0xFFDC3CAA)
val Pinkish = Color(0xFFF53C69)
val GraySuit = Color(0xFF8F8F91)
val StatusGreen = Color(0xFF68CD67)
val Gray300 = Color(0xFF48484A)
val Gray400 = Color(0xFF2C2C2E)
val Gray500 = Color(0xFF1C1C1E)
val Gray600 = Color(0xFF121214)
val Gray6F = Color(0xFF6F6F70)
val Gray650 = Color(0x80121214)
val Gray16 = Color(0xFF161618)
val Red = Color(0xFFFF0000)
val Gray100 = Color(0xFF8E8E93)
val LightSkyBlue = Color(0xFF5091EB)
val Gray23 = Color(0xFF232323)
val DarkViolet = Color(0xFFC341F0)
val DarkPink = Color(0xFFF53C69)
val OceanGreen = Color(0xFF41BE91)
val BrightOrange = Color(0xFFFF6E32)
val YellowJacket = Color(0xFFFFC33C)
val SystemRed = Color(0xFFEB5545)
val SteelPink = Color(0xFFD841F0)
val CerisePink = Color(0xFFF53C74)
val GlassSmith = Color(0xFF46B5C0)




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

val DarkColorPalette = darkColors(
    primary = Purple,
    primaryVariant = Pinkish,
    background = Black,
    surface = Gray600,
    onPrimary = White,
    onBackground = White,
    onSurface = White,
    onError = White
)

