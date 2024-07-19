import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.internal.implementations.ForceAppUpdateUseCaseImpl
import io.newm.shared.internal.repositories.RemoteConfigRepository
import io.newm.shared.internal.api.models.MobileClientConfig
import io.newm.shared.internal.api.models.MobileConfig
import io.newm.shared.public.usecases.ForceAppUpdateUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse


class ForceAppUpdateUseCaseTest {

    private lateinit var useCase: ForceAppUpdateUseCase

    private fun setUp() {
        // Setting up the test environment with fake implementations
        val currentVersion = "1.0.1"
        val fakeConfig = MobileConfig(
            version = 1,
            android = MobileClientConfig("1.0.2"), // You might want to adjust these based on the test
            ios = MobileClientConfig("1.0.0")
        )
        val remoteConfigRepository = FakeRemoteConfigRepository(fakeConfig)
        val sharedBuildConfig = FakeNewmSharedBuildConfig(currentVersion)
        useCase = ForceAppUpdateUseCaseImpl(remoteConfigRepository, sharedBuildConfig)
    }

    @Test
    fun `test Android update required when current version is less`() = runBlocking {
        setUp()
        assertTrue(
            useCase.isAndroidUpdateRequired(),
            "Android update should be required when the current version is less than the minimum required version"
        )
    }

    @Test
    fun `test iOS update not required when current version is greater`() = runBlocking {
        setUp()
        assertFalse(
            useCase.isiOSUpdateRequired(),
            "iOS update should not be required when the current version is greater than the minimum required version"
        )
    }



    // Inner classes for fakes
    class FakeRemoteConfigRepository(private val config: MobileConfig?) : RemoteConfigRepository {
        override suspend fun getMobileConfig(): MobileConfig? = config
    }

    class FakeNewmSharedBuildConfig(private val fakeVersion: String) : NewmSharedBuildConfig {
        override val appVersion: String = fakeVersion
        override val baseUrl: String = "https://example.com"
        override val sentryAuthToken: String = "sentryAuthToken"
        override val androidSentryDSN: String = "androidSentryDSN"
        override val googleAuthClientId: String = "googleAuthClientId"
        override val recaptchaSiteKey: String = "recaptchaSiteKey"
        override val isStagingMode: Boolean = false
    }
}