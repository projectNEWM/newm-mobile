package io.newm.core.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Enum class representing the direction of a swipe.
 */
enum class SwipeDirection {
    LEFT, RIGHT
}

/**
 * A composable function that wraps content and provides swipeable functionality.
 *
 * @param modifier Modifier to be applied to the swipeable wrapper.
 * @param swipeThreshold The distance in Dp that must be swiped to trigger a swipe action.
 * @param onSwipe Lambda function to be executed when a swipe action is detected.
 *                The direction parameter indicates the direction of the swipe.
 * @param content Composable content to be displayed within the swipeable wrapper.
 */
@Composable
fun SwipeableWrapper(
    modifier: Modifier = Modifier,
    swipeThreshold: Dp = 100.dp,
    onSwipe: (SwipeDirection) -> Unit,
    content: @Composable () -> Unit
) {
    val swipeThresholdPx = with(LocalDensity.current) { swipeThreshold.toPx() }
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offsetX = 0f },
                    onHorizontalDrag = { change, dragAmount ->
                        offsetX += dragAmount
                        change.consume()
                    },
                    onDragEnd = {
                        when {
                            offsetX > swipeThresholdPx -> onSwipe(SwipeDirection.RIGHT)
                            offsetX < -swipeThresholdPx -> onSwipe(SwipeDirection.LEFT)
                        }
                        offsetX = 0f // Reset for next gesture
                    }
                )
            }
            .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
    ) {
        content()
    }
}