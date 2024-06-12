package io.newm.shared.config

import io.newm.shared.generated.BuildConfig
import io.newm.shared.internal.db.PreferencesDataStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Enum to represent the mode in which the app is running
enum class Mode {
    PRODUCTION,
    STAGING
}

// BuildConfiguration class to manage app configurations based on the mode
class NewmSharedBuildConfigImpl: NewmSharedBuildConfig, KoinComponent {

    private val APP_MODE = "app_mode"

    private val defaultMode
        get() = Mode.PRODUCTION

    private val storage: PreferencesDataStore by inject()
    var mode: Mode
        get() {
            val modeString = storage.getString(APP_MODE) ?: defaultMode.name
            return Mode.valueOf(modeString)
        }
        set(value) {
            storage.saveString(APP_MODE, value.name)
        }

    init {
        mode = defaultMode
    }

    override val baseUrl: String
        get() = when (mode) {
            Mode.STAGING -> BuildConfig.STAGING_URL
            Mode.PRODUCTION -> BuildConfig.PRODUCTION_URL
        }

    override val sentryAuthToken: String
        get() = BuildConfig.SENTRY_AUTH_TOKEN

    override val googleAuthClientId: String
        get() = BuildConfig.GOOGLE_AUTH_CLIENT_ID

    override val walletConnectProjectId: String
        get() = BuildConfig.WALLET_CONNECT_PROJECT_ID

    override val recaptchaSiteKey: String
        get() = BuildConfig.RECAPTCHA_SITE_KEY

    override val isStagingMode: Boolean
        get() = mode == Mode.STAGING

    override val appVersion: String
        get() = BuildConfig.NEWM_MOBILE_APP_VERSION
}

interface NewmSharedBuildConfig {
    val baseUrl: String
    val appVersion: String
    val sentryAuthToken: String
    val googleAuthClientId: String
    val walletConnectProjectId: String
    val recaptchaSiteKey: String
    val isStagingMode: Boolean
}
