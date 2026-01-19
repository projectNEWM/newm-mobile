package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.db.PreferencesDataStore
import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.LogInRepository
import io.newm.shared.commonInternal.repositories.models.OAuthData
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.LoginUseCase
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.cancellation.CancellationException

@Inject
class LoginUseCaseImpl(
    private val repository: LogInRepository,
    private val dataStore: PreferencesDataStore,
) : LoginUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logIn(
        email: String,
        password: String,
        humanVerificationCode: String,
    ) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.logIn(email.trim(), password.trim(), humanVerificationCode)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logInWithGoogle(
        idToken: String,
        humanVerificationCode: String,
    ) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(
                OAuthData.Google(idToken),
                humanVerificationCode,
            )
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
    override suspend fun logInWithApple(
        idToken: String,
        humanVerificationCode: String,
    ) {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.oAuthLogin(OAuthData.Apple(idToken), humanVerificationCode)
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun logout() {
        mapErrorsSuspend {
            repository.logout()
            dataStore.clearAll()
        }
    }
}
