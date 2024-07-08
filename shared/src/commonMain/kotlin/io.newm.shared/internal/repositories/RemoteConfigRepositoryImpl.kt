package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.api.RemoteConfigAPI
import io.newm.shared.internal.api.models.MobileConfig

/**
 * Interface for fetching remote mobile configuration.
 */
interface RemoteConfigRepository {
    /**
     * Fetches the mobile configuration.
     *
     * @return The mobile configuration or null if an error occurs.
     */
    suspend fun getMobileConfig(): MobileConfig?
}

/**
 * Implementation of [RemoteConfigRepository] that fetches remote mobile configuration using an API.
 *
 * @property mobileConfigAPI The API service for fetching the mobile configuration.
 * @property logger The crash reporter for logging exceptions.
 */
internal class RemoteConfigRepositoryImpl(
    private val mobileConfigAPI: RemoteConfigAPI,
    private val logger: NewmAppLogger
) : RemoteConfigRepository {

    override suspend fun getMobileConfig(): MobileConfig? {
        return try {
            mobileConfigAPI.getMobileConfig()
        } catch (e: Exception) {
            logger.error("RemoteConfigRepositoryImpl", "Error fetching mobile config", e)
            null
        }
    }
}