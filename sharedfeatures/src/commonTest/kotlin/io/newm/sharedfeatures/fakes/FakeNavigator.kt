package io.newm.sharedfeatures.fakes

import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.PopResult
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class FakeNavigator : Navigator {
    val goToHistory = mutableListOf<Screen>()
    val popHistory = mutableListOf<PopResult?>()
    val resetRootHistory = mutableListOf<Screen>()

    override fun goTo(screen: Screen): Boolean {
        goToHistory.add(screen)
        return true
    }

    override fun pop(result: PopResult?): Screen? {
        popHistory.add(result)
        return null
    }

    override fun resetRoot(
        newRoot: Screen,
        options: Navigator.StateOptions,
    ): ImmutableList<Screen> {
        resetRootHistory.add(newRoot)
        return persistentListOf()
    }

    override fun peek(): Screen? = goToHistory.lastOrNull()

    override fun peekBackStack(): ImmutableList<Screen> = persistentListOf(*goToHistory.toTypedArray())
}
