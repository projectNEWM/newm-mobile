package io.newm.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.*

@Composable
fun ThisWeekCarousel(
    followers: Int,
    royalties: Double,
    earnings: Double,
    onViewAll: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title_this_week),
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Gray100
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.view_all),
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Purple,
                modifier = Modifier.clickable { onViewAll() })
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .horizontalScroll(rememberScrollState()),
        ) {
            ThisWeekCard(
                imageRes = R.drawable.ic_followers,
                title = stringResource(R.string.title_this_week_followers, followers),
                subtitle = stringResource(R.string.subtitle_this_week_followers)
            )
            ThisWeekCard(
                imageRes = R.drawable.ic_royalties,
                title = stringResource(R.string.title_this_week_royalties, royalties),
                subtitle = stringResource(R.string.subtitle_this_week_royalties)
            )
            ThisWeekCard(
                imageRes = R.drawable.ic_earnings,
                title = stringResource(R.string.title_this_week_earnings, earnings),
                subtitle = stringResource(R.string.subtitle_this_week_earnings)
            )
        }
    }
}

@Composable
private fun ThisWeekCard(
    imageRes: Int,
    title: String,
    subtitle: String,
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .size(120.dp),
        backgroundColor = Gray600,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = imageRes),
                contentDescription = null,
            )
            Text(
                text = title,
                modifier = Modifier.padding(top = 12.dp),
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = subtitle,
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Gray100
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThisWeekCarouselPreview() {
    NewmTheme {
        ThisWeekCarousel(followers = 12, royalties = 51.56, earnings = 2.15, onViewAll = {})
    }
}
