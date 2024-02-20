package io.newm.shared.config

import com.liftric.kvault.KVault
import io.newm.shared.generated.SharedBuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Enum to represent the mode in which the app is running
enum class Mode {
    PRODUCTION,
    DEVELOPMENT
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
            Mode.DEVELOPMENT -> SharedBuildConfig.DEVELOPMENT_URL
            Mode.PRODUCTION -> SharedBuildConfig.PRODUCTION_URL
        }

    val sentryAuthToken: String
        get() = SharedBuildConfig.SENTRY_AUTH_TOKEN

    val googleAuthClientId: String
        get() = SharedBuildConfig.GOOGLE_AUTH_CLIENT_ID

    val walletConnectProjectId: String
        get() = SharedBuildConfig.WALLET_CONNECT_PROJECT_ID

    val recaptchaSiteKey: String
        get() = SharedBuildConfig.RECAPTCHA_SITE_KEY

    val isDevMode: Boolean
        get() = mode == Mode.DEVELOPMENT
}

