package io.newm.shared.usecases

import io.newm.shared.login.repository.KMMException
import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.login.repository.OAuthData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun logIn(email: String, password: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithGoogle(idToken: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithFacebook(accessToken: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithLinkedIn(accessToken: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun logInWithApple(idToken: String)

    fun logOut()
    val userIsLoggedIn: Boolean
}

internal class LoginUseCaseImpl(private val repository: LogInRepository) : LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(email: String, password: String) {
        return repository.logIn(email = email, password = password)
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithGoogle(idToken: String) {
        return repository.oAuthLogin(OAuthData.Google(idToken))
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithFacebook(accessToken: String) {
        return repository.oAuthLogin(OAuthData.Facebook(accessToken))
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithLinkedIn(accessToken: String) {
        return repository.oAuthLogin(OAuthData.LinkedIn(accessToken))
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithApple(idToken: String) {
        return repository.oAuthLogin(OAuthData.Apple(idToken))
    }

    override fun logOut() {
        repository.logOut()
    }

    override val userIsLoggedIn: Boolean
        get() = repository.userIsLoggedIn()
}

class LoginUseCaseProvider(): KoinComponent {
    private val loginUseCase: LoginUseCase by inject()

    fun get(): LoginUseCase {
        return this.loginUseCase
    }
}