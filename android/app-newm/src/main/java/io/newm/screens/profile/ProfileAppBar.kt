package io.newm.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.sharedfeatures.core.resources.R

@Composable
fun ProfileAppBar(
    bannerUrl: String,
    avatarUrl: String,
    onOverflowTapped: () -> Unit,
    onNavigationClick: (() -> Unit)? = null,
    onAvatarClick: (() -> Unit)? = null,
) {
    Box {
        ProfileBanner(bannerUrl = bannerUrl, avatarUrl = avatarUrl, onAvatarClick = onAvatarClick)
        TopAppBar(
            modifier = Modifier.systemBarsPadding(),
            elevation = 0.dp,
            title = {},
            backgroundColor = Color.Transparent,
            navigationIcon =
                onNavigationClick?.let {
                    {
                        ScrimCircle {
                            IconButton(onClick = onNavigationClick) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back_description),
                                )
                            }
                        }
                    }
                },
            actions = {
                ScrimCircle {
                    IconButton(onClick = onOverflowTapped) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.overflow_description),
                        )
                    }
                }
            },
        )
    }
}
