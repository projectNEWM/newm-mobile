package io.newm.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import newm_mobile.android.core.ui_utils.generated.resources.Res
import newm_mobile.android.core.ui_utils.generated.resources.newm_login_logo_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingMainImage(painter: Painter) {
    Image(
        modifier = Modifier
            .width(250.dp)
            .height(250.dp),
        painter = painter,
        contentDescription = stringResource(Res.string.newm_login_logo_description),
        contentScale = ContentScale.Crop,
    )
}