package io.newm.sharedfeatures.paparazzi

import androidx.compose.ui.Modifier
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.newm.core.test.utils.SnapshotTest
import io.newm.core.test.utils.SnapshotTestConfiguration
import io.newm.shared.commonPublic.featureflags.EvaluationSource
import io.newm.shared.commonPublic.featureflags.FeatureFlag
import io.newm.shared.commonPublic.featureflags.FlagCategory
import io.newm.sharedfeatures.devmenu.FeatureFlagsListUi
import io.newm.sharedfeatures.screens.FeatureFlagsListScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class FeatureFlagsListUiTest(
    @TestParameter configuration: SnapshotTestConfiguration,
) : SnapshotTest(configuration) {
    @Test
    fun defaultFeatureFlagsListUi() {
        val flags =
            listOf(
                FeatureFlagsListScreen.FeatureFlagListItem(
                    featureFlag =
                        object : FeatureFlag {
                            override val key = "flag1"
                            override val displayName = "Test Flag 1"
                            override val defaultValue = false
                            override val category = FlagCategory.BUSINESS_LOGIC
                            override val description = "Description 1"
                        },
                    effectiveValue = true,
                    remoteValue = true,
                    localOverrideValue = null,
                    isOverridden = false,
                    category = FlagCategory.BUSINESS_LOGIC,
                    description = "Description 1",
                    evaluationSource = EvaluationSource.REMOTE_SOURCE,
                ),
                FeatureFlagsListScreen.FeatureFlagListItem(
                    featureFlag =
                        object : FeatureFlag {
                            override val key = "flag2"
                            override val displayName = "Test Flag 2"
                            override val defaultValue = true
                            override val category = FlagCategory.UI_FEATURE
                            override val description = "Description 2"
                        },
                    effectiveValue = false,
                    remoteValue = true,
                    localOverrideValue = false,
                    isOverridden = true,
                    category = FlagCategory.UI_FEATURE,
                    description = "Description 2",
                    evaluationSource = EvaluationSource.LOCAL_OVERRIDE,
                ),
            )
        snapshot {
            FeatureFlagsListUi(
                state =
                    FeatureFlagsListScreen.UiState.Content(
                        flags = flags,
                        groupedFlags = flags.groupBy { it.category },
                        environmentInfo =
                            FeatureFlagsListScreen.EnvironmentInfo(
                                environment = "Development",
                                clientStatus = "Connected",
                                lastSync = "2023-10-27T10:00:00Z",
                            ),
                        onEvent = {},
                    ),
                modifier = Modifier,
            )
        }
    }

    @Test
    fun loadingFeatureFlagsListUi() {
        snapshot {
            FeatureFlagsListUi(state = FeatureFlagsListScreen.UiState.Loading, modifier = Modifier)
        }
    }

    @Test
    fun errorFeatureFlagsListUi() {
        snapshot {
            FeatureFlagsListUi(
                state =
                    FeatureFlagsListScreen.UiState.Error(
                        message = "Failed to load flags",
                        onRetry = {},
                    ),
                modifier = Modifier,
            )
        }
    }
}
