package io.newm.shared.config

import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.generated.BuildConfig
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Enum to represent the mode in which the app is running
enum class Mode {
    PRODUCTION,
    STAGING,
}

/**
 * BuildConfiguration class to manage app configurations based on the mode.
 *
 * Note: Mode is cached in memory for synchronous access. Changes are persisted asynchronously to
 * storage. The initial value defaults to PRODUCTION and is loaded from storage asynchronously
 * during initialization.
 */
class NewmSharedBuildConfigImpl(
    private val storage: PreferencesDataStore,
    private val scope: CoroutineScope,
) : NewmSharedBuildConfig {
    companion object {
        private const val APP_MODE = "app_mode"
    }

    private val defaultMode = Mode.PRODUCTION

    // Cached mode value using atomicfu for cross-platform thread safety
    // Starts with default value; actual value loaded asynchronously
    private val cachedMode = atomic(defaultMode)
    private val initialized = atomic(false)

    init {
        // Load the persisted mode value asynchronously
        scope.launch {
            try {
                val modeString = storage.getString(APP_MODE)
                if (modeString != null) {
                    try {
                        cachedMode.value = Mode.valueOf(modeString)
                    } catch (e: IllegalArgumentException) {
                        // Invalid mode string, keep default
                    }
                }
            } catch (e: Exception) {
                // Storage access failed, keep default
            }
            initialized.value = true
        }
    }

    var mode: Mode
        get() = cachedMode.value
        set(value) {
            cachedMode.value = value
            // Persist asynchronously - fire and forget
            scope.launch { storage.saveString(APP_MODE, value.name) }
        }

    override val launchDarklyKey: String
        get() = BuildConfig.LAUNCHDARKLY_MOBILE_KEY

    override val baseUrl: String
        get() =
            when (mode) {
                Mode.STAGING -> BuildConfig.STAGING_URL
                Mode.PRODUCTION -> BuildConfig.PRODUCTION_URL
            }

    override val sentryAuthToken: String
        get() = BuildConfig.SENTRY_AUTH_TOKEN

    override val androidSentryDSN: String
        get() = BuildConfig.ANDROID_SENTRY_DSN

    override val googleAuthClientId: String
        get() = BuildConfig.GOOGLE_AUTH_CLIENT_ID

    override val recaptchaSiteKey: String
        get() = BuildConfig.RECAPTCHA_SITE_KEY

    override val isStagingMode: Boolean
        get() = mode == Mode.STAGING

    override val isDebug: Boolean
        get() = BuildConfig.IS_DEBUG
}

interface NewmSharedBuildConfig {
    val launchDarklyKey: String
    val baseUrl: String
    val sentryAuthToken: String
    val androidSentryDSN: String
    val googleAuthClientId: String
    val recaptchaSiteKey: String
    val isStagingMode: Boolean
    val isDebug: Boolean
}
