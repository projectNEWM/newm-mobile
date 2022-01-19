package com.projectnewm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import com.projectnewm.R

@Composable
fun NewmRainbowDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = NewmRainbowBrush()
            )
    )
}

@Composable
fun NewmRainbowBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.gradient_blue),
            colorResource(id = R.color.gradient_dark_blue),
            colorResource(id = R.color.gradient_purple),
            colorResource(id = R.color.gradient_pink),
            colorResource(id = R.color.gradient_red),
            colorResource(id = R.color.gradient_orange),
            colorResource(id = R.color.gradient_yellow),
            colorResource(id = R.color.gradient_green),
        )
    )
}