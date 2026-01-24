package io.newm.sharedfeatures.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import newm_mobile.sharedfeatures.ui_utils.generated.resources.Res
import newm_mobile.sharedfeatures.ui_utils.generated.resources.inter_bold
import newm_mobile.sharedfeatures.ui_utils.generated.resources.inter_normal
import newm_mobile.sharedfeatures.ui_utils.generated.resources.inter_semibold
import newm_mobile.sharedfeatures.ui_utils.generated.resources.montserrat_medium
import newm_mobile.sharedfeatures.ui_utils.generated.resources.raleway_bold
import newm_mobile.sharedfeatures.ui_utils.generated.resources.raleway_medium
import org.jetbrains.compose.resources.Font

val raleway
    @Composable
    get() =
        FontFamily(
            Font(Res.font.raleway_bold, weight = FontWeight.Bold),
            Font(Res.font.raleway_medium, weight = FontWeight.Medium),
        )

val montserrat
    @Composable get() = FontFamily(Font(Res.font.montserrat_medium, weight = FontWeight.Medium))

val inter
    @Composable
    get() =
        FontFamily(
            Font(Res.font.inter_normal, weight = FontWeight.Normal),
            Font(Res.font.inter_semibold, weight = FontWeight.SemiBold),
            Font(Res.font.inter_bold, weight = FontWeight.Bold),
        )
