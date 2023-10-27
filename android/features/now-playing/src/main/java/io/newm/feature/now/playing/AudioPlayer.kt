package io.newm.feature.now.playing

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import io.newm.core.theme.Gray100
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.shared.models.Song

@Composable
internal fun AudioPlayerViewer(song: Song) {
    val context = LocalContext.current
    val uri = song.getAssetSourceSongURI(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "Playing from your NFT library".toUpperCase(Locale.current),
                fontFamily = inter,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Gray100,
            )
            AsyncImage(
                model = song.coverArtUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = song.title,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = White
            )
            Text(
                text = song.ownerId,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Gray100
            )
            VideoAudioPlayer(uri = uri)
        }
    }
}

@SuppressLint("OpaqueUnitKey")
@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun VideoAudioPlayer(uri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))
                setMediaSource(source)

                prepare()
                play()
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        AndroidView(modifier = Modifier.background(Color.Black), factory = {
            PlayerView(context).apply {
                showController()
                useController = true
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}

private fun Song.getAssetSourceSongURI(context: Context): Uri {
    val assetName = when (tempSourceId) {
        1 -> R.raw.song1
        2 -> R.raw.song2
        3 -> R.raw.song3
        4 -> R.raw.song4
        5 -> R.raw.song5
        6 -> R.raw.song6
        else -> R.raw.song0
    }
    return Uri.parse("android.resource://${context.packageName}/${assetName}")
}
