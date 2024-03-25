package io.newm.core.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.GlassSmith
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.LightSkyBlue
import io.newm.core.theme.OceanGreen
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.ui.utils.iconGradient

private val buttonGradient =
    iconGradient(OceanGreen.copy(alpha = 0.08f), LightSkyBlue.copy(alpha = 0.08f))

@Composable
fun ConnectWalletPanel(
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Gray16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.connect_wallet_label1),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = White
            )
            Text(
                text = stringResource(id = R.string.connect_wallet_label2),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = GraySuit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(buttonGradient)
                    .clip(RoundedCornerShape(8.dp)),
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            )
            {
                Text(
                    text = stringResource(id = R.string.connect_wallet_button_label),
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = GlassSmith
                )
            }
        }
    }
}