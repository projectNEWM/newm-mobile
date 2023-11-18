package io.newm.shared.internal.implementations

import io.newm.shared.login.repository.LogInRepository
import io.newm.shared.login.repository.OAuthData
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.LoginUseCase
import kotlin.coroutines.cancellation.CancellationException

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
}