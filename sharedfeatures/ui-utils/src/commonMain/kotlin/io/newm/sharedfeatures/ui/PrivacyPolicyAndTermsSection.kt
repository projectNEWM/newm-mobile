package io.newm.sharedfeatures.ui

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
import io.newm.sharedfeatures.core.resources.Res
import io.newm.sharedfeatures.core.resources.and
import io.newm.sharedfeatures.core.resources.privacy_continue
import io.newm.sharedfeatures.core.resources.privacy_policy
import io.newm.sharedfeatures.core.resources.terms_of_service
import org.jetbrains.compose.resources.stringResource

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
            append(stringResource(Res.string.privacy_continue))

            pushLink(
                LinkAnnotation.Clickable(
                    tag = "privacy",
                    styles = linkStyles,
                    linkInteractionListener = { onPrivacyPolicyClicked() },
                ),
            )
            append(stringResource(Res.string.privacy_policy))
            pop()

            append(stringResource(Res.string.and))

            pushLink(
                LinkAnnotation.Clickable(
                    tag = "terms",
                    styles = linkStyles,
                    linkInteractionListener = { onTermsOfServiceClicked() },
                ),
            )
            append(stringResource(Res.string.terms_of_service))
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
