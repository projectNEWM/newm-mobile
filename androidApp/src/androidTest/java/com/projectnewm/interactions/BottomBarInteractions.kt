package com.projectnewm.interactions

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText

fun onBottomBar(composeTestRule: ComposeTestRule, actions: BottomBarInteractions.() -> Unit) {
    actions(BottomBarInteractions(composeTestRule))
}

class BottomBarInteractions(private val composeTestRule: ComposeTestRule) {
    val homeButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithText("Home")

    val walletButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithText("Wallet")

    val starsButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithText("Stars")

    val tribeButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithText("Tribe")
}

