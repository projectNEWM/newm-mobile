package io.newm.shared.internal.usecases

import io.newm.shared.internal.domainservices.HumanVerificationAction
import io.newm.shared.internal.domainservices.HumanVerificationService
import io.newm.shared.internal.repositories.LogInRepository
import io.newm.shared.internal.repositories.models.OAuthData
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.LoginUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class LoginUseCaseImpl(
    private val repository: LogInRepository,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val humanVerificationService: HumanVerificationService
) : LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(email: String, password: String) {
        return mapErrorsSuspend {
            val humanVerificationToken = humanVerificationService.verify(HumanVerificationAction.LOGIN_EMAIL)
            return@mapErrorsSuspend repository.logIn(email = email, password = password, humanVerificationToken)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithGoogle(idToken: String) {
        return mapErrorsSuspend {
            val humanVerificationToken = humanVerificationService.verify(HumanVerificationAction.LOGIN_GOOGLE)
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Google(idToken), humanVerificationToken)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithFacebook(accessToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Facebook(accessToken), "")
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithLinkedIn(accessToken: String) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.LinkedIn(accessToken), "")
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithApple(idToken: String) {
        return mapErrorsSuspend {
            val humanVerificationToken = humanVerificationService.verify(HumanVerificationAction.LOGIN_EMAIL)
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Apple(idToken), humanVerificationToken)
        }
    }

    override fun logout() {
        connectWalletUseCase.disconnect()
        repository.logout()
    }
}
