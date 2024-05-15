package io.newm.shared.public.usecases

/**
 * Defines the contract for checking if a forced update is required for mobile applications.
 * This interface provides methods to determine whether the current version of the Android or iOS
 * app meets the minimum version requirements specified in the remote configuration.
 */
interface ForceAppUpdateUseCase {

    /**
     * Checks if the current Android application version is lower than the minimum required version.
     *
     * @return True if the current app version is below the minimum required version, indicating
     *         that an update is required; false otherwise.
     */
    suspend fun isAndroidUpdateRequired(): Boolean

    /**
     * Checks if the current iOS application version is lower than the minimum required version.
     *
     * @return True if the current app version is below the minimum required version, indicating
     *         that an update is required; false otherwise.
     */
    suspend fun isiOSUpdateRequired(): Boolean
}