package io.newm.core.ui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.newm.core.ui.theme.Gray16
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToBeImplemented(
    text: StringResource,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val toastText = stringResource(text)
    Box(modifier = Modifier.clickable { context.shortToast(toastText) }) { content() }
}

/**
 * A wrapper around [AnimatedVisibility] to let us know when the enter/exit animation is finished.
 * Useful for when you want to make the background of a view change shape
 */
@Composable
inline fun CollapsibleView(
    isVisible: Boolean,
    crossinline onEnterFinished: () -> Unit = {},
    crossinline onExitFinished: () -> Unit = {},
    crossinline content: @Composable () -> Unit,
) {
    val transitionState =
        remember {
            MutableTransitionState(isVisible).apply { targetState = isVisible }
        }

    LaunchedEffect(isVisible) { transitionState.targetState = isVisible }

    LaunchedEffect(transitionState) {
        snapshotFlow {
            transitionState.isIdle &&
                transitionState.currentState == transitionState.targetState
        }.distinctUntilChanged()
            .filter { it }
            .collect {
                if (transitionState.currentState) {
                    onEnterFinished()
                } else {
                    onExitFinished()
                }
            }
    }

    AnimatedVisibility(
        visibleState = transitionState,
        enter = expandVertically(),
        exit = shrinkVertically(),
        content = { content() },
    )
}

/** You can't put a LazyColumn inside a Column/Card */
inline fun <T> LazyListScope.collapsibleCard(
    items: List<T>,
    isExpanded: Boolean,
    crossinline onEnterFinished: () -> Unit = {},
    crossinline onExitFinished: () -> Unit = {},
    crossinline header: @Composable () -> Unit,
    crossinline content: @Composable (T) -> Unit,
) {
    item { header() }
    itemsIndexed(
        items = items,
        key = { i, item -> "$i+${item.hashCode()}" },
        contentType = { _, item -> item },
    ) { i, item ->
        val itemShape =
            when (i) {
                items.size - 1 -> RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp)
                else -> RoundedCornerShape(0.dp)
            }
        CollapsibleView(
            isVisible = isExpanded,
            onEnterFinished = onEnterFinished,
            onExitFinished = onExitFinished,
        ) {
            Box(modifier = Modifier.fillMaxWidth().background(color = Gray16, shape = itemShape)) {
                content(item)
            }
        }
    }
}
