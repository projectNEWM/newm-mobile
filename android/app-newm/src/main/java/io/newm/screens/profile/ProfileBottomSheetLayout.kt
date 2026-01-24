package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.newm.BuildConfig
import io.newm.core.resources.R
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.text.versionTextStyle
import io.newm.core.ui.theme.Black90

@Composable
fun ProfileBottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    onLogout: () -> Unit,
    onBottomSheetVisible: () -> Unit,
    onShowTermsAndConditions: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetContent = {
            LaunchedEffect(Unit) { onBottomSheetVisible() }
            Column(
                modifier =
                    Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(16.dp),
            ) {
                SecondaryButton(
                    label = stringResource(R.string.privacy_policy),
                    onClick = onShowPrivacyPolicy,
                )
                Spacer(modifier = Modifier.height(16.dp))
                SecondaryButton(
                    label = stringResource(R.string.profile_terms_and_condition),
                    onClick = onShowTermsAndConditions,
                )
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    text = stringResource(id = R.string.user_account_logout),
                    onClick = onLogout,
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppVersion()
            }
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        scrimColor = Black90,
        content = content,
    )
}

@Composable
private fun AppVersion() {
    Column {
        Text(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Version " + BuildConfig.VERSION_NAME,
            style = versionTextStyle.copy(fontWeight = FontWeight.Bold),
        )
        Text(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Build: " + BuildConfig.VERSION_CODE,
            style = versionTextStyle,
        )
    }
}
