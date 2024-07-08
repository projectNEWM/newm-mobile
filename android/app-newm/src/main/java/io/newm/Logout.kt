@file:OptIn(DelicateCoroutinesApi::class)

package io.newm

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.usecases.LoginUseCase
import io.newm.shared.public.usecases.UserSessionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class Logout(
    private val loginUseCase: LoginUseCase,
    private val userSessionUseCase: UserSessionUseCase,
    private val restartApp: RestartApp,
    private val scope: CoroutineScope,
    private val logger: NewmAppLogger
) {

    fun signOutUser() = try {
        scope.launch {
            loginUseCase.logout()
        }
        restartApp.run()
        logger.info("Logout", "Logout successful")
    } catch (e: Exception) {
        logger.error("Logout", "Logout failed", e)
    }

    fun register() {
        GlobalScope.launch {
            userSessionUseCase.isLoggedInFlow().collect { isLoggedIn ->
                if (!isLoggedIn) {
                    logger.debug("Logout", "User is not logged in")
                    signOutUser()
                }
            }
        }
    }
}

class RestartApp(
    private val context: Context
) : KoinComponent, Runnable {
    override fun run() {
        ProcessPhoenix.triggerRebirth(context)
    }
}
