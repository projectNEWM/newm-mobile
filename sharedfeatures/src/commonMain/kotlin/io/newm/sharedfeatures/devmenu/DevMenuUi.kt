package io.newm.sharedfeatures.devmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Composable
fun DevMenuUi(state: DevMenuMainScreen.UiState, modifier: Modifier) {
    val onEvent = (state as? DevMenuMainScreen.UiState.Content)?.onEvent ?: {}

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Dev Menu") },
                modifier = Modifier.statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = { onEvent(DevMenuMainScreen.UiEvent.OnBack) }) {
                        Text("Back")
//                        TODO: Find solution for icons
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back"
//                        )
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            is DevMenuMainScreen.UiState.Content -> DevMenuScreenContent(
                modifier = Modifier.padding(padding),
                menuItems = state.menuItems,
                onEvent = onEvent
            )

            DevMenuMainScreen.UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...")
                }
            }
        }
    }
}

@Composable
fun DevMenuScreenContent(
    modifier: Modifier,
    menuItems: List<DevMenuItem>,
    onEvent: (DevMenuMainScreen.UiEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        items(menuItems) { item ->
            DevMenuListItem(
                title = item.title,
                onClick = { onEvent(DevMenuMainScreen.UiEvent.OnItemClick(item.screen)) }
            )
        }
    }
}

@Composable
fun DevMenuListItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
//        TODO: Find solution for icons
//        Icon(
//            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
//            contentDescription = null
//        )
    }
}


class DevMenuUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            DevMenuMainScreen -> ui<DevMenuMainScreen.UiState> { state, modifier ->
                DevMenuUi(state, modifier)
            }

            is FeatureFlagsListScreen -> ui<FeatureFlagsListScreen.UiState> { state, modifier ->
                FeatureFlagsListUi(state, modifier)
            }

            else -> null
        }
    }
}