package io.newm.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.GraySuit
import io.newm.core.theme.White
import io.newm.core.theme.inter

@Composable
fun EmptyLibraryContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.library_empty_content_title),
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.library_empty_content_subtitle),
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = GraySuit
        )
    }
}
