package io.newm

import android.app.Application
import co.touchlab.kermit.Logger
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.shared.di.initKoin
import org.koin.android.ext.koin.androidContext


//@ShowkaseRoot
open class NewmApplication : Application() { //}, ShowkaseRootModule {

    override fun onCreate() {
        Logger.d { "NewmAndroid - NewmApplication" }
        super.onCreate()

        initKoin(enableNetworkLogs = true) {
            androidContext(this@NewmApplication)
            modules(viewModule, androidModules)
        }
    }
}
