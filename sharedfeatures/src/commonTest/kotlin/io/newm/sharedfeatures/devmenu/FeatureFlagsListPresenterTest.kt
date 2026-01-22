package io.newm.sharedfeatures.devmenu

import com.slack.circuit.test.test
import com.varabyte.truthish.assertThat
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.sharedfeatures.fakes.FakeFeatureFlagService
import io.newm.sharedfeatures.fakes.FakeNavigator
import io.newm.sharedfeatures.fakes.FakeNewmSharedBuildConfig
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen.UiEvent
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen.UiState
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class FeatureFlagsListPresenterTest {
    private lateinit var navigator: FakeNavigator
    private lateinit var featureFlagService: FakeFeatureFlagService
    private lateinit var buildConfig: FakeNewmSharedBuildConfig

    @BeforeTest
    fun setup() {
        navigator = FakeNavigator()
        featureFlagService = FakeFeatureFlagService()
        buildConfig = FakeNewmSharedBuildConfig()

        // Setup default flags
        featureFlagService.setAvailableFlags(FeatureFlags.ALL_FLAGS)
        FeatureFlags.ALL_FLAGS.forEach { featureFlagService.setFlag(it.key, it.defaultValue) }
    }

    @Test
    fun `initial state loads flags`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            presenter.test {
                assertThat(awaitItem()).isInstanceOf<UiState.Loading>()

                val initialState = awaitItem()
                assertThat(initialState).isInstanceOf<UiState.Content>()

                val contentState = initialState as UiState.Content
                assertThat(contentState.flags).isNotEmpty()
                assertThat(contentState.environmentInfo.environment)
                    .isEqualTo("Production") // Default in fake is !isStagingMode
            }
        }

    @Test
    fun `toggling flag updates local override`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            presenter.test {
                awaitItem() // Loading
                val initialState = awaitItem() as UiState.Content
                val firstFlag = initialState.flags.first()

                initialState.onEvent(
                    UiEvent.OnFlagToggled(firstFlag.featureFlag.key, !firstFlag.effectiveValue),
                )

                // Verify override was set in service
                val override = featureFlagService.getLocalOverride(firstFlag.featureFlag.key)
                assertThat(override).isEqualTo(!firstFlag.effectiveValue)
            }
        }

    @Test
    fun `resetting flag removes local override`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            val flag = FeatureFlags.ALL_FLAGS.first()
            featureFlagService.setLocalOverride(flag.key, !flag.defaultValue)

            presenter.test {
                awaitItem() // Loading
                val initialState = awaitItem() as UiState.Content
                val flagItem = initialState.flags.first { it.featureFlag.key == flag.key }
                assertThat(flagItem.isOverridden).isTrue()

                initialState.onEvent(UiEvent.OnResetFlag(flag.key))

                assertThat(featureFlagService.getLocalOverride(flag.key)).isNull()
            }
        }

    @Test
    fun `resetting all flags removes all overrides`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            featureFlagService.setLocalOverride(FeatureFlags.ALL_FLAGS[0].key, true)
            featureFlagService.setLocalOverride(FeatureFlags.ALL_FLAGS[1].key, false)

            presenter.test {
                awaitItem() // Loading
                val initialState = awaitItem() as UiState.Content
                assertThat(initialState.flags.any { it.isOverridden }).isTrue()

                initialState.onEvent(UiEvent.OnResetAllFlags)

                assertThat(featureFlagService.localOverrides.value).isEmpty()
            }
        }

    @Test
    fun `refreshing reloads flags`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            presenter.test {
                awaitItem() // Loading
                val initialState = awaitItem() as UiState.Content

                // Change a value in the service to verify refresh picks it up
                val flag = FeatureFlags.ALL_FLAGS.first()
                featureFlagService.setFlag(flag.key, !flag.defaultValue)

                initialState.onEvent(UiEvent.OnRefresh)

                // Expect the state to settle
                val finalState = expectMostRecentItem() as UiState.Content
                assertThat(finalState.isRefreshing).isFalse()

                val updatedFlag = finalState.flags.first { it.featureFlag.key == flag.key }
                assertThat(updatedFlag.remoteValue).isEqualTo(!flag.defaultValue)
            }
        }

    @Test
    fun `back navigates back`() =
        runTest {
            val presenter = FeatureFlagsListPresenter(navigator, featureFlagService, buildConfig)

            presenter.test {
                awaitItem() // Loading
                val initialState = awaitItem() as UiState.Content

                initialState.onEvent(UiEvent.OnBack)

                assertThat(navigator.popHistory).isNotEmpty()
            }
        }
}
