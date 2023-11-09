package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
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

class LoginUseCaseProvider(): KoinComponent {
    private val loginUseCase: LoginUseCase by inject()

    fun get(): LoginUseCase {
        return this.loginUseCase
    }
}