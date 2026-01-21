package io.newm.core.test.utils

import app.cash.paparazzi.DeviceConfig

private val normalDevice = DeviceConfig.PIXEL_6
private val tablet = DeviceConfig.NEXUS_10

enum class SnapshotTestConfiguration(
    val deviceConfig: DeviceConfig,
    val isDarkMode: Boolean,
    val fontScale: Float,
) {
    NormalLAccessibilityLight(deviceConfig = normalDevice, isDarkMode = false, fontScale = 2f),
    NormalDark(deviceConfig = normalDevice, isDarkMode = true, fontScale = 1f),
    TabletDark(deviceConfig = tablet, isDarkMode = true, fontScale = 2f),
}
