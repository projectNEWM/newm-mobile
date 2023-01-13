package io.newm.interactions

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.platform.app.InstrumentationRegistry
import io.newm.core.resources.R
import io.newm.TAG_BOTTOM_NAVIGATION

fun onBottomBar(composeTestRule: ComposeTestRule, actions: BottomBarInteractions.() -> Unit) {
    actions(BottomBarInteractions(composeTestRule))
}

class BottomBarInteractions(private val composeTestRule: ComposeTestRule) {
    private val context: Context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private val hasBottomBarAncestor = hasAnyAncestor(hasTestTag(TAG_BOTTOM_NAVIGATION))

    val homeButtonInteraction: SemanticsNodeInteraction
        get() {
            return composeTestRule.onNode(
                hasContentDescriptionExactly(context.getString(R.string.home))
                    .and(hasBottomBarAncestor)
            )
        }

    val libraryButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNode(
            hasContentDescriptionExactly(context.getString(R.string.library))
                .and(hasBottomBarAncestor)
        )

    val walletButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNode(
            hasContentDescriptionExactly(context.getString(R.string.wallet))
                .and(hasBottomBarAncestor)
        )

    val marketplaceButtonInteraction: SemanticsNodeInteraction
        get() = composeTestRule.onNode(
            hasContentDescriptionExactly(context.getString(R.string.marketplace))
                .and(hasBottomBarAncestor)
        )
}

