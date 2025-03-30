package io.newm.core.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.drawWithBrush(brush: Brush, blendMode: BlendMode = BlendMode.SrcAtop): Modifier {
    return graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawRect(brush = brush, blendMode = blendMode)
            }
        }
}





