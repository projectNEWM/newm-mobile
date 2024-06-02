package io.newm.core.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.Pinkish
import io.newm.core.ui.utils.iconGradient

private val enabledButtonGradient =
    iconGradient(DarkViolet, Pinkish)

private val disabledButtonGradient =
    iconGradient(DarkViolet.copy(alpha = 0.4f), Pinkish.copy(alpha = 0.4f))

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    enabledIconRes: Int? = null,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(brush = if (enabled) enabledButtonGradient else disabledButtonGradient)
            .then(
                if (enabled) Modifier.clickable { onClick.invoke() }
                else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        enabledIconRes?.takeIf { enabled }?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = "Check",
                tint = White,
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(
            text = text,
            color = if (enabled) MaterialTheme.colors.onPrimary else Gray,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    textColor: Color = if (enabled) MaterialTheme.colors.primary else Gray,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        border = ButtonDefaults.outlinedBorder.copy(brush = SolidColor(Gray)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = textColor,
            backgroundColor = Color.Transparent,
            disabledContentColor = Gray,
        ),
    ) {
        Text(text)
    }
}
