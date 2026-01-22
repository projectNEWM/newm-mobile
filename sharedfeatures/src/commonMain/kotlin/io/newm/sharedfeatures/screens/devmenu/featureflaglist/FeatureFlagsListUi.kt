@file:OptIn(ExperimentalMaterialApi::class)

package io.newm.sharedfeatures.screens.devmenu.featureflaglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.shared.commonPublic.featureflags.FlagCategory
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatureFlagsListUi(
    state: FeatureFlagsListScreen.UiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is FeatureFlagsListScreen.UiState.Loading -> {
            LoadingState(modifier)
        }

        is FeatureFlagsListScreen.UiState.Error -> {
            ErrorState(
                title = "Feature Flags Unavailable",
                message = state.message,
                onRetry = state.onRetry,
            )
        }

        is FeatureFlagsListScreen.UiState.Content -> {
            ClearContentState(state, modifier)
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    message: String = "Loading feature flags...",
) {
    Box(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary, strokeWidth = 3.dp)

            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Error Loading Flags",
) {
    Box(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Error icon
                Text(
                    text = "⚠️",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.error,
                )

                // Error title
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                // Error message
                Text(
                    text = message,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                )

                // Retry button
                Button(
                    onClick = onRetry,
                    colors =
                        ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("🔄") // Refresh emoji
                    Spacer(Modifier.width(8.dp))
                    Text("Retry")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ClearContentState(
    state: FeatureFlagsListScreen.UiState.Content,
    modifier: Modifier,
) {
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = { state.onEvent(FeatureFlagsListScreen.UiEvent.OnRefresh) },
        )

    Scaffold(modifier = modifier, topBar = { ClearTopAppBar(state) }) { padding ->
        Box(modifier = Modifier.padding(padding).pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Environment info header
                item { EnvironmentInfoCard(state.environmentInfo) }

                // Debug info section
                state.debugInfo?.let { debugInfo ->
                    item { DebugInfoCard(debugInfo, state.onEvent) }
                }

                // Grouped flags by category
                state.groupedFlags.forEach { (category, flags) ->
                    item { CategoryHeader(category, flags.size) }

                    items(flags) { flagItem ->
                        ClearFeatureFlagRow(item = flagItem, onEvent = state.onEvent)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun EnvironmentInfoCard(environmentInfo: FeatureFlagsListScreen.EnvironmentInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor =
            when (environmentInfo.environment) {
                "Production" -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                "Development" -> Color(0xFFFF9800).copy(alpha = 0.1f)
                else -> MaterialTheme.colors.surface
            },
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "Environment: ${environmentInfo.environment}",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color =
                        when (environmentInfo.environment) {
                            "Production" -> Color(0xFF4CAF50)
                            "Development" -> Color(0xFFFF9800)
                            else -> MaterialTheme.colors.onSurface
                        },
                )
                Text(
                    text = "LaunchDarkly: ${environmentInfo.clientStatus}",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                )
                environmentInfo.lastSync?.let { lastSync ->
                    Text(
                        text = "Last synced: ${formatTimestamp(lastSync)}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Text(
                text =
                    when (environmentInfo.environment) {
                        "Production" -> "🚀"
                        "Development" -> "🔧"
                        else -> "⚙️"
                    },
                style = MaterialTheme.typography.h5,
            )
        }
    }
}

@Composable
private fun ClearTopAppBar(state: FeatureFlagsListScreen.UiState.Content) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Column {
                Text("Feature Flags")
                Text(
                    text =
                        "${state.environmentInfo.environment} • ${state.flags.size} flags • ${state.flags.count {
                            it.isOverridden
                        }} overridden",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { state.onEvent(FeatureFlagsListScreen.UiEvent.OnBack) }) {
                Text("←", style = MaterialTheme.typography.h5)
            }
        },
        actions = {
            IconButton(onClick = { showMenu = true }) {
                Text("⋮", style = MaterialTheme.typography.h5)
            }

            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    onClick = {
                        state.onEvent(FeatureFlagsListScreen.UiEvent.OnToggleDebugMode)
                        showMenu = false
                    },
                ) {
                    Text("🐛")
                    Spacer(Modifier.width(8.dp))
                    Text(if (state.debugInfo != null) "Hide Debug" else "Show Debug")
                }

                DropdownMenuItem(
                    onClick = {
                        state.onEvent(FeatureFlagsListScreen.UiEvent.OnExportDebugState)
                        showMenu = false
                    },
                ) {
                    Text("📥")
                    Spacer(Modifier.width(8.dp))
                    Text("Export Debug State")
                }

                Divider()

                DropdownMenuItem(
                    onClick = {
                        state.onEvent(FeatureFlagsListScreen.UiEvent.OnResetAllFlags)
                        showMenu = false
                    },
                ) {
                    Text("🔄", color = MaterialTheme.colors.error)
                    Spacer(Modifier.width(8.dp))
                    Text("Reset All Flags", color = MaterialTheme.colors.error)
                }
            }
        },
    )
}

