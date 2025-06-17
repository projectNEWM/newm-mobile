package io.newm.sharedfeatures.devmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FeatureFlagsListUi(
    state: FeatureFlagsListScreen.UiState,
    modifier: Modifier = Modifier
) {
    val onEvent = (state as? FeatureFlagsListScreen.UiState.Content)?.onEvent ?: {}

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Feature Flags") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(FeatureFlagsListScreen.UiEvent.OnBack) }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back"
//                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { onEvent(FeatureFlagsListScreen.UiEvent.OnResetAllFlags) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text("Reset All")
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            is FeatureFlagsListScreen.UiState.Content -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.flags) { flagItem ->
                        FeatureFlagRow(item = flagItem, onEvent = onEvent)
                    }
                }
            }
            FeatureFlagsListScreen.UiState.Loading -> {
                // Handle Loading state if necessary
            }
        }
    }
}

@Composable
private fun FeatureFlagRow(
    item: FeatureFlagsListScreen.FeatureFlagListItem,
    onEvent: (FeatureFlagsListScreen.UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.featureFlag.displayName,
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
            )
            Switch(
                checked = item.effectiveValue,
                onCheckedChange = { isEnabled ->
                    onEvent(
                        FeatureFlagsListScreen.UiEvent.OnFlagToggled(
                            key = item.featureFlag.key,
                            isEnabled = isEnabled
                        )
                    )
                }
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val remoteStatus = if (item.remoteValue) "On" else "Off"
            Text(
                text = "Remote: $remoteStatus",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            if (item.isOverridden) {
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "OVERRIDDEN",
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colors.primary
                )
                Spacer(Modifier.weight(1f))
                OutlinedButton(
                    onClick = {
                        onEvent(
                            FeatureFlagsListScreen.UiEvent.OnResetFlag(
                                key = item.featureFlag.key
                            )
                        )
                    },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Reset")
                }
            }
        }
    }
}