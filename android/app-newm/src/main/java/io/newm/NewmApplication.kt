package io.newm

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import io.newm.BuildConfig.*
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.shared.NewmAppAnalyticsTracker
import io.newm.shared.NewmAppLogger
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.initKoin
import io.newm.utils.AndroidNewmAppAnalyticsTracker
import io.newm.utils.AndroidNewmAppLogger
import io.newm.utils.NewmImageLoaderFactory
import io.sentry.Hint
import io.sentry.SentryEvent
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid
import io.sentry.android.core.SentryAndroidOptions
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


open class NewmApplication : Application(), ImageLoaderFactory {

    private val logout: Logout by inject()
    private val logger: NewmAppLogger by inject()
    private val analyticsTracker: NewmAppAnalyticsTracker by inject()
    private val imageLoaderFactory by lazy { NewmImageLoaderFactory(this) }
    private val config: NewmSharedBuildConfig by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        logout.register()
        bindClientImplementations()
    }

    private fun initKoin() {
        val enableLogs = DEBUG
        initKoin(enableNetworkLogs = enableLogs) {
            androidLogger(if (enableLogs) Level.INFO else Level.NONE)
            androidContext(this@NewmApplication)
            modules(
                viewModule,
                androidModules
            )
        }
    }

    private fun bindClientImplementations() {
        SentryAndroid.init(
            this
        ) { options: SentryAndroidOptions ->
            options.dsn = config.androidSentryDSN
            options.environment = if (DEBUG) "development" else "production"
            options.beforeSend =
                SentryOptions.BeforeSendCallback { event: SentryEvent, hint: Hint ->
                    if (SentryLevel.DEBUG == event.level) {
                        null
                    } else {
                        event
                    }
                }
        }
        analyticsTracker.setClientAnalyticsTracker(AndroidNewmAppAnalyticsTracker(logger))
        logger.setClientLogger(AndroidNewmAppLogger(analyticsTracker))
    }

    override fun newImageLoader(): ImageLoader {
        return imageLoaderFactory.newImageLoader()
    }
}
