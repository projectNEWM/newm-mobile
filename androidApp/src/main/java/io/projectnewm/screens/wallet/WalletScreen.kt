package io.projectnewm.screens.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController

internal const val TAG_WALLET_SCREEN = "TAG_WALLET_SCREEN"

@Composable
fun WalletScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .testTag(TAG_WALLET_SCREEN)
    ) {
        OutlinedButton(
            onClick = { throw RuntimeException("Test Crash") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Test Crash")
        }
    }
}