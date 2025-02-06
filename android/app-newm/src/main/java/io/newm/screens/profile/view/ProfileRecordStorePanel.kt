package io.newm.screens.profile.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray16
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens

private val recordStoreLabelStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Medium,
    color = White
)

private const val RECORD_STORE_URL = "https://recordstore.newm.io/"

@Composable
fun RecordStorePanel(
    eventLogger: NewmAppEventLogger
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Gray16)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(id = R.string.profile_add_music),
                style = recordStoreLabelStyle,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileButton(
                label = stringResource(id = R.string.profile_visit_store),
                onClick = {
                    eventLogger.logPageLoad(AppScreens.RecordStoreScreen.name)
                    eventLogger.logClickEvent(AppScreens.AccountScreen.VISIT_RECORDS_BUTTON)
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(RECORD_STORE_URL)
                        )
                    )
                },
            )
        }
    }
}