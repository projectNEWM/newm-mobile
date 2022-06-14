package io.projectnewm.demo

import android.app.Application
import io.projectnewm.demo.di.cacheModule
import io.projectnewm.demo.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DemoApplication)
            modules(
                networkModule,
                cacheModule
            )
        }
    }
}