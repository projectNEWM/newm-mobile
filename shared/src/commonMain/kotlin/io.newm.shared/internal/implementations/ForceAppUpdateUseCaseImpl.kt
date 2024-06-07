package io.newm.shared.internal.implementations

import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.internal.repositories.RemoteConfigRepository
import io.newm.shared.internal.api.models.MobileConfig
import io.newm.shared.public.usecases.ForceAppUpdateUseCase
import io.newm.shared.utils.VersionUtils

internal class ForceAppUpdateUseCaseImpl(
    private val remoteConfigRepository: RemoteConfigRepository,
    private val sharedBuildConfig: NewmSharedBuildConfig
) : ForceAppUpdateUseCase {

    override suspend fun isAndroidUpdateRequired(): Boolean =
        shouldForceAppUpdate { it.android.minAppVersion }

    override suspend fun isiOSUpdateRequired(): Boolean =
        shouldForceAppUpdate { it.ios.minAppVersion }


    private suspend fun shouldForceAppUpdate(versionSelector: (MobileConfig) -> String): Boolean {
        val mobileConfig = remoteConfigRepository.getMobileConfig()
        return mobileConfig?.let {
            VersionUtils.isVersionGreaterThan(versionSelector(it), sharedBuildConfig.appVersion)
        } ?: false
    }

}