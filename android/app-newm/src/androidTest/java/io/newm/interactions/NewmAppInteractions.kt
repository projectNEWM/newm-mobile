package io.newm.interactions

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import io.newm.screens.home.TAG_HOME_SCREEN
import io.newm.screens.marketplace.TAG_MARKETPLACE_SCREEN
import io.newm.screens.library.TAG_LIBRARY_SCREEN
import io.newm.screens.wallet.TAG_WALLET_SCREEN

fun onNewmApp(
    composeTestRule: ComposeTestRule,
    actions: NewmAppInteractions.() -> Unit
) {
    actions(NewmAppInteractions(composeTestRule))
}

class NewmAppInteractions(private val composeTestRule: ComposeTestRule) {

    val homeScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_HOME_SCREEN)

    private val walletScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_WALLET_SCREEN)

    private val marketPlaceScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_MARKETPLACE_SCREEN)

    private val libraryScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_LIBRARY_SCREEN)



    fun assertHomeScreenIsDisplayed() {
        homeScreenInteraction.assertIsDisplayed()

        listOf(libraryScreenInteraction, walletScreenInteraction, marketPlaceScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertLibraryScreenIsDisplayed() {
        libraryScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, walletScreenInteraction, marketPlaceScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertWalletScreenIsDisplayed() {
        walletScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, libraryScreenInteraction, marketPlaceScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertMarketplaceScreenIsDisplayed() {
        marketPlaceScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, libraryScreenInteraction, walletScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }
}
