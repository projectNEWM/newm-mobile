package io.newm.feature.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R

@Composable
fun PreLoginArtistBackgroundContentTemplate(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        header()
        Spacer(modifier = Modifier.height(70.dp))
        LoginPageMainImage(R.drawable.ic_newm_logo)
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }

    if (isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}
