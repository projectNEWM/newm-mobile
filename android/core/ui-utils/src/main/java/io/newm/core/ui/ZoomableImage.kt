package io.newm.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ZoomableImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    initialZoomed: Boolean = true,
) {

    var zoomed by remember { mutableStateOf(initialZoomed) }

    AnimatedContent(
        modifier = modifier,
        targetState = zoomed,
        label = "zoom animation"
    ) { isZoomed ->
        Crossfade(
            targetState = model,
            label = "artwork crossfade"
        ) { imageModel ->
            AsyncImage(
                model = imageModel,
                contentDescription = contentDescription,
                contentScale = if (isZoomed) contentScale else ContentScale.Fit,
                modifier = Modifier
                    .then(
                        if (isZoomed) Modifier.fillMaxSize() else Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    .padding(if (isZoomed) 0.dp else 24.dp)
                    .then(if (isZoomed) Modifier else Modifier.clip(MaterialTheme.shapes.medium))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                zoomed = !isZoomed
                            }
                        )
                    }
            )
        }
    }
}

