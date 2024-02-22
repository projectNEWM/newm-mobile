package io.newm.shared.config

import com.liftric.kvault.KVault
import newm_mobile.shared.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Enum to represent the mode in which the app is running
enum class Mode {
    PRODUCTION,
    STAGING
}

// BuildConfiguration class to manage app configurations based on the mode
object NewmSharedBuildConfig: KoinComponent {

    private const val APP_MODE = "app_mode"

    private val storage: KVault by inject()
    var mode: Mode
        get() {
            val modeString = storage.string(APP_MODE) ?: Mode.PRODUCTION.name
            return Mode.valueOf(modeString)
        }
        set(value) {
            storage.set(APP_MODE, value.name)
        }

    init {
        // If there's no mode saved in KVault, default to Production.
        if (storage.string(APP_MODE) == null) {
            mode = Mode.PRODUCTION
        }
    }

    val baseUrl: String
        get() = when (mode) {
            Mode.STAGING -> BuildConfig.STAGING_URL
            Mode.PRODUCTION -> BuildConfig.PRODUCTION_URL
        }

    val sentryAuthToken: String
        get() = BuildConfig.SENTRY_AUTH_TOKEN

    val googleAuthClientId: String
        get() = BuildConfig.GOOGLE_AUTH_CLIENT_ID

    val walletConnectProjectId: String
        get() = BuildConfig.WALLET_CONNECT_PROJECT_ID

    val recaptchaSiteKey: String
        get() = BuildConfig.RECAPTCHA_SITE_KEY

    val isStagingMode: Boolean
        get() = mode == Mode.STAGING
}

