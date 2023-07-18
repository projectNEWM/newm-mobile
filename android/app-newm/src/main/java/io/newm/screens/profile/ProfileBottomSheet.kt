package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.R
import io.newm.core.theme.*
import io.newm.core.ui.buttons.SecondaryButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileBottomSheet(
    sheetState: ModalBottomSheetState,
    onLogout: () -> Unit,
    onShowTermsAndConditions: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onShowDocuments: () -> Unit,
    onShowAskTheCommunity: () -> Unit,
    onShowFaq: () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Divider(
                    thickness = 1.dp,
                    color = Gray400
                )
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = stringResource(id = R.string.profile_help_and_support),
                        fontFamily = inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Gray100
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        textColor = White,
                        borderColor = Gray500,
                        text = stringResource(R.string.profile_faq),
                        onClick = onShowFaq
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        textColor = White,
                        borderColor = Gray500,
                        text = stringResource(R.string.profile_ask_the_community),
                        onClick = onShowAskTheCommunity
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Text(
                        text = stringResource(id = R.string.profile_company_related),
                        fontFamily = inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Gray100
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        textColor = White,
                        borderColor = Gray500,
                        text = stringResource(R.string.profile_documents),
                        onClick = onShowDocuments,

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        textColor = White,
                        borderColor = Gray500,
                        text = stringResource(R.string.profile_privacy_policy),
                        onClick = onShowPrivacyPolicy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        textColor = White,
                        borderColor = Gray500,
                        text = stringResource(R.string.profile_terms_and_condition),
                        onClick = onShowTermsAndConditions
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    SecondaryButton(
                        textColor = SystemRed,
                        borderColor = SystemRed,
                        text = stringResource(R.string.profile_logout),
                        onClick = onLogout
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        },
        scrimColor = Black90,
        content = { }
    )
}