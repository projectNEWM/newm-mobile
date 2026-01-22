package io.newm.sharedfeatures.fakes

import io.newm.shared.config.NewmSharedBuildConfig

class FakeNewmSharedBuildConfig : NewmSharedBuildConfig {
    override val launchDarklyKey: String = "fake-launch-darkly-key"
    override val baseUrl: String = "https://fake.newm.io"
    override val sentryAuthToken: String = "fake-sentry-token"
    override val androidSentryDSN: String = "fake-sentry-dsn"
    override val googleAuthClientId: String = "fake-google-client-id"
    override val recaptchaSiteKey: String = "fake-recaptcha-key"
    override var isStagingMode: Boolean = false
    override var isDebug: Boolean = true
}
