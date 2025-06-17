package io.newm.sharedfeatures.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.public.featureflags.FeatureFlagService
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class FeatureFlagsListPresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
    private val featureFlagService: FeatureFlagService,
) : Presenter<FeatureFlagsListScreen.UiState> {

    @Composable
    override fun present(): FeatureFlagsListScreen.UiState {
        var flagItems by remember { mutableStateOf(buildFlagListItems()) }

        fun refreshFlags() {
            flagItems = buildFlagListItems()
        }

        return FeatureFlagsListScreen.UiState.Content(
            flags = flagItems
        ) { event ->
            when (event) {
                is FeatureFlagsListScreen.UiEvent.OnFlagToggled -> {
                    featureFlagService.setLocalOverride(event.key, event.isEnabled)
                    refreshFlags()
                }

                is FeatureFlagsListScreen.UiEvent.OnResetFlag -> {
                    featureFlagService.setLocalOverride(event.key, null)
                    refreshFlags()
                }

                FeatureFlagsListScreen.UiEvent.OnResetAllFlags -> {
                    featureFlagService.resetAllLocalOverrides()
                    refreshFlags()
                }

                FeatureFlagsListScreen.UiEvent.OnBack -> navigator.pop()
            }
        }
    }

    private fun buildFlagListItems(): List<FeatureFlagsListScreen.FeatureFlagListItem> {
        return featureFlagService.getAllDeveloperFlags().map { flag ->
            FeatureFlagsListScreen.FeatureFlagListItem(
                featureFlag = flag,
                effectiveValue = featureFlagService.isEnabled(flag),
                remoteValue = featureFlagService.getRawBooleanVariationFromSource(flag),
                isOverridden = featureFlagService.getLocalOverride(flag.key) != null
            )
        }
    }
}