package io.newm

import android.app.Application
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.google.android.recaptcha.Recaptcha
import com.google.firebase.FirebaseApp
import io.newm.BuildConfig.*
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.shared.NewmAppLogger
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.initKoin
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.utils.AndroidEventLoggerImpl
import io.newm.utils.AndroidNewmAppLogger
import io.newm.utils.AppForegroundBackgroundTracker
import io.newm.utils.ForceAppUpdateViewModel
import io.newm.utils.NewmImageLoaderFactory
import io.sentry.Hint
import io.sentry.SentryEvent
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid
import io.sentry.android.core.SentryAndroidOptions
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

open class NewmApplication : Application(), ImageLoaderFactory {

    private val analyticsTracker: NewmAppEventLogger by inject()
    private val config: NewmSharedBuildConfig by inject()
    private val forceAppUpdateViewModel: ForceAppUpdateViewModel by inject()
    private val imageLoaderFactory by lazy { NewmImageLoaderFactory(this@NewmApplication) }
    private val logger: NewmAppLogger by inject()
    private val logout: Logout by inject()
    private val recaptchaClientProvider: RecaptchaClientProvider by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        FirebaseApp.initializeApp(this)
        logout.register()
        bindClientImplementations()
        initializeRecaptchaClient()
    }

    private fun initializeRecaptchaClient() {
        forceAppUpdateViewModel.viewModelScope.launch {
            Recaptcha.getClient(this@NewmApplication, config.recaptchaSiteKey, timeout = 50000L)
                .onSuccess { client ->
                    recaptchaClientProvider.setRecaptchaClient(client)
                    forceAppUpdateViewModel.checkForUpdates(currentVersion = BuildConfig.VERSION_NAME)
                }.onFailure { exception ->
                    logger.error(
                        tag = "RecaptchaClient",
                        message = "Failed to initialize Recaptcha client.",
                        exception = exception
                    )
                }
        }
    }

    private fun initKoin() {
        val enableLogs = DEBUG
        initKoin(enableNetworkLogs = enableLogs) {
            androidLogger(if (enableLogs) Level.INFO else Level.NONE)
            androidContext(this@NewmApplication)
            modules(
                androidModules,
                viewModule
            )
        }
    }

    private fun bindClientImplementations() {
        setupSentry()
        setupLogger()
        registerActivityLifecycleCallbacks(AppForegroundBackgroundTracker(analyticsTracker, logger))
    }

    private fun setupSentry() {
        SentryAndroid.init(this) { options: SentryAndroidOptions ->
            options.dsn = config.androidSentryDSN
            options.environment = if (DEBUG) "development" else "production"
            options.beforeSend =
                SentryOptions.BeforeSendCallback { event: SentryEvent, hint: Hint ->
                    if (SentryLevel.DEBUG == event.level) null else event
                }
        }
    }

    private fun setupLogger() {
        logger.setClientLogger(AndroidNewmAppLogger(analyticsTracker))
        analyticsTracker.setClientAnalyticsTracker(AndroidEventLoggerImpl(logger))
    }

    override fun newImageLoader(): ImageLoader {
        return imageLoaderFactory.newImageLoader()
    }
}
