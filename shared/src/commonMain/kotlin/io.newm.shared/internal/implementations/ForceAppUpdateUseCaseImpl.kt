package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.RemoteConfigRepository
import io.newm.shared.internal.api.models.MobileConfig
import io.newm.shared.public.usecases.ForceAppUpdateUseCase
import io.newm.shared.utils.VersionUtils

internal class ForceAppUpdateUseCaseImpl(
    private val remoteConfigRepository: RemoteConfigRepository,
) : ForceAppUpdateUseCase {

    override suspend fun isAndroidUpdateRequired(
        currentAppVersion: String,
        humanVerificationCode: String
    ): Boolean =
        shouldForceAppUpdate(currentAppVersion, humanVerificationCode) { it.android.minAppVersion }

    override suspend fun isiOSUpdateRequired(
        currentAppVersion: String,
        humanVerificationCode: String
    ): Boolean =
        shouldForceAppUpdate(currentAppVersion, humanVerificationCode) { it.ios.minAppVersion }


    private suspend fun shouldForceAppUpdate(
        currentAppVersion: String,
        humanVerificationCode: String,
        minSupportedVersion: (MobileConfig) -> String
    ): Boolean {
        val mobileConfig = remoteConfigRepository.getMobileConfig(humanVerificationCode)
        return mobileConfig?.let {
            VersionUtils.isUpgradeRequired(minSupportedVersion(it), currentAppVersion)
        } ?: false
    }

}