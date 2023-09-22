package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
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
                        text = stringResource(R.string.profile_faq),
                        textColor = White,
                        borderColor = Gray500,
                        onClick = onShowFaq
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        text = stringResource(R.string.profile_ask_the_community),
                        textColor = White,
                        borderColor = Gray500,
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
                        text = stringResource(R.string.profile_documents),
                        textColor = White,
                        borderColor = Gray500,
                        onClick = onShowDocuments,

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        text = stringResource(R.string.profile_privacy_policy),
                        textColor = White,
                        borderColor = Gray500,
                        onClick = onShowPrivacyPolicy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SecondaryButton(
                        text = stringResource(R.string.profile_terms_and_condition),
                        textColor = White,
                        borderColor = Gray500,
                        onClick = onShowTermsAndConditions
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    SecondaryButton(
                        text = stringResource(R.string.profile_logout),
                        textColor = SystemRed,
                        borderColor = SystemRed,
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
