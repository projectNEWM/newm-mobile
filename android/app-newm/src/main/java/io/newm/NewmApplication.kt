package io.newm

import android.app.Application
import co.touchlab.kermit.Logger
import coil.ImageLoader
import coil.ImageLoaderFactory
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.shared.di.initKoin
import io.newm.utils.NewmImageLoaderFactory
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

open class NewmApplication : Application(), ImageLoaderFactory {

    private val logout: Logout by inject()
    private val imageLoaderFactory by lazy { NewmImageLoaderFactory(this) }

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

    override fun newImageLoader(): ImageLoader {
        return imageLoaderFactory.newImageLoader()
    }
}
