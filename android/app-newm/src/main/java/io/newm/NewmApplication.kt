package io.newm

import android.app.Application
import co.touchlab.kermit.Logger
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.initKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

open class NewmApplication : Application() {

    private val logout: Logout by inject()
    private val sharedBuildConfig by inject<NewmSharedBuildConfig>()

    override fun onCreate() {
        Logger.d { "NewmAndroid - NewmApplication" }
        super.onCreate()
        initKoin()
        logout.register()
    }

    private fun initKoin(enableNetworkLogs: Boolean = true) {
        initKoin(enableNetworkLogs = enableNetworkLogs) {
            androidLogger(if (enableNetworkLogs) Level.INFO else Level.NONE)
            androidContext(this@NewmApplication)
            modules(
                viewModule,
                androidModules
            )
        }
    }
}
