package io.newm.sharedfeatures.devmenu

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.public.featureflags.FeatureFlag
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize
object FeatureFlagsListScreen : Screen {
    data class FeatureFlagListItem(
        val featureFlag: FeatureFlag,
        val effectiveValue: Boolean,
        val remoteValue: Boolean,
        val isOverridden: Boolean,
    )

    sealed interface UiState : CircuitUiState {
        data object Loading : UiState
        data class Content(
            val flags: List<FeatureFlagListItem>,
            val onEvent: (UiEvent) -> Unit,
        ) : UiState
    }

    sealed interface UiEvent : CircuitUiEvent {
        data object OnBack : UiEvent
        data class OnFlagToggled(val key: String, val isEnabled: Boolean) : UiEvent
        data class OnResetFlag(val key: String) : UiEvent
        data object OnResetAllFlags : UiEvent
    }
}