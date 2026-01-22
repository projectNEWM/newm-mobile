package io.newm.sharedfeatures.screens.devmenu.featureflaglist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.newm.shared.commonPublic.featureflags.EvaluationSource

@Composable
fun ValueChip(
    label: String,
    value: String,
    color: Color,
    isHighlighted: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .then(
                    if (isHighlighted) {
                        Modifier.border(
                            width = 1.dp,
                            color = color.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp),
                        )
                    } else {
                        Modifier
                    },
                ),
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = if (isHighlighted) 0.15f else 0.08f),
        elevation = if (isHighlighted) 2.dp else 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Label text (Remote, Current, Override)
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                color = color,
                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            )

            // Value text (ON/OFF)
            Text(
                text = value,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                color = color,
            )
        }
    }
}

// Usage examples for different scenarios:

@Composable
fun ValueChipExamples() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Example 1: Normal Flag (No Override)", style = MaterialTheme.typography.h6)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Remote value is active (highlighted)
            ValueChip(
                label = "Remote",
                value = "ON",
                color = Color(0xFF4CAF50), // Green
                isHighlighted = true,
            )

            // Current matches remote (highlighted)
            ValueChip(
                label = "Current",
                value = "ON",
                color = Color(0xFF2196F3), // Blue
                isHighlighted = true,
            )
        }

        Text("Example 2: Overridden Flag", style = MaterialTheme.typography.h6)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Remote value is not active (dimmed)
            ValueChip(
                label = "Remote",
                value = "OFF",
                color = Color(0xFF757575), // Gray
                isHighlighted = false,
            )

            // Current is what user experiences (highlighted)
            ValueChip(
                label = "Current",
                value = "ON",
                color = Color(0xFF2196F3), // Blue
                isHighlighted = true,
            )

            // Override shows what user set (highlighted)
            ValueChip(
                label = "Override",
                value = "ON",
                color = Color(0xFF9C27B0), // Purple
                isHighlighted = true,
            )
        }

        Text("Example 3: Advanced Access Rule", style = MaterialTheme.typography.h6)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Remote would be OFF (dimmed)
            ValueChip(
                label = "Remote",
                value = "OFF",
                color = Color(0xFF757575), // Gray
                isHighlighted = false,
            )

            // But current is ON due to Advanced Access (highlighted)
            ValueChip(
                label = "Current",
                value = "ON",
                color = Color(0xFF2196F3), // Blue
                isHighlighted = true,
            )

            // Rule chip to show why it's enabled
            ValueChip(
                label = "Rule",
                value = "AA",
                color = Color(0xFFFF9800), // Orange
                isHighlighted = true,
            )
        }
    }
}

// Enhanced version with additional features:

@Composable
fun EnhancedValueChip(
    label: String,
    value: String,
    color: Color,
    isHighlighted: Boolean = false,
    showIcon: Boolean = false,
    icon: String? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .then(
                    if (onClick != null) {
                        Modifier.clickable { onClick() }
                    } else {
                        Modifier
                    },
                ).then(
                    if (isHighlighted) {
                        Modifier.border(
                            width = 1.dp,
                            color = color.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp),
                        )
                    } else {
                        Modifier
                    },
                ),
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = if (isHighlighted) 0.15f else 0.08f),
        elevation = if (isHighlighted) 2.dp else 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Optional icon
            if (showIcon && icon != null) {
                Text(text = icon, style = MaterialTheme.typography.caption, color = color)
            }

            // Label text
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                color = color,
                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            )

            // Value text
            Text(
                text = value,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                color = color,
            )
        }
    }
}

// Predefined color schemes for consistency:

object ValueChipColors {
    val Remote = Color(0xFF4CAF50) // Green
    val Current = Color(0xFF2196F3) // Blue
    val Override = Color(0xFF9C27B0) // Purple
    val Rule = Color(0xFFFF9800) // Orange
    val Error = Color(0xFFF44336) // Red
    val Disabled = Color(0xFF757575) // Gray
    val Cache = Color(0xFF607D8B) // Blue Gray
}

// Helper function to determine highlighting logic:

@Composable
fun createValueChips(
    remoteValue: Boolean,
    currentValue: Boolean,
    overrideValue: Boolean?,
    evaluationSource: EvaluationSource?,
): List<@Composable () -> Unit> {
    val chips = mutableListOf<@Composable () -> Unit>()

    // Remote chip (highlighted only if no override)
    chips.add {
        ValueChip(
            label = "Remote",
            value = if (remoteValue) "ON" else "OFF",
            color = ValueChipColors.Remote,
            isHighlighted = overrideValue == null,
        )
    }

    // Current chip (always highlighted as it's what user experiences)
    chips.add {
        ValueChip(
            label = "Current",
            value = if (currentValue) "ON" else "OFF",
            color = ValueChipColors.Current,
            isHighlighted = true,
        )
    }

    // Override chip (only shown if there's an override)
    if (overrideValue != null) {
        chips.add {
            ValueChip(
                label = "Override",
                value = if (overrideValue) "ON" else "OFF",
                color = ValueChipColors.Override,
                isHighlighted = true,
            )
        }
    }

    // Source chip (optional, shows evaluation source)
    if (evaluationSource != null) {
        chips.add {
            ValueChip(
                label = "Source",
                value =
                    when (evaluationSource) {
                        EvaluationSource.CACHE -> "Cache"
                        EvaluationSource.REMOTE_SOURCE -> "Remote"
                        EvaluationSource.LOCAL_OVERRIDE -> "Override"
                        EvaluationSource.FALLBACK -> "Fallback"
                    },
                color =
                    when (evaluationSource) {
                        EvaluationSource.CACHE -> ValueChipColors.Cache
                        EvaluationSource.REMOTE_SOURCE -> ValueChipColors.Remote
                        EvaluationSource.LOCAL_OVERRIDE -> ValueChipColors.Override
                        EvaluationSource.FALLBACK -> ValueChipColors.Error
                    },
                isHighlighted = false,
            )
        }
    }

    return chips
}
