package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
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
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.screens.walletdetail.WalletDetailEvent
import io.newm.screens.walletdetail.WalletDetailUiState
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletDetailUi(
    modifier: Modifier = Modifier,
    state: WalletDetailUiState,
    eventLogger: NewmAppEventLogger
) {

    Scaffold(
        modifier = modifier.then(Modifier.fillMaxSize()),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.statusBars,
                backgroundColor = Color.Transparent,
                title = { WalletDetailTitle(state.walletName) },
                navigationIcon = { WalletDetailBackNav { state.eventSink(WalletDetailEvent.OnBack) } }
            )
        }
    ) { padding ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isSyncing,
            onRefresh = { state.eventSink(WalletDetailEvent.OnRefresh) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center
        ) {

            when (state) {
                is WalletDetailUiState.Error -> Text(text = "ERROR")
                is WalletDetailUiState.Loading -> CircularProgressIndicator()
                is WalletDetailUiState.Content -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                    ) {
                        addressItem(state)
                        tokenItem(state)
                    }
                }
            }

            PullRefreshIndicator(
                state = pullRefreshState,
                refreshing = state.isSyncing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun WalletDetailTitle(walletName: String) {
    Text(
        text = walletName,
        color = White,
        style = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    )
}

@Composable
private fun WalletDetailBackNav(onClick: () -> Unit) {
    ScrimCircle {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back_description)
            )
        }
    }
}