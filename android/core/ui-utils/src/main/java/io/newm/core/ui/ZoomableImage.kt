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
import coil.compose.AsyncImagePainter

@Composable
fun ZoomableImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    initialZoomed: Boolean = true,
    onState: ((AsyncImagePainter.State) -> Unit)? = null
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
            if (isZoomed) {
                AsyncImage(
                    model = imageModel,
                    onState = onState,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    zoomed = false
                                }
                            )
                        }
                )
            } else {
                AsyncImage(
                    model = imageModel,
                    onState = onState,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    zoomed = true
                                }
                            )
                        }
                )
            }
        }
    }
}

