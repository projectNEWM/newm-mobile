package io.newm.shared.commonInternal.api.models

import kotlinx.serialization.Serializable

@Serializable
data class MobileConfig(
    val version: Int,
    val android: MobileClientConfig,
    val ios: MobileClientConfig,
)

@Serializable data class MobileClientConfig(
    val minAppVersion: String,
)
