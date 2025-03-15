package io.newm.screens.wallets.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
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
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.core.ui.utils.shortToast
import io.newm.core.ui.utils.textGradient
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsEvent.OnBack
import io.newm.screens.wallets.WalletsUiState
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletsUi(
    state: WalletsUiState,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger
) {
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
                        Content(
                            state = state,
                            eventLogger = eventLogger,
                            onOptionsClick = {
                                scope.launch {
                                    selectedWalletId = it
                                    bottomSheetState.show()
                                }
                            }
                        )
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
}

@Composable
private fun BoxScope.Content(
    state: WalletsUiState.Content,
    eventLogger: NewmAppEventLogger,
    onOptionsClick: (String) -> Unit
) {
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
                    onOptionsClick = {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.WALLET_OPTIONS_BUTTON)
                        onOptionsClick(it)
                    }
                )
            }
        }
        SecondaryButton(
            labelResId = R.string.wallets_screen_disconnect_all_wallets,
            onClick = { state.eventSink(WalletsEvent.OnDisconnectAllWallets) }
        )
        ConnectNewWalletButton { state.eventSink(WalletsEvent.OnConnectWallet(it)) }
    }
}

@Composable
private fun ConnectNewWalletButton(onConnectWalletClick: (String) -> Unit) {
    val context = LocalContext.current
    val intent = Intent(context, BarcodeScannerActivity::class.java)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            onActivityResultOk(result, context, onConnectWalletClick)
        }
    }

    val requestPermission = rememberRequestPermissionIntent(
        onGranted = { launcher.launch(intent) },
        onDismiss = { /*TODO*/ }
    )

    PrimaryButton(
        text = stringResource(R.string.wallets_screen_connect_new_wallet),
        onClick = {
            context.doWithPermission(
                onGranted = { launcher.launch(intent) },
                requestPermissionLauncher = requestPermission,
                appPermission = AppPermission.CAMERA
            )
        }
    )
}

private fun onActivityResultOk(
    result: ActivityResult,
    context: Context,
    onConnectWalletClick: (String) -> Unit
) {
    // Handle the returned result here
    val data = result.data
    // Do something with the data
    val newmWalletConnectionId =
        data?.getStringExtra(BarcodeScannerActivity.NEWM_WALLET_CONNECTION_ID).orEmpty()
    // create message
    val message = context.getString(R.string.wallet_link_connected_message, newmWalletConnectionId)
    // show message
    context.shortToast(message)
    onConnectWalletClick(newmWalletConnectionId)
}