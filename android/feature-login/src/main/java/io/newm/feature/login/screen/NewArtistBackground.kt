package io.newm.feature.login.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R

@Composable
fun PreLoginArtistBackgroundContentTemplate(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
    ) {
        LoginPageBackgroundImage(backgroundImage = R.drawable.bg_login)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            LoginPageMainImage(R.drawable.ic_newm_logo)
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}