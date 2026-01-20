package io.newm.core.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import newm_mobile.android.core.ui_utils.generated.resources.and
import newm_mobile.android.core.ui_utils.generated.resources.privacy_continue
import newm_mobile.android.core.ui_utils.generated.resources.privacy_policy
import newm_mobile.android.core.ui_utils.generated.resources.terms_of_service
import org.jetbrains.compose.resources.stringResource
import newm_mobile.android.core.ui_utils.generated.resources.Res as R

@Composable
fun PrivacyPolicyAndTermsSection(
    modifier: Modifier = Modifier,
    onPrivacyPolicyClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
) {
    val linkStyles =
        TextLinkStyles(
            style =
                SpanStyle(
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline,
                ),
        )

    val annotatedText =
        buildAnnotatedString {
            append(stringResource(R.string.privacy_continue))

            pushLink(
                LinkAnnotation.Clickable(
                    tag = "privacy",
                    styles = linkStyles,
                    linkInteractionListener = { onPrivacyPolicyClicked() },
                ),
            )
            append(stringResource(R.string.privacy_policy))
            pop()

            append(stringResource(R.string.and))

            pushLink(
                LinkAnnotation.Clickable(
                    tag = "terms",
                    styles = linkStyles,
                    linkInteractionListener = { onTermsOfServiceClicked() },
                ),
            )
            append(stringResource(R.string.terms_of_service))
            pop()
        }

    Text(
        text = annotatedText,
        style =
            MaterialTheme.typography.body2.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground,
            ),
        modifier = modifier,
    )
}
