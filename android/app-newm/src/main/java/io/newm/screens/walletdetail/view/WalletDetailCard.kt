package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.sharedfeatures.theme.Gray16

@Composable
fun WalletDetailCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().heightIn(min = 64.dp),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = Gray16,
    ) {
        content()
    }
}
