package io.newm.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val h1
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 32.sp)

val h2
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 26.sp)

val h3
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 22.sp)

val h4
    @Composable
    get() = TextStyle(fontFamily = raleway, fontWeight = FontWeight.Bold, fontSize = 20.sp)

val body1
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 16.sp)

val body2
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 14.sp)

val button
    @Composable
    get() = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Bold, fontSize = 16.sp)

val Typography
    @Composable
    get() =
        Typography(
            h1 = h1,
            h2 = h2,
            h3 = h3,
            h4 = h4,
            body1 = body1,
            body2 = body2,
            button = button,
        )
