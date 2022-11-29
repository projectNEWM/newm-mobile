import io.projectnewm.shared.di.commonModule
import io.projectnewm.shared.login.models.LoginStatus
import io.projectnewm.shared.login.models.RequestEmailStatus
import io.projectnewm.shared.login.repository.LogInRepository
import io.projectnewm.shared.platformModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

//TODO: "Find a way to reset dependencies per test run"
@Ignore()
class LoginRepositoryTest : KoinTest {

    private val repo: LogInRepository by inject()

    init {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(commonModule(enableNetworkLogs = false), platformModule())
        }
    }

    @Test
    fun testActualUserLogin() = runTest {
        val result = repo.logIn("cescobar+5@newm.io", "Password18")
        assertTrue(result is LoginStatus.Success)
    }

    @Test
    fun testMalformRequest() = runTest {
        val result = repo.logIn("cescobar+5@newm.io", "p")
        assertTrue(result is LoginStatus.WrongPassword)
    }


    @Test
    fun testRequestCodeValidEmail() = runTest {
        val result = repo.requestEmailConfirmationCode("cescobar+5@newm.io")
        assertTrue(result is RequestEmailStatus.Success)
    }

    @Test
    fun testRequestCodeInvalidEmail() = runTest {
        val result = repo.requestEmailConfirmationCode("cescobar+5")
        assertTrue(result is RequestEmailStatus.Failure)
    }
}