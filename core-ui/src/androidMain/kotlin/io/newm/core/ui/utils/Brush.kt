package io.newm.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import io.newm.core.ui.theme.GradientBlue
import io.newm.core.ui.theme.GradientDarkBlue
import io.newm.core.ui.theme.GradientGreen
import io.newm.core.ui.theme.GradientOrange
import io.newm.core.ui.theme.GradientPink
import io.newm.core.ui.theme.GradientPurple
import io.newm.core.ui.theme.GradientRed
import io.newm.core.ui.theme.GradientYellow
import io.newm.core.ui.theme.OrangeSongRing1
import io.newm.core.ui.theme.OrangeSongRing2
import io.newm.core.ui.theme.PurpleArtistBackground1
import io.newm.core.ui.theme.PurpleArtistBackground2
import io.newm.core.ui.theme.PurpleArtistRing1

@Composable
fun HotPinkBrush(): Brush = Brush.horizontalGradient(colors = listOf(PurpleArtistRing1, GradientPurple, PurpleArtistRing1))

@Composable
fun DisabledHotPinkBrush(): Brush =
    Brush.horizontalGradient(
        colors = listOf(PurpleArtistRing1, PurpleArtistRing1, PurpleArtistRing1),
    )

@Composable
fun SongRingBrush(): Brush =
    Brush.horizontalGradient(
        colors =
            listOf(
                OrangeSongRing1,
                OrangeSongRing2,
                OrangeSongRing1,
                OrangeSongRing2,
                OrangeSongRing1,
            ),
    )

@Composable
fun ActionButtonBackgroundBrush(): Brush = Brush.verticalGradient(colors = listOf(PurpleArtistBackground1, PurpleArtistBackground2))

@Composable
fun NewmRainbowBrush(): Brush =
    Brush.horizontalGradient(
        colors =
            listOf(
                GradientBlue,
                GradientDarkBlue,
                GradientPurple,
                GradientPink,
                GradientRed,
                GradientOrange,
                GradientYellow,
                GradientGreen,
            ),
    )
