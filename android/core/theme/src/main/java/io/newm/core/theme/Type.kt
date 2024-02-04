package io.newm.core.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
//import com.airbnb.android.showkase.annotation.ShowkaseTypography

//@ShowkaseTypography
val h1 = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp
)

//@ShowkaseTypography
val h2 = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp
)

//@ShowkaseTypography
val h3 = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp
)

//@ShowkaseTypography
val h4 = TextStyle(
    fontFamily = raleway,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
)

//@ShowkaseTypography
val body1 = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)

//@ShowkaseTypography
val body2 = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

val button = TextStyle(
    fontFamily = montserrat,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = h1,
    h2 = h2,
    h3 = h3,
    h4 = h4,
    body1 = body1,
    body2 = body2,
    button = button,
)
