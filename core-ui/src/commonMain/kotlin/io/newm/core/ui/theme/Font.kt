package io.newm.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import io.newm.core.ui.Res
import io.newm.core.ui.inter_bold
import io.newm.core.ui.inter_normal
import io.newm.core.ui.inter_semibold
import io.newm.core.ui.montserrat_medium
import io.newm.core.ui.raleway_bold
import io.newm.core.ui.raleway_medium
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
