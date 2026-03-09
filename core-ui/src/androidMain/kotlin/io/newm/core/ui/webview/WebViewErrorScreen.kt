package io.newm.core.ui.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.theme.DarkViolet
import io.newm.core.ui.theme.Gray16
import io.newm.core.ui.theme.Gray600
import io.newm.core.ui.theme.Pinkish
import io.newm.core.ui.theme.White50

@Composable
internal fun WebViewErrorScreen(
    title: String,
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors = listOf(Gray600, Gray16, MaterialTheme.colors.background),
                        ),
                ).padding(24.dp),
    ) {
        Column(
            modifier = Modifier.widthIn(max = 420.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .background(
                            brush =
                                Brush.linearGradient(
                                    colors =
                                        listOf(
                                            DarkViolet.copy(alpha = 0.28f),
                                            Pinkish.copy(alpha = 0.28f),
                                        ),
                                ),
                        ).border(1.dp, White50, CircleShape)
                        .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(32.dp),
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 10.dp,
                border =
                    androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary.copy(alpha = 0.12f),
                    ),
            ) {
                Column(
                    modifier =
                        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.76f),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    PrimaryButton(
                        text = actionLabel,
                        onClick = onAction,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
