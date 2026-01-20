package io.newm.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import newm_mobile.android.core.theme.generated.resources.inter_bold
import newm_mobile.android.core.theme.generated.resources.inter_normal
import newm_mobile.android.core.theme.generated.resources.inter_semibold
import newm_mobile.android.core.theme.generated.resources.montserrat_medium
import newm_mobile.android.core.theme.generated.resources.raleway_bold
import newm_mobile.android.core.theme.generated.resources.raleway_medium
import org.jetbrains.compose.resources.Font
import newm_mobile.android.core.theme.generated.resources.Res as R

val raleway
    @Composable
    get() =
        FontFamily(
            Font(R.font.raleway_bold, weight = FontWeight.Bold),
            Font(R.font.raleway_medium, weight = FontWeight.Medium),
            // TODO: More fonts
        )

val montserrat
    @Composable
    get() =
        FontFamily(
            Font(R.font.montserrat_medium, weight = FontWeight.Medium),
            // TODO: More fonts
        )

val inter
    @Composable
    get() =
        FontFamily(
            Font(R.font.inter_normal, weight = FontWeight.Normal),
            Font(R.font.inter_semibold, weight = FontWeight.SemiBold),
            Font(R.font.inter_bold, weight = FontWeight.Bold),
            // TODO: More fonts
        )
