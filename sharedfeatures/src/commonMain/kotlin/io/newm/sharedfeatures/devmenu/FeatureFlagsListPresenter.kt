@file:OptIn(ExperimentalTime::class)

package io.newm.sharedfeatures.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.commonPublic.featureflags.EvaluationSource
import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class FeatureFlagsListPresenter @Inject constructor(
    @Assisted private val navigator: Navigator,
    private val featureFlagService: FeatureFlagService,
) : Presenter<FeatureFlagsListScreen.UiState> {
    @Composable
    override fun present(): FeatureFlagsListScreen.UiState {
        var flagItems by remember { mutableStateOf<List<FeatureFlagsListScreen.FeatureFlagListItem>>(emptyList()) }
        var isLoading by remember { mutableStateOf(true) }
        var error by remember { mutableStateOf<String?>(null) }
        var isRefreshing by remember { mutableStateOf(false) }
        var debugMode by remember { mutableStateOf(false) }
        var debugInfo by remember { mutableStateOf<FeatureFlagsListScreen.DebugInfo?>(null) }

        val scope = rememberCoroutineScope()

        // Observe all flags reactively
        val allFlagsState by featureFlagService.observeAllFlags().collectAsState(initial = emptyMap())

        // Environment info - you can get this from your build config or feature flag service
        val environmentInfo = remember {
            FeatureFlagsListScreen.EnvironmentInfo(
                environment = "Production",//if (BuildConfig.DEBUG) "Development" else "Production",
                clientStatus = "Connected", // You can make this dynamic
                lastSync = Clock.System.now().toString()
            )
        }

        // Load and refresh flag data
        suspend fun loadFlags() {
            try {
                val flags = buildEnhancedFlagListItems()
                flagItems = flags

                if (debugMode) {
                    debugInfo = buildDebugInfo(flags)
                }

                error = null
            } catch (e: Exception) {
                error = "Failed to load flags: ${e.message}"
            }
        }

        // Initial load and reactive updates
        LaunchedEffect(allFlagsState) {
            if (isLoading) {
                loadFlags()
                isLoading = false
            } else if (!isRefreshing) {
                loadFlags()
            }
        }

        if (isLoading && flagItems.isEmpty()) {
            return FeatureFlagsListScreen.UiState.Loading
        }

        if (error != null && flagItems.isEmpty()) {
            return FeatureFlagsListScreen.UiState.Error(
                message = error!!,
                onRetry = {
                    scope.launch {
                        isLoading = true
                        error = null
                        loadFlags()
                        isLoading = false
                    }
                }
            )
        }

        val groupedFlags = flagItems.groupBy { it.category }

        return FeatureFlagsListScreen.UiState.Content(
            flags = flagItems,
            groupedFlags = groupedFlags,
            isRefreshing = isRefreshing,
            debugInfo = if (debugMode) debugInfo else null,
            environmentInfo = environmentInfo
        ) { event ->
            when (event) {
                is FeatureFlagsListScreen.UiEvent.OnFlagToggled -> {
                    scope.launch {
                        val result = featureFlagService.setLocalOverride(event.key, event.isEnabled)
                        result.fold(
                            onSuccess = { /* State updates automatically */ },
                            onError = { exception, _ ->
                                error = "Failed to toggle flag: ${exception.message}"
                            }
                        )
                    }
                }

                is FeatureFlagsListScreen.UiEvent.OnResetFlag -> {
                    scope.launch {
                        val result = featureFlagService.setLocalOverride(event.key, null)
                        result.fold(
                            onSuccess = { /* State updates automatically */ },
                            onError = { exception, _ ->
                                error = "Failed to reset flag: ${exception.message}"
                            }
                        )
                    }
                }

                FeatureFlagsListScreen.UiEvent.OnResetAllFlags -> {
                    scope.launch {
                        val result = featureFlagService.resetAllOverrides()
                        result.fold(
                            onSuccess = { /* State updates automatically */ },
                            onError = { exception, _ ->
                                error = "Failed to reset all flags: ${exception.message}"
                            }
                        )
                    }
                }

                FeatureFlagsListScreen.UiEvent.OnRefresh -> {
                    scope.launch {
                        isRefreshing = true
                        loadFlags()
                        isRefreshing = false
                    }
                }

                FeatureFlagsListScreen.UiEvent.OnToggleDebugMode -> {
                    debugMode = !debugMode
                    if (debugMode) {
                        scope.launch {
                            debugInfo = buildDebugInfo(flagItems)
                        }
                    } else {
                        debugInfo = null
                    }
                }

                FeatureFlagsListScreen.UiEvent.OnExportDebugState -> {
                    scope.launch {
                        try {
                            val debugState = featureFlagService.exportDebugState()
                            println("Debug State: $debugState")
                        } catch (e: Exception) {
                            error = "Failed to export debug state: ${e.message}"
                        }
                    }
                }

                FeatureFlagsListScreen.UiEvent.OnBack -> navigator.pop()

                is FeatureFlagsListScreen.UiEvent.OnCategoryToggled -> {
                    // Category expansion/collapse if needed
                }
            }
        }
    }

    private suspend fun buildEnhancedFlagListItems(): List<FeatureFlagsListScreen.FeatureFlagListItem> {
        val evaluationHistory = featureFlagService.getEvaluationHistory()

        return featureFlagService.getAllFlags().map { flag ->
            val effectiveResult = featureFlagService.getEffectiveValue(flag)
            val localOverride = featureFlagService.getLocalOverride(flag.key)

            val effectiveValue = effectiveResult.fold(
                onSuccess = { it },
                onError = { _, fallback -> fallback ?: flag.defaultValue }
            )

            // Get the actual remote value (what LaunchDarkly returns without local overrides)
            val remoteValue = calculateTrueRemoteValue(flag)

            val lastEvaluation = evaluationHistory
                .filter { it.flagKey == flag.key }
                .maxByOrNull { it.timestamp }

            FeatureFlagsListScreen.FeatureFlagListItem(
                featureFlag = flag,
                effectiveValue = effectiveValue,
                remoteValue = remoteValue,
                localOverrideValue = localOverride,
                isOverridden = localOverride != null,
                category = flag.category,
                description = flag.description,
                evaluationSource = lastEvaluation?.source
            )
        }
    }

    private suspend fun calculateTrueRemoteValue(flag: FeatureFlag): Boolean {
        // This should get the raw value from LaunchDarkly without any local overrides
        // You might need to add a method to your AndroidFeatureFlagManager for this
        return flag.defaultValue // Placeholder - implement with actual remote fetch
    }

    private suspend fun buildDebugInfo(flags: List<FeatureFlagsListScreen.FeatureFlagListItem>): FeatureFlagsListScreen.DebugInfo {
        val evaluationHistory = featureFlagService.getEvaluationHistory()
        val cacheHits = evaluationHistory.count { it.source == EvaluationSource.CACHE }

        return FeatureFlagsListScreen.DebugInfo(
            totalFlags = flags.size,
            overriddenCount = flags.count { it.isOverridden },
            cacheHits = cacheHits,
            lastUpdated = Clock.System.now().toString()
        )
    }
}