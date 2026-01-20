package io.newm.shared.commonInternal.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CloudinarySignResponse(
    val signature: String,
    val timestamp: Long,
    val apiKey: String,
    val cloudName: String,
)
