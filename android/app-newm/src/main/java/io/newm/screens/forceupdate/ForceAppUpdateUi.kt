package io.newm.screens.forceupdate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.feature.login.screen.LoginPageMainImage
import io.newm.shared.public.analytics.NewmAppEventLogger

@Composable
fun ForceAppUpdateUi(
    state: ForceAppUpdateState,
    eventLogger: NewmAppEventLogger,
    modifier: Modifier = Modifier
) {
    when (state) {
        is ForceAppUpdateState.Content -> {
            eventLogger.logPageLoad("Force App Update")
            ForceAppUpdateContent(
                state = state,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ForceAppUpdateContent(
    state: ForceAppUpdateState.Content,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(.25f))

            LoginPageMainImage(R.drawable.ic_newm_logo)

            Text(
                text = stringResource(id = R.string.force_app_update_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.force_app_update_message),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))


            PrimaryButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = stringResource(id = R.string.force_app_update_button),
                onClick = { state.eventSink(ForceAppUpdateEvent.OnForceUpdate) },
            )
        }
    }
}

@Preview
@Composable
fun ForceAppUpdateUiDarkModePreview() {
    NewmTheme(darkTheme = true) {
        ForceAppUpdateUi(
            state = ForceAppUpdateState.Content(eventSink = {}),
            eventLogger = NewmAppEventLogger()
        )
    }
}

@Preview
@Composable
fun ForceAppUpdateUiPreview() {
    NewmTheme(darkTheme = false) {
        ForceAppUpdateUi(
            state = ForceAppUpdateState.Content(eventSink = {}),
            eventLogger = NewmAppEventLogger()
        )
    }
}