package io.newm.shared.internal.useCases

import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.repositories.models.OAuthData
import io.newm.shared.internal.useCases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.LoginUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class LoginUseCaseImpl(
    private val repository: LogInRepository
) : LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(email: String, password: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.logIn(email = email, password = password)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithGoogle(idToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Google(idToken))
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithFacebook(accessToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Facebook(accessToken))
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithLinkedIn(accessToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.LinkedIn(accessToken))
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithApple(idToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Apple(idToken))
        }
    }

    override fun logout() {
        repository.logout()
    }
}