@Composable
private fun ClearFeatureFlagRow(
    item: FeatureFlagsListScreen.FeatureFlagListItem,
    onEvent: (FeatureFlagsListScreen.UiEvent) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = if (item.isOverridden) 4.dp else 2.dp,
        shape = RoundedCornerShape(12.dp),
        border = if (item.isOverridden) BorderStroke(2.dp, MaterialTheme.colors.secondary) else null,
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Override badge
            if (item.isOverridden) {
                Row(
                    modifier =
                        Modifier
                            .background(
                                MaterialTheme.colors.secondary.copy(alpha = 0.15f),
                                RoundedCornerShape(4.dp),
                            ).padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "⚠",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.secondary,
                    )
                    Text(
                        text = "LOCALLY OVERRIDDEN",
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondary,
                    )
                }
                Spacer(Modifier.height(8.dp))
            }

            // Header row with name and switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.featureFlag.displayName,
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        color =
                            if (item.isOverridden) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface
                            },
                    )

                    if (item.description.isNotEmpty()) {
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        )
                    }
                }

                Switch(
                    checked = item.effectiveValue,
                    onCheckedChange = { isEnabled ->
                        onEvent(
                            FeatureFlagsListScreen.UiEvent.OnFlagToggled(
                                key = item.featureFlag.key,
                                isEnabled = isEnabled,
                            ),
                        )
                    },
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor =
                                if (item.isOverridden) {
                                    MaterialTheme.colors.secondary
                                } else {
                                    MaterialTheme.colors.primary
                                },
                        ),
                )
            }

            Spacer(Modifier.height(12.dp))

            // Clear value breakdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Remote value chip
                ValueChip(
                    label = "Remote Flag",
                    value = if (item.remoteValue) "ON" else "OFF",
                    color = if (item.remoteValue) Color(0xFF4CAF50) else Color(0xFF757575),
                    isHighlighted = !item.isOverridden,
                )

                // Current/Effective value chip
                ValueChip(
                    label = "Current",
                    value = if (item.effectiveValue) "ON" else "OFF",
                    color = if (item.effectiveValue) Color(0xFF2196F3) else Color(0xFF757575),
                    isHighlighted = true,
                )

                // Override chip (if applicable)
                if (item.isOverridden && item.localOverrideValue != null) {
                    ValueChip(
                        label = "Override",
                        value = if (item.localOverrideValue) "ON" else "OFF",
                        color = MaterialTheme.colors.secondary,
                        isHighlighted = true,
                    )
                }

                Spacer(Modifier.weight(1f))

                // Reset button for overridden flags
                if (item.isOverridden) {
                    OutlinedButton(
                        onClick = {
                            onEvent(
                                FeatureFlagsListScreen.UiEvent.OnResetFlag(
                                    key = item.featureFlag.key,
                                ),
                            )
                        },
                        modifier = Modifier.height(32.dp),
                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colors.secondary,
                            ),
                    ) {
                        Text("↶", fontSize = 14.sp)
                        Spacer(Modifier.width(4.dp))
                        Text("Reset", fontSize = 12.sp)
                    }
                }
            }

            // Evaluation source and dependencies info
            if (item.evaluationSource != null) {
                Spacer(Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text =
                            "Source: ${item.evaluationSource.name.lowercase().replace("_", " ")}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    )
                }
            }
        }
    }
}

@Composable
private fun DebugInfoCard(
    debugInfo: FeatureFlagsListScreen.DebugInfo,
    onEvent: (FeatureFlagsListScreen.UiEvent) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.5f),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Debug Information",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    "🐛",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DebugMetric("Total Flags", debugInfo.totalFlags.toString())
                DebugMetric("Overridden", debugInfo.overriddenCount.toString())
                DebugMetric("Cache Hits", debugInfo.cacheHits.toString())
            }

            Text(
                text = "Last Updated: ${debugInfo.lastUpdated}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            )
        }
    }
}

@Composable
private fun DebugMetric(
    label: String,
    value: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun CategoryHeader(
    category: FlagCategory,
    count: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text =
                category.name
                    .replace("_", " ")
                    .lowercase()
                    .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
        )

        Spacer(Modifier.width(8.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
        ) {
            Text(
                text = count.toString(),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary,
            )
        }

        Spacer(Modifier.weight(1f))

        Divider(
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
        )
    }
}

/**
 * Format timestamp to a more readable format Converts from ISO instant format to relative time
 * (e.g., "2 minutes ago")
 */
private fun formatTimestamp(timestamp: String): String {
    try {
        val parts = timestamp.split("T")
        if (parts.size == 2) {
            val time = parts[1].substringBefore(".").substringBefore("Z")
            return "Today at $time UTC"
        }
    } catch (e: Exception) {
        // Fall back to showing the raw timestamp
    }
    return timestamp
}
