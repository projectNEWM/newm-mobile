package io.newm.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val displayLarge
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 57.sp)

val displayMedium
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 45.sp)

val displaySmall
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 36.sp)

val headlineLarge
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 32.sp)

val headlineMedium
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 28.sp)

val headlineSmall
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 24.sp)

val titleLarge
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 22.sp)

val titleMedium
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 16.sp)

val titleSmall
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 14.sp)

val bodyLarge
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 16.sp)

val bodyMedium
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 14.sp)

val bodySmall
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 12.sp)

val labelLarge
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp)

val labelMedium
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Bold, fontSize = 12.sp)

val labelSmall
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Bold, fontSize = 11.sp)

val Typography
    @Composable
    get() =
        Typography(
            displayLarge = displayLarge,
            displayMedium = displayMedium,
            displaySmall = displaySmall,
            headlineLarge = headlineLarge,
            headlineMedium = headlineMedium,
            headlineSmall = headlineSmall,
            titleLarge = titleLarge,
            titleMedium = titleMedium,
            titleSmall = titleSmall,
            bodyLarge = bodyLarge,
            bodyMedium = bodyMedium,
            bodySmall = bodySmall,
            labelLarge = labelLarge,
            labelMedium = labelMedium,
            labelSmall = labelSmall,
        )