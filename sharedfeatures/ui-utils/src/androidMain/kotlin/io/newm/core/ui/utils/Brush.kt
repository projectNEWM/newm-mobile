package io.newm.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import io.newm.sharedfeatures.theme.GradientBlue
import io.newm.sharedfeatures.theme.GradientDarkBlue
import io.newm.sharedfeatures.theme.GradientGreen
import io.newm.sharedfeatures.theme.GradientOrange
import io.newm.sharedfeatures.theme.GradientPink
import io.newm.sharedfeatures.theme.GradientPurple
import io.newm.sharedfeatures.theme.GradientRed
import io.newm.sharedfeatures.theme.GradientYellow
import io.newm.sharedfeatures.theme.OrangeSongRing1
import io.newm.sharedfeatures.theme.OrangeSongRing2
import io.newm.sharedfeatures.theme.PurpleArtistBackground1
import io.newm.sharedfeatures.theme.PurpleArtistBackground2
import io.newm.sharedfeatures.theme.PurpleArtistRing1

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
