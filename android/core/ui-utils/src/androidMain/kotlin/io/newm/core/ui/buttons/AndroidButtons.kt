package io.newm.core.ui.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    labelResId: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
    iconResId: Int? = null,
) {
    PrimaryButton(
        text = stringResource(labelResId),
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        iconPainter = iconResId?.let { painterResource(it) },
    )
}

@Composable
fun SecondaryButton(
    labelResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    iconResId: Int? = null,
) {
    SecondaryButton(
        label = stringResource(labelResId),
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        iconPainter = iconResId?.let { painterResource(it) },
    )
}
