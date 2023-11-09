package io.newm

import android.content.Context
import co.touchlab.kermit.Logger
import com.jakewharton.processphoenix.ProcessPhoenix
import io.newm.shared.public.usecases.UserSessionUseCase
import org.koin.core.component.KoinComponent
import java.util.concurrent.Callable

class Logout(
    private val userSession: UserSessionUseCase,
    private val restartApp: RestartApp,
) : KoinComponent, Callable<Int> {

    private val logger = Logger.withTag("NewmAndroid-Logout")

    override fun call(): Int {
        return try {
            userSession.logout()
            restartApp.run()
            logger.d("Logout successful")
            0
        } catch (e: Exception) {
            logger.d("Logout Exception: $e")
            1
        }

    }
}

class RestartApp(
    private val context: Context
): KoinComponent, Runnable {
    override fun run() {
        ProcessPhoenix.triggerRebirth(context)
    }
}