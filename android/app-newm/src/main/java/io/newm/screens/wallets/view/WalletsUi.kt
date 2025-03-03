package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.SteelPink
import io.newm.core.theme.raleway
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.utils.textGradient
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsEvent.OnBack
import io.newm.screens.wallets.WalletsUiState
import io.newm.shared.public.analytics.NewmAppEventLogger

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletsUi(
    state: WalletsUiState,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.statusBars,
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = stringResource(id = R.string.wallets_screen_topbar_title),
                        style = TextStyle(
                            fontFamily = raleway,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            brush = textGradient(SteelPink, CerisePink)
                        )
                    )
                },
                navigationIcon = {
                    ScrimCircle {
                        IconButton(
                            onClick = { state.eventSink(OnBack) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_description),

                                )
                        }
                    }
                }
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
                is WalletsUiState.Empty -> {
                    /* TODO */
                    Text(text = "No wallets connected")
                }

                is WalletsUiState.Content -> {
                    Content(state)
                }
            }
            PullRefreshIndicator(
                state = pullRefreshState,
                refreshing = state.isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun BoxScope.Content(state: WalletsUiState.Content) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = state.wallets,
                key = { it.id }
            ) {
                WalletRow(
                    connection = it,
                    onOptionsClick = {} // TODO open bottom sheet
                )
            }
        }
        SecondaryButton(
            labelResId = R.string.wallets_screen_disconnect_all_wallets,
            onClick = {} // TODO disconnect all wallets
        )
        PrimaryButton(
            text = stringResource(R.string.wallets_screen_connect_new_wallet),
            onClick = {} // TODO open connect wallet screen
        )
    }
}