package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.GraySuit
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.screens.walletdetail.WalletDetailUiState

fun LazyListScope.tokenItem(state: WalletDetailUiState.Content) {
    item {
        WalletDetailCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.tokens),
                        style = TextStyle(
                            fontFamily = inter,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = White
                        )
                    )
                    Text(
                        text = "(≈ Ɲ1,718) $1.41",
                        style = TextStyle(
                            fontFamily = inter,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = GraySuit
                        )
                    )
                }
            }
        }
    }
}