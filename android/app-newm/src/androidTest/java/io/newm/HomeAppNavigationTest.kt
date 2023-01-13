package io.newm

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import io.newm.interactions.BottomBarInteractions
import io.newm.interactions.NewmAppInteractions
import io.newm.interactions.onBottomBar
import io.newm.interactions.onNewmApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeAppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LoginActivity>()


    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            NewmApp()
        }
    }

    @Test
    fun when_rendered_then_home_screen_selected() {
        onNewmApp {
            assertHomeScreenIsDisplayed()
        }
    }

    @Test
    fun given_not_in_home_screen_when_tap_home_then_switches_to_home_screen() {
        onNewmApp {
            onBottomBar { walletButtonInteraction.performClick() }
            homeScreenInteraction.assertDoesNotExist()

            onBottomBar { homeButtonInteraction.performClick() }

            homeScreenInteraction.assertIsDisplayed()
        }
    }

    @Test
    fun when_tap_library_on_bottom_bar_then_switches_to_library_screen() {
        onNewmApp {
            onBottomBar {
                libraryButtonInteraction.performClick()
            }

            assertLibraryScreenIsDisplayed()
        }
    }

    @Test
    fun when_tap_wallet_on_bottom_bar_then_switches_to_wallet_screen() {
        onNewmApp {
            onBottomBar {
                walletButtonInteraction.performClick()
            }

            assertWalletScreenIsDisplayed()
        }
    }

    @Test
    fun when_tap_marketplace_on_bottom_bar_then_switches_to_marketplace_screen() {
        onNewmApp {
            onBottomBar {
                marketplaceButtonInteraction.performClick()
            }

            assertMarketplaceScreenIsDisplayed()
        }
    }

    private fun onNewmApp(actions: NewmAppInteractions.() -> Unit) =
        onNewmApp(composeTestRule, actions)

    private fun onBottomBar(actions: BottomBarInteractions.() -> Unit) =
        onBottomBar(composeTestRule, actions)
}