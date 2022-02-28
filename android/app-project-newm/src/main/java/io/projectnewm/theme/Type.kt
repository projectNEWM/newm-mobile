package io.projectnewm.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h2 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)