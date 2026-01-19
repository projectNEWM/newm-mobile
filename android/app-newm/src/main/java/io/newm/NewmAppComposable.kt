package io.newm

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
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
import io.newm.core.ui.LocalSnackBarHostState
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.iconGradient
import io.newm.feature.musicplayer.MiniPlayer
import io.newm.feature.musicplayer.MusicPlayerScreen
import io.newm.screens.Screen
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.sharedfeatures.devmenu.DebugOverlay
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import kotlinx.coroutines.launch
import com.slack.circuit.runtime.screen.Screen as CircuitScreen

internal const val TAG_BOTTOM_NAVIGATION = "TAG_BOTTOM_NAVIGATION"

private val HomeIconGradient = iconGradient(LightSkyBlue, DarkViolet)
private val AccountIconGradient = iconGradient(LightSkyBlue, DarkViolet)
private val LibraryIconGradient = iconGradient(DarkViolet, DarkPink)
private val SearchIconGradient = iconGradient(DarkPink, BrightOrange)
private val WalletIconGradient = iconGradient(OceanGreen, LightSkyBlue)
private val MarketIconGradient = iconGradient(BrightOrange, YellowJacket)

val LocalIsBottomBarVisible = compositionLocalOf { mutableStateOf(true) }

@Composable internal fun isBottomBarVisible() = remember { mutableStateOf(true) }

private val initialScreen = Screen.NFTLibrary

@Composable
internal fun NewmApp(
    config: NewmSharedBuildConfig,
    logger: NewmAppLogger,
    eventLogger: NewmAppEventLogger,
    showRecordStore: Boolean,
    showInvestmentPortfolio: Boolean,
) {
    val context = LocalContext.current
    val backstack = rememberSaveableBackStack(initialScreen)

    val circuitNavigator = rememberCircuitNavigator(backstack, enableBackHandler = false)

    val newmNavigator =
        rememberNewmNavigator(
            circuitNavigator = circuitNavigator,
            logger = logger,
            launchBrowser = { url -> context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri())) },
            eventLogger = eventLogger,
        )

    val currentRootScreen = backstack.topRecord?.screen
    val currentNewmScreen = currentRootScreen as? Screen

    val sheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
        )

    val coroutineScope = rememberCoroutineScope()

    BackHandler(
        enabled = backstack.size > 1 || currentNewmScreen != initialScreen || sheetState.isVisible,
    ) {
        when {
            sheetState.isVisible -> coroutineScope.launch { sheetState.hide() }
            backstack.size > 1 -> newmNavigator.pop()
            currentNewmScreen != initialScreen && currentNewmScreen != null ->
                newmNavigator.resetRoot(initialScreen)
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    DebugOverlay(
        buildConfig = config,
        onOpenDebugMenu = { circuitNavigator.goTo(DevMenuMainScreen) },
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier,
            sheetState = sheetState,
            sheetContent = {
                MusicPlayerScreen(onNavigateUp = { coroutineScope.launch { sheetState.hide() } })
            },
        ) {
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = LocalIsBottomBarVisible.current.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = ExitTransition.None,
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (currentNewmScreen?.showMiniPlayer == true) {
                                MiniPlayer(
                                    modifier =
                                        Modifier.clickable {
                                            coroutineScope.launch {
                                                eventLogger.logPageLoad(AppScreens.MusicPlayerScreen.name)
                                                sheetState.show()
                                            }
                                        },
                                )
                                Spacer(
                                    modifier =
                                        Modifier.height(2.dp).fillMaxWidth().background(MaterialTheme.colors.surface),
                                )
                            }

                            if (currentNewmScreen?.showBottomBar == true) {
                                NewmBottomNavigation(
                                    currentRootScreen = currentNewmScreen,
                                    eventLogger = eventLogger,
                                    showRecordStore = showRecordStore,
                                    showInvestmentPortfolio = showInvestmentPortfolio,
                                    onNavigationSelected = { circuitNavigator.resetRoot(it) },
                                )
                            }
                        }
                    }
                },
                scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
            ) { padding ->
                CompositionLocalProvider(LocalSnackBarHostState provides snackbarHostState) {
                    NavigableCircuitContent(
                        modifier = Modifier.padding(padding),
                        navigator = newmNavigator,
                        backStack = backstack,
                    )
                }
            }
        }
    }
}

