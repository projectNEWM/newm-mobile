package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.internal.api.RemoteConfigAPI
import io.newm.shared.internal.api.models.MobileConfig
import org.koin.core.component.KoinComponent

interface RemoteConfigRepository {
    suspend fun getMobileConfig(): MobileConfig?
}

internal class RemoteConfigRepositoryImpl(
    private val mobileConfigAPI: RemoteConfigAPI
) : KoinComponent, RemoteConfigRepository {

    private val logger = Logger.withTag("NewmKMM-MobileConfigRepository")

    override suspend fun getMobileConfig(): MobileConfig? {
        return try {
            mobileConfigAPI.getMobileConfig()
        } catch (e: Exception) {
            logger.e(e) { "Failed to get mobile config" }
            null
        }
    }
}