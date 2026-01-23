package io.newm.sharedfeatures.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.sharedfeatures.core.resources.Res
import io.newm.sharedfeatures.core.resources.check_icon_description
import io.newm.sharedfeatures.theme.DarkViolet
import io.newm.sharedfeatures.theme.Pinkish
import io.newm.sharedfeatures.theme.Purple
import io.newm.sharedfeatures.ui.utils.iconGradient
import org.jetbrains.compose.resources.stringResource

private val enabledButtonGradient = iconGradient(DarkViolet, Pinkish)

private val disabledButtonGradient =
    iconGradient(DarkViolet.copy(alpha = 0.4f), Pinkish.copy(alpha = 0.4f))

@Composable
fun NewmButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    selectedBrush: Brush = enabledButtonGradient,
    unselectedBrush: Brush = disabledButtonGradient,
    onClick: () -> Unit = {},
    content: @Composable (RowScope.() -> Unit),
) {
    val newmModifier =
        modifier.then(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .height(40.dp)
                .then(
                    if (isSelected) {
                        Modifier.background(selectedBrush)
                    } else {
                        Modifier.background(unselectedBrush)
                    },
                ),
        )

    Button(
        onClick = onClick,
        modifier = newmModifier,
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        content = content,
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconPainter: Painter? = null,
) {
    Row(
        modifier =
            modifier
                .height(40.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(brush = if (enabled) enabledButtonGradient else disabledButtonGradient)
                .then(if (enabled) Modifier.clickable { onClick.invoke() } else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        iconPainter
            ?.takeIf { enabled }
            ?.let {
                Icon(
                    painter = it,
                    contentDescription = stringResource(Res.string.check_icon_description),
                    tint = White,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        Text(
            text = text,
            color = if (enabled) MaterialTheme.colors.onPrimary else Gray,
            fontFamily = FontFamily.Default,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun SecondaryButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundBrush: Brush = defaultButtonGradient,
    textStyle: TextStyle = defaultButtonLabelStyle,
    enabled: Boolean = true,
    iconPainter: Painter? = null,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundBrush)
                .fillMaxWidth()
                .height(40.dp),
        elevation = null,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            iconPainter?.let {
                Icon(painter = it, contentDescription = null, tint = Purple)
                Spacer(modifier = Modifier.size(8.dp))
            }
            Text(text = label, style = textStyle)
        }
    }
}

private val defaultButtonGradient =
    iconGradient(DarkViolet.copy(alpha = 0.08f), Pinkish.copy(alpha = 0.08f))

private val defaultButtonLabelStyle
    @Composable
    get() =
        TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            color = Purple,
        )
