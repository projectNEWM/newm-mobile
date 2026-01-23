package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.sharedfeatures.core.resources.R
import io.newm.sharedfeatures.theme.GraySuit
import io.newm.sharedfeatures.theme.NewmTheme
import io.newm.sharedfeatures.theme.White

fun LazyListScope.tokenItem(claimable: Long) {
    item { Spacer(modifier = Modifier.height(8.dp)) }
    item {
        WalletDetailCard {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.tokens),
                        style =
                            TextStyle(
                                fontFamily = FontFamily.Default,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = White,
                            ),
                    )
                    Text(
                        text = "$$claimable",
                        style =
                            TextStyle(
                                fontFamily = FontFamily.Default,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = GraySuit,
                            ),
                    )
                }
            }
        }
    }
}

@Preview(device = "id:pixel_9_pro_xl", showSystemUi = true)
@Composable
private fun Preview() {
    NewmTheme { LazyColumn { tokenItem(123) } }
}
