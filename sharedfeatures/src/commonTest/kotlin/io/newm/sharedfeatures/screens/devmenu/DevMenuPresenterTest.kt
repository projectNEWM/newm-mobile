package io.newm.sharedfeatures.screens.devmenu

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.screens.DevMenuMainScreen.UiEvent
import io.newm.sharedfeatures.screens.DevMenuMainScreen.UiState
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DevMenuPresenterTest {
    private lateinit var navigator: FakeNavigator

    @BeforeTest
    fun setup() {
        navigator = FakeNavigator()
    }

    @Test
    fun `initial state has correct menu items`() =
        runTest {
            val presenter = DevMenuPresenter(navigator)

            presenter.test {
                val state = awaitItem() as UiState.Content

                assertThat(state.menuItems.single().screen).isEqualTo(FeatureFlagsListScreen)
            }
        }

    @Test
    fun `clicking feature flags navigates to feature flags screen`() =
        runTest {
            val presenter = DevMenuPresenter(navigator)

            presenter.test {
                val state = awaitItem() as UiState.Content

                state.onEvent(UiEvent.OnItemClick(FeatureFlagsListScreen))

                assertThat(navigator.goToHistory.single()).isEqualTo(FeatureFlagsListScreen)
            }
        }

    @Test
    fun `back event navigates back`() =
        runTest {
            val presenter = DevMenuPresenter(navigator)

            presenter.test {
                val state = awaitItem() as UiState.Content

                state.onEvent(UiEvent.OnBack)

                assertThat(navigator.popHistory).hasSize(1)
            }
        }
}
