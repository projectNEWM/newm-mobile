package io.projectnewm.feature.login.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.projectnewm.core.ui.utils.HotPinkBrush

@Composable
fun VerificationScreen(
    viewModel: SignupViewModel,
    onVerificationComplete: () -> Unit,
) {
    PreLoginArtistBackgroundContentTemplate {
        Text(text = "Check your email!")
        Spacer(modifier = Modifier.height(16.dp))

        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.setEmailVerificationCode(it)
            },
            label = {
                Text("Enter Verification Code:")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(BorderStroke(1.dp, HotPinkBrush()))
                .background(brush = HotPinkBrush())
                .clickable {
                    viewModel.verifyAccount()
//                    onVerificationComplete()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Enter",
                color = MaterialTheme.colors.onSurface,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}