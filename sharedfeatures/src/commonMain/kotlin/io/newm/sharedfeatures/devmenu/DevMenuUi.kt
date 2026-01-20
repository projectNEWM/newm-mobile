package io.newm.sharedfeatures.devmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import io.newm.sharedfeatures.screens.DevMenuItem
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Composable
fun DevMenuUi(
    state: DevMenuMainScreen.UiState,
    modifier: Modifier = Modifier,
) {
    val onEvent = (state as? DevMenuMainScreen.UiState.Content)?.onEvent ?: {}

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Developer Menu")
                        Text(
                            text = "Debug tools and utilities",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                        )
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = { onEvent(DevMenuMainScreen.UiEvent.OnBack) }) {
                        Text("←", style = MaterialTheme.typography.h5) // Back arrow
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp,
            )
        },
    ) { padding ->
        when (state) {
            is DevMenuMainScreen.UiState.Content -> {
                DevMenuScreenContent(
                    modifier = Modifier.padding(padding),
                    menuItems = state.menuItems,
                    onEvent = onEvent,
                )
            }

            DevMenuMainScreen.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                        Text(
                            text = "Loading developer tools...",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DevMenuScreenContent(
    modifier: Modifier,
    menuItems: List<DevMenuItem>,
    onEvent: (DevMenuMainScreen.UiEvent) -> Unit,
) {
    LazyColumn(
        modifier =
            modifier.fillMaxSize().background(MaterialTheme.colors.background).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            // Welcome header card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "🛠️",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(end = 12.dp),
                    )
                    Column {
                        Text(
                            text = "Development Tools",
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Access debugging and testing utilities",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }

        items(menuItems) { item ->
            DevMenuListItem(
                item = item,
                onClick = { onEvent(DevMenuMainScreen.UiEvent.OnItemClick(item.screen)) },
            )
        }

        item {
            // Footer disclaimer
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.5f),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "⚠️ These tools are for development only",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    )
                }
            }
        }
    }
}

@Composable
fun DevMenuListItem(
    item: DevMenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier =
            modifier.fillMaxWidth().clickable {
                isPressed = true
                onClick()
                // Reset pressed state
                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                    delay(150)
                    isPressed = false
                }
            },
        elevation = if (isPressed) 8.dp else 4.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor =
            if (isPressed) {
                MaterialTheme.colors.primary.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colors.surface
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f),
            ) {
                // Smart icon based on title
                Text(
                    text =
                        when (item.title) {
                            "Feature Flags" -> "🚩"
                            "Debug Info" -> "🐛"
                            "Network Logs" -> "🌐"
                            "Cache Manager" -> "🗂️"
                            "Performance" -> "⚡"
                            else -> "🔧"
                        },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary,
                )

                // Title and description
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Medium,
                    )

                    // Show description if it exists
                    if (item.description.isNotEmpty()) {
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        )
                    }
                }
            }

            // Arrow indicator
            Text(
                text = "→",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
            )
        }
    }
}

class DevMenuUiFactory
    @Inject
    constructor() : Ui.Factory {
        override fun create(
            screen: Screen,
            context: CircuitContext,
        ): Ui<*>? =
            when (screen) {
                DevMenuMainScreen -> {
                    ui<DevMenuMainScreen.UiState> { state, modifier -> DevMenuUi(state, modifier) }
                }

                is FeatureFlagsListScreen -> {
                    ui<FeatureFlagsListScreen.UiState> { state, modifier ->
                        FeatureFlagsListUi(state, modifier)
                    }
                }

                else -> {
                    null
                }
            }
    }
