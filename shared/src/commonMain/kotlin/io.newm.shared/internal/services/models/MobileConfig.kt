package io.newm.shared.internal.services.models

import kotlinx.serialization.Serializable

@Serializable
data class MobileConfig(
    val version: Int,
    val android: MobileClientConfig,
    val ios: MobileClientConfig
)

@Serializable
data class MobileClientConfig(
    val minAppVersion: String
)