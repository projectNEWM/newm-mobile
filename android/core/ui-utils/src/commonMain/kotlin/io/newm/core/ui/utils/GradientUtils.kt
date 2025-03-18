package io.newm.core.ui.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun iconGradient(
    startColor: Color,
    endColor: Color
) = Brush.linearGradient(
    listOf(startColor, endColor),
    start = Offset(0f, Float.POSITIVE_INFINITY),    // bottom-left
    end = Offset(Float.POSITIVE_INFINITY, 0f)       // top-right
)

fun textGradient(
    startColor: Color,
    endColor: Color
) = Brush.verticalGradient(listOf(startColor, endColor))