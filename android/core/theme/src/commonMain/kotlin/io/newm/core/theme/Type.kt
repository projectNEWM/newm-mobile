package io.newm.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val h1 @Composable get() = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp
)

//@ShowkaseTypography
val h2 @Composable get() = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp
)

//@ShowkaseTypography
val h3 @Composable get() = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp
)

//@ShowkaseTypography
val h4 @Composable get() = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
)

//@ShowkaseTypography
val body1 @Composable get() = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)

//@ShowkaseTypography
val body2 @Composable get() = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

val button @Composable get() = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

// Set of Material typography styles to start with
val Typography @Composable get() = Typography(
    h1 = h1,
    h2 = h2,
    h3 = h3,
    h4 = h4,
    body1 = body1,
    body2 = body2,
    button = button,
)
