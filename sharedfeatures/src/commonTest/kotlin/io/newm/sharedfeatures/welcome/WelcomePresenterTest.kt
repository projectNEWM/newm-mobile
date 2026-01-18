package io.newm.sharedfeatures.welcome

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.screens.DevMenuMainScreen
import io.newm.sharedfeatures.screens.LoginScreen
import io.newm.sharedfeatures.screens.WelcomeScreen
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class WelcomePresenterTest {

    @Test
    fun `navigation events work`() = runTest {
        val navigator = FakeNavigator()
        val presenter = WelcomePresenter(navigator)

        presenter.test<WelcomeScreen.UiState> {
            val state = awaitItem() as WelcomeScreen.UiState.Content
            
            state.onEvent(WelcomeScreen.UiEvent.OnLogin)
            assertThat(navigator.goToHistory.last()).isEqualTo(LoginScreen)

            state.onEvent(WelcomeScreen.UiEvent.OnDevMenu)
            assertThat(navigator.goToHistory.last()).isEqualTo(DevMenuMainScreen)

            state.onEvent(WelcomeScreen.UiEvent.OnBack)
            assertThat(navigator.popHistory).hasSize(1)
        }
    }
}