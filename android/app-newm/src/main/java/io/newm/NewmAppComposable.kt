package io.newm

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import io.newm.core.resources.R
import io.newm.core.theme.Black
import io.newm.core.theme.BrightOrange
import io.newm.core.theme.DarkPink
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.Gray100
import io.newm.core.theme.LightSkyBlue
import io.newm.core.theme.OceanGreen
import io.newm.core.theme.YellowJacket
import io.newm.core.theme.inter
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.iconGradient
import io.newm.screens.Screen

internal const val TAG_BOTTOM_NAVIGATION = "TAG_BOTTOM_NAVIGATION"

private val HomeIconGradient = iconGradient(LightSkyBlue, DarkViolet)
private val AccountIconGradient = iconGradient(LightSkyBlue, DarkViolet)
private val LibraryIconGradient = iconGradient(DarkViolet, DarkPink)
private val SearchIconGradient = iconGradient(DarkPink, BrightOrange)
private val WalletIconGradient = iconGradient(OceanGreen, LightSkyBlue)
private val MarketIconGradient = iconGradient(BrightOrange, YellowJacket)

@Composable
internal fun NewmApp() {
    var currentRootScreen: Screen by remember {
        mutableStateOf(Screen.NFTLibrary)
    }

    val isBottomNavBarVisible = rememberSaveable { (mutableStateOf(true)) }

    val backstack = rememberSaveableBackStack {
        push(currentRootScreen)
    }
    val navigator = rememberCircuitNavigator(backstack)

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            NewmBottomNavigation(
                currentRootScreen = currentRootScreen,
                isVisible = isBottomNavBarVisible.value,
                onNavigationSelected = {
                    currentRootScreen = it
                    navigator.resetRoot(it)
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            NavigableCircuitContent(
                navigator = navigator,
                backstack = backstack
            )
        }
    }
}

@Composable
internal fun NewmBottomNavigation(
    currentRootScreen: Screen,
    isVisible: Boolean,
    onNavigationSelected: (Screen) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Column(Modifier.height(76.dp)) {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxHeight()
                    .testTag(TAG_BOTTOM_NAVIGATION),
                backgroundColor = Black,
                contentColor = Gray100
            ) {
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.NFTLibrary,
                    iconResId = R.drawable.ic_library,
                    labelResId = R.string.nft_library,
                    selectedIconBrush = LibraryIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = { onNavigationSelected(Screen.NFTLibrary) },
                )
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.UserAccount,
                    iconResId = R.drawable.ic_profile,
                    labelResId = R.string.account,
                    selectedIconBrush = AccountIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = { onNavigationSelected(Screen.UserAccount) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    NewmBottomNavigation(Screen.NFTLibrary, true) {}
}

// Based on content from: https://github.com/wlara/android-next-gen/blob/main/app/src/main/java/com/github/wlara/nextgen/ui/home/HomeScreen.kt
@Composable
private fun RowScope.HomeBottomNavigationItem(
    selected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    selectedIconBrush: Brush,
    selectedLabelColor: Color,
    onClick: () -> Unit,
) {
    val label = stringResource(id = labelResId)
    BottomNavigationItem(
        icon = {
            Icon(
                modifier = if (selected) {
                    Modifier
                        .align(Alignment.CenterVertically)
                        .drawWithBrush(selectedIconBrush)
                } else {
                    Modifier.align(Alignment.CenterVertically)
                },
                painter = painterResource(id = iconResId),
                contentDescription = label,
            )
        },
        label = {
            Text(
                text = label,
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                color = if (selected) selectedLabelColor else Color.Unspecified
            )
        },
        selected = selected,
        onClick = onClick
    )
}