@Composable
internal fun NewmBottomNavigation(
    currentRootScreen: CircuitScreen?,
    eventLogger: NewmAppEventLogger,
    showRecordStore: Boolean,
    showInvestmentPortfolio: Boolean,
    onNavigationSelected: (Screen) -> Unit,
) {
    Column(Modifier.height(76.dp)) {
        BottomNavigation(
            modifier = Modifier.fillMaxHeight().testTag(TAG_BOTTOM_NAVIGATION),
            backgroundColor = Black,
            contentColor = Gray100,
        ) {
            HomeBottomNavigationItem(
                selected = currentRootScreen == Screen.NFTLibrary,
                iconResId = R.drawable.ic_library,
                labelResId = R.string.bottom_nav_nft_tab,
                selectedIconBrush = LibraryIconGradient,
                selectedLabelColor = DarkPink,
                onClick = {
                    eventLogger.logClickEvent(AppScreens.HomeScreen.NFT_LIBRARY_BUTTON)
                    onNavigationSelected(Screen.NFTLibrary)
                },
            )
            if (showInvestmentPortfolio) {
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.InvestmentPortfolio,
                    iconResId = R.drawable.ic_wallet,
                    labelResId = R.string.bottom_nav_portfolio_tab,
                    selectedIconBrush = AccountIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = {
                        eventLogger.logClickEvent(AppScreens.InvestmentPortfolioScreen.RECORD_STORE_BUTTON)
                        eventLogger.logPageLoad(AppScreens.InvestmentPortfolioScreen.name)
                        onNavigationSelected(Screen.InvestmentPortfolio)
                    },
                )

                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.Marketplace,
                    iconResId = R.drawable.ic_marketplace,
                    labelResId = R.string.bottom_nav_marketplace_tab,
                    selectedIconBrush = AccountIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = {
                        eventLogger.logClickEvent(AppScreens.MarketplaceScreen.MARKETPLACE_BUTTON)
                        eventLogger.logPageLoad(AppScreens.MarketplaceScreen.name)
                        onNavigationSelected(Screen.Marketplace)
                    },
                )
            }
            if (showRecordStore) {
                HomeBottomNavigationItem(
                    selected = currentRootScreen == Screen.RecordStore,
                    iconResId = R.drawable.ic_recordstore_active,
                    labelResId = R.string.bottom_nav_record_store_tab,
                    selectedIconBrush = AccountIconGradient,
                    selectedLabelColor = DarkPink,
                    onClick = {
                        eventLogger.logClickEvent(AppScreens.RecordStoreScreen.RECORD_STORE_BUTTON)
                        eventLogger.logPageLoad(AppScreens.RecordStoreScreen.name)
                        onNavigationSelected(Screen.RecordStore)
                    },
                )
            }
            HomeBottomNavigationItem(
                selected = currentRootScreen == Screen.UserAccount,
                iconResId = R.drawable.ic_profile,
                labelResId = R.string.account,
                selectedIconBrush = AccountIconGradient,
                selectedLabelColor = DarkPink,
                onClick = {
                    eventLogger.logClickEvent(AppScreens.HomeScreen.ACCOUNT_BUTTON)
                    eventLogger.logPageLoad(AppScreens.AccountScreen.name)
                    onNavigationSelected(Screen.UserAccount)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    NewmBottomNavigation(
        Screen.NFTLibrary,
        NewmAppEventLogger(),
        showRecordStore = false,
        showInvestmentPortfolio = false,
    ) {}
}

// Based on content from:
// https://github.com/wlara/android-next-gen/blob/main/app/src/main/java/com/github/wlara/nextgen/ui/home/HomeScreen.kt
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
                modifier =
                    if (selected) {
                        Modifier.align(Alignment.CenterVertically).drawWithBrush(selectedIconBrush)
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
                fontSize = 9.sp,
                maxLines = 1,
                color = if (selected) selectedLabelColor else Color.Unspecified,
            )
        },
        selected = selected,
        onClick = onClick,
    )
}
