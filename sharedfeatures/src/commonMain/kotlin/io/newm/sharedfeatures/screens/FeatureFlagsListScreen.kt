package io.newm.sharedfeatures.screens

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.commonPublic.featureflags.EvaluationSource
import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FlagCategory
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize
object FeatureFlagsListScreen : Screen {
    data class FeatureFlagListItem(
        val featureFlag: FeatureFlag,
        val effectiveValue: Boolean,
        val remoteValue: Boolean,
        val localOverrideValue: Boolean?,
        val isOverridden: Boolean,
        val category: FlagCategory,
        val description: String,
        val evaluationSource: EvaluationSource? = null
    )

    sealed interface UiState : CircuitUiState {
        data object Loading : UiState

        data class Error(
            val message: String,
            val onRetry: () -> Unit
        ) : UiState

        data class Content(
            val flags: List<FeatureFlagListItem>,
            val groupedFlags: Map<FlagCategory, List<FeatureFlagListItem>>,
            val isRefreshing: Boolean = false,
            val debugInfo: DebugInfo? = null,
            val environmentInfo: EnvironmentInfo,
            val onEvent: (UiEvent) -> Unit,
        ) : UiState
    }

    data class DebugInfo(
        val totalFlags: Int,
        val overriddenCount: Int,
        val cacheHits: Int,
        val lastUpdated: String
    )

    data class EnvironmentInfo(
        val environment: String, // "Production", "Development", "Staging"
        val clientStatus: String, // "Connected", "Offline", "Error"
        val lastSync: String? = null
    )

    sealed interface UiEvent : CircuitUiEvent {
        data object OnBack : UiEvent
        data object OnRefresh : UiEvent
        data object OnResetAllFlags : UiEvent
        data object OnToggleDebugMode : UiEvent
        data object OnExportDebugState : UiEvent

        data class OnFlagToggled(val key: String, val isEnabled: Boolean) : UiEvent
        data class OnResetFlag(val key: String) : UiEvent
        data class OnCategoryToggled(val category: FlagCategory) : UiEvent
    }
}
