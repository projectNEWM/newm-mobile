package io.newm.sharedfeatures.devmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.shared.config.NewmSharedBuildConfig

/**
 * A global overlay that provides access to the developer menu via:
 * 1. A floating "DEV" button (bottom-right)
 * 2. A keyboard shortcut (SHIFT + D)
 *
 * Only active in debug builds.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DebugOverlay(
    buildConfig: NewmSharedBuildConfig,
    onOpenDebugMenu: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (!buildConfig.isDebug) {
        content()
        return
    }

    val haptic = LocalHapticFeedback.current
    val focusRequester = FocusRequester()

    val triggerDebugMenu = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        onOpenDebugMenu()
    }

    Box(
        modifier =
            modifier.fillMaxSize().focusRequester(focusRequester).onKeyEvent { event ->
                if (event.isShiftPressed && event.key == Key.D) {
                    triggerDebugMenu()
                    true
                } else {
                    false
                }
            },
    ) {
        content()

        // Floating Debug Button
        Box(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 80.dp,
                        end = 16.dp,
                    ) // Offset to avoid interference with bottom navigation
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.8f))
                    .clickable { triggerDebugMenu() }
                    .alpha(0.7f),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "DEV", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Request focus to capture key events
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}
