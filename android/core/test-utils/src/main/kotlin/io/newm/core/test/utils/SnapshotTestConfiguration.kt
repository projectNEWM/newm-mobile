package io.newm.core.test.utils

import app.cash.paparazzi.DeviceConfig

private val normalDevice = DeviceConfig.PIXEL_6
private val smallDevice = DeviceConfig.NEXUS_4

enum class SnapshotTestConfiguration(
    val deviceConfig: DeviceConfig,
    val isDarkMode: Boolean,
    val fontScale: Float,
) {
    NormalLight(deviceConfig = normalDevice, isDarkMode = false, fontScale = 1f),
    NormalDark(deviceConfig = normalDevice, isDarkMode = true, fontScale = 1f),
    AccessibilityLight(deviceConfig = smallDevice, isDarkMode = false, fontScale = 2f),
}
