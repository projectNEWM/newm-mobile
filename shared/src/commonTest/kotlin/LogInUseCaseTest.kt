
import io.newm.shared.di.commonModule
import io.newm.shared.internal.domainservices.Mocks.MockHumanVerificationService
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.usecases.LoginUseCaseImpl
import io.newm.shared.public.usecases.mocks.MockConnectWalletUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import shared.platformModule
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseImplTest {
    private val repository = LogInRepository()
    private val connectWalletUseCase = MockConnectWalletUseCase()
    private val humanVerificationService = MockHumanVerificationService()
    private val loginUseCase = LoginUseCaseImpl(repository, connectWalletUseCase, humanVerificationService)

    init {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(commonModule(enableNetworkLogs = false), platformModule())
        }
    }

    @Test
    fun `logIn should call use case with correct parameters`() = runTest {
        val email = "mulrich@newm.io"
        val password = "Password1"

        loginUseCase.logIn(email, password)
    }
}
