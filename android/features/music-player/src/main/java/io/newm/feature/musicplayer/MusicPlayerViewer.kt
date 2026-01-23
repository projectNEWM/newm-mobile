package io.newm.feature.musicplayer

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.error
import coil3.toBitmap
import io.newm.core.ui.ZoomableImage
import io.newm.core.ui.utils.SwipeDirection
import io.newm.core.ui.utils.SwipeableWrapper
import io.newm.feature.musicplayer.components.MusicPlayerControls
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.share.ShareButton
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent
import io.newm.sharedfeatures.core.resources.R
import io.newm.sharedfeatures.theme.Black
import io.newm.sharedfeatures.theme.DarkPink
import io.newm.sharedfeatures.theme.DarkViolet
import io.newm.sharedfeatures.theme.Gray500
import io.newm.sharedfeatures.theme.GraySuit
import io.newm.sharedfeatures.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val playbackTimeStyle
    @Composable
    get() =
        TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            color = GraySuit,
        )

internal val MusicPlayerBrush = Brush.horizontalGradient(listOf(DarkViolet, DarkPink))

@Composable
internal fun MusicPlayerViewer(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
    onSwipe: (SwipeDirection) -> Unit,
) {
    val song: Track = remember(playbackStatus) { playbackStatus.track } ?: return
    var palette by remember { mutableStateOf<Palette?>(null) }
    val dominantColor = remember(palette) { palette?.dominantColor ?: Black }
    val animatedColor by
        animateColorAsState(
            dominantColor,
            label = "",
            animationSpec = spring(stiffness = StiffnessLow),
        )

    val context = LocalContext.current
    Box(modifier = modifier.background(animatedColor)) {
        val coroutineScope = rememberCoroutineScope()
        SwipeableWrapper(modifier = Modifier.align(Alignment.Center), onSwipe = onSwipe) {
            val imageModel =
                remember(song.artworkUri) {
                    ImageRequest
                        .Builder(context)
                        .data(song.artworkUri)
                        .error(R.drawable.ic_default_track_cover_art)
                        .allowHardware(false) // Disable hardware bitmaps.
                        .build()
                }

            ZoomableImage(
                modifier = Modifier.align(Alignment.Center),
                model = imageModel,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                onState = { state ->
                    when (state) {
                        is AsyncImagePainter.State.Success -> {
                            coroutineScope.launch(Dispatchers.Default) {
                                val image = state.result.image
                                val bitmap = image.toBitmap(image.width, image.height)
                                palette = bitmap.getPalletColors()
                            }
                        }

                        else -> {}
                    }
                },
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val buttonModifier =
                    Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.4f))

                IconButton(modifier = buttonModifier, onClick = onNavigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = stringResource(R.string.back_description),
                        tint = White,
                    )
                }

                ShareButton(
                    modifier = buttonModifier,
                    songTitle = playbackStatus.track?.title,
                    songArtist = playbackStatus.track?.artist,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = song.title,
                color = White,
                style =
                    LocalTextStyle.current.copy(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        lineBreak = LineBreak.Heading,
                        shadow = Shadow(color = Black, blurRadius = 10f, offset = Offset(2f, 3f)),
                    ),
            )
            Text(
                text = song.artist,
                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp),
                style =
                    LocalTextStyle.current.copy(
                        color = White,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        shadow = Shadow(color = Black, blurRadius = 10f, offset = Offset(2f, 0f)),
                    ),
            )
            MusicPlayerControls(playbackStatus, onEvent)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

val Palette.dominantColor: Color?
    get() {
        val swatch = dominantSwatch ?: vibrantSwatch ?: lightVibrantSwatch ?: darkVibrantSwatch
        return swatch?.let { Color(it.rgb) }
    }

suspend fun Bitmap.getPalletColors(): Palette =
    withContext(Dispatchers.Default) {
        val palette = Palette.from(this@getPalletColors).generate()
        palette
    }
