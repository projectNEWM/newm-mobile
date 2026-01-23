package io.newm.screens.walletdetail.view

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ContentCopy
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.sharedfeatures.core.resources.R
import io.newm.sharedfeatures.theme.GraySuit
import io.newm.sharedfeatures.theme.White
import kotlinx.coroutines.launch

fun LazyListScope.addressItem(address: String) {
    item { Spacer(modifier = Modifier.height(8.dp)) }
    item {
        val clipboard = LocalClipboard.current
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        WalletDetailCard {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.width(150.dp)) {
                    Text(
                        text = stringResource(R.string.address),
                        style =
                            TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = White,
                            ),
                    )
                    Text(
                        text = address,
                        maxLines = 1,
                        overflow = TextOverflow.MiddleEllipsis,
                        style =
                            TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = GraySuit,
                            ),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier =
                        Modifier
                            .size(40.dp)
                            .background(color = GraySuit, shape = RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        clipboard.setClipEntry(
                                            ClipEntry(
                                                ClipData.newPlainText(
                                                    context.getString(
                                                        R.string.wallets_copy_address_label,
                                                        address,
                                                    ),
                                                    address,
                                                ),
                                            ),
                                        )
                                    }
                                },
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp).scale(scaleX = -1f, scaleY = 1f),
                        imageVector = Icons.TwoTone.ContentCopy,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
