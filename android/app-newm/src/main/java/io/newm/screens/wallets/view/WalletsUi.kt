package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.SteelPink
import io.newm.core.theme.raleway
import io.newm.core.ui.utils.textGradient
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsUiState
import io.newm.shared.public.analytics.NewmAppEventLogger
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletsUi(
    state: WalletsUiState,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger
) {
    var isAlertVisible by remember { mutableStateOf(false) }
    var selectedWalletId by remember { mutableStateOf<String?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    WalletsBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        state = bottomSheetState,
        eventLogger = eventLogger,
        onDisconnectWallet = {
            scope.launch {
                state.eventSink(
                    WalletsEvent.OnDisconnectWallet(
                        requireNotNull(selectedWalletId) { "selectedWalletId should not be null" }
                    )
                )
                bottomSheetState.hide()
            }
        },
        onCancel = {
            scope.launch {
                selectedWalletId = null
                bottomSheetState.hide()
            }
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    backgroundColor = Color.Transparent,
                    title = { WalletsTopBarTitle() },
                    navigationIcon = { WalletsBackNav { state.eventSink(WalletsEvent.OnBack) } }
                )
            }
        ) { padding ->

            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isRefreshing,
                onRefresh = { state.eventSink(WalletsEvent.OnRefresh) }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    is WalletsUiState.Loading -> CircularProgressIndicator()
                    is WalletsUiState.Empty -> Empty(state, eventLogger)

                    is WalletsUiState.Content -> {
                        Content(
                            state = state,
                            eventLogger = eventLogger,
                            onOptionsClick = {
                                scope.launch {
                                    selectedWalletId = it
                                    bottomSheetState.show()
                                }
                            },
                            onDisconnectAllClick = { isAlertVisible = true }
                        )
                    }
                }
                PullRefreshIndicator(
                    state = pullRefreshState,
                    refreshing = state.isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            if (isAlertVisible) {
                WalletsAlertDialog(
                    onConfirm = {
                        state.eventSink(WalletsEvent.OnDisconnectAllWallets)
                        isAlertVisible = false
                    },
                    onDismiss = {
                        isAlertVisible = false
                    }
                )
            }
        }
    }
}

@Composable
private fun WalletsBackNav(onClick: () -> Unit) {
    ScrimCircle {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back_description)
            )
        }
    }
}

@Composable
private fun WalletsTopBarTitle() {
    Text(
        text = stringResource(id = R.string.wallets_screen_topbar_title),
        style = TextStyle(
            fontFamily = raleway,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            brush = textGradient(SteelPink, CerisePink)
        )
    )
}