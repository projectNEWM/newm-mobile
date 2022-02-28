package io.projectnewm.interactions

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import io.projectnewm.screens.home.TAG_HOME_SCREEN
import io.projectnewm.screens.stars.TAG_STARS_SCREEN
import io.projectnewm.screens.tribe.TAG_TRIBE_SCREEN
import io.projectnewm.screens.wallet.TAG_WALLET_SCREEN

fun onNewmApp(
    composeTestRule: ComposeTestRule,
    actions: NewmAppInteractions.() -> Unit
) {
    actions(NewmAppInteractions(composeTestRule))
}

class NewmAppInteractions(private val composeTestRule: ComposeTestRule) {
    private val walletScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_WALLET_SCREEN)

    private val starsScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_STARS_SCREEN)

    val tribeScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_TRIBE_SCREEN)

    val homeScreenInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TAG_HOME_SCREEN)

    fun assertHomeScreenIsDisplayed() {
        homeScreenInteraction.assertIsDisplayed()

        listOf(tribeScreenInteraction, starsScreenInteraction, walletScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertTribeScreenIsDisplayed() {
        tribeScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, starsScreenInteraction, walletScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertStarsScreenIsDisplayed() {
        starsScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, tribeScreenInteraction, walletScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }

    fun assertWalletScreenIsDisplayed() {
        walletScreenInteraction.assertIsDisplayed()

        listOf(homeScreenInteraction, tribeScreenInteraction, starsScreenInteraction).forEach {
            it.assertDoesNotExist()
        }
    }
}
