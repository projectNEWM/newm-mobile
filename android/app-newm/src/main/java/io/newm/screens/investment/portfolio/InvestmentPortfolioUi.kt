package io.newm.screens.investment.portfolio

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.SteelPink
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.theme.raleway
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.utils.textGradient
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.models.NFTTrack

internal const val TAG_INVESTMENT_PORTFOLIO_SCREEN = "TAG_INVESTMENT_PORTFOLIO_SCREEN"

@Composable
fun InvestmentPortfolioUi(
    state: InvestmentPortfolioState,
    modifier: Modifier = Modifier,
    eventLogger: NewmAppEventLogger
) {
    when (state) {
        is InvestmentPortfolioState.Content -> PortfolioScreen(modifier, state)
        InvestmentPortfolioState.Error -> ErrorScreen(
            title = stringResource(R.string.nft_library_error_message),
            message = "Something went wrong"
        )

        InvestmentPortfolioState.Loading -> LoadingScreen()
        InvestmentPortfolioState.ZeroState -> ZeroStateScreen()
    }

}

@Composable
fun PortfolioScreen(
    modifier: Modifier = Modifier,
    content: InvestmentPortfolioState.Content
) {
    val currentContext = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag(TAG_INVESTMENT_PORTFOLIO_SCREEN),
    ) {
        Text(
            text = stringResource(id = R.string.title_investment_portfolio),
            modifier = Modifier.padding(16.dp),
            style = TextStyle(
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                brush = textGradient(SteelPink, CerisePink)
            )
        )

        Box(
            modifier = Modifier
                .padding(all = 16.dp)
                .background(color = Gray16)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.you_have_royalties_to_claim),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "NEWM Tokens: $${content.claimableTokenAmount}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                SecondaryButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    labelResId = R.string.claim,
                    onClick = {
                        Toast.makeText(currentContext, "Coming soon!", Toast.LENGTH_SHORT).show()
                    })
                ListOfStreamTokens(content)
            }
        }
    }
}

@Composable
private fun ListOfStreamTokens(content: InvestmentPortfolioState.Content) {
    Box {
        LazyColumn(
            modifier = Modifier.padding(bottom = 32.dp)
                .fillMaxSize(),
        ) {
            when {
                content.streamTokens.isNotEmpty() -> {
                    items(content.streamTokens, key = { track ->
                        // Use the unique ID as the key
                        track.id
                    }) { track ->
                        StreamTokenRowItem(
                            track = track
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ZeroStateScreen() {

}

@Composable
private fun StreamTokenRowItem(
    track: NFTTrack,
) {
    Box(
        Modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(track.imageUrl)
                    .error(R.drawable.ic_default_track_cover_art)
                    .placeholder(R.drawable.ic_default_track_cover_art)
                    .build(),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = track.title,
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = White
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = track.artists.joinToString(", "),
                        fontFamily = inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = GraySuit
                    )
                }
            }
        }
    }
}