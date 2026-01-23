package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.core.ui.utils.collapsibleCard
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.sharedfeatures.core.resources.R

fun LazyListScope.tracksItem(
    tracks: List<NFTTrack>,
    isExpanded: Boolean,
    headerShape: RoundedCornerShape,
    onExitFinished: () -> Unit,
    onClick: () -> Unit,
) {
    if (tracks.isNotEmpty()) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        collapsibleCard(
            items = tracks,
            isExpanded = isExpanded,
            onExitFinished = onExitFinished,
            header = {
                WalletCardHeader(
                    title = stringResource(R.string.wallet_detail_music_header),
                    headerShape = headerShape,
                    expanded = isExpanded,
                    onClick = onClick,
                )
            },
            content = { WalletItem(it) },
        )
    }
}
