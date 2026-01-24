package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.ui.theme.White
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.screens.walletdetail.WalletDetailEvent
import io.newm.screens.walletdetail.WalletDetailUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletDetailUi(
    modifier: Modifier = Modifier,
    state: WalletDetailUiState,
) {
    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize()),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.statusBars,
                backgroundColor = Color.Transparent,
                title = { WalletDetailTitle(state.walletName) },
                navigationIcon = {
                    WalletDetailBackNav { state.eventSink(WalletDetailEvent.OnBack) }
                },
            )
        },
    ) { padding ->
        val pullRefreshState =
            rememberPullRefreshState(
                refreshing = state.isSyncing,
                onRefresh = { state.eventSink(WalletDetailEvent.OnRefresh) },
            )

        Box(
            modifier = Modifier.fillMaxSize().padding(padding).pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is WalletDetailUiState.Error -> Text(text = "ERROR")

                // todo need real error screen
                is WalletDetailUiState.Loading -> CircularProgressIndicator()

                is WalletDetailUiState.Content -> ContentWrapper(state)
            }

            PullRefreshIndicator(
                state = pullRefreshState,
                refreshing = state.isSyncing,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun ContentWrapper(state: WalletDetailUiState.Content) {
    var trackExpanded by remember { mutableStateOf(true) }
    var trackHeaderShape by remember { mutableStateOf(HeaderExpandedShape) }
    var streamExpanded by remember { mutableStateOf(true) }
    var streamHeaderShape by remember { mutableStateOf(HeaderExpandedShape) }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        addressItem(state.walletConnection.stakeAddress)
        tokenItem(state.claimableTokenAmount)
        tracksItem(
            tracks = state.nftTracks,
            isExpanded = trackExpanded,
            headerShape = trackHeaderShape,
            onExitFinished = { trackHeaderShape = HeaderCollapsedShape },
            onClick = {
                trackExpanded = !trackExpanded
                if (trackExpanded) {
                    trackHeaderShape = HeaderExpandedShape
                }
            },
        )
        streamTokensItem(
            tokens = state.streamTokens,
            isExpanded = streamExpanded,
            headerShape = streamHeaderShape,
            onExitFinished = { streamHeaderShape = HeaderCollapsedShape },
            onClick = {
                streamExpanded = !streamExpanded
                if (streamExpanded) {
                    streamHeaderShape = HeaderExpandedShape
                }
            },
        )
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun WalletDetailTitle(walletName: String) {
    Text(
        text = walletName,
        color = White,
        style =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
    )
}

@Composable
private fun WalletDetailBackNav(onClick: () -> Unit) {
    ScrimCircle {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back_description),
            )
        }
    }
}

private val HeaderExpandedShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
private val HeaderCollapsedShape = RoundedCornerShape(4.dp)
