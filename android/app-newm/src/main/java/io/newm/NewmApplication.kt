package io.newm

import android.app.Application
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.google.android.recaptcha.Recaptcha
import com.google.firebase.FirebaseApp
import io.newm.BuildConfig.DEBUG
import io.newm.BuildConfig.VERSION_NAME
import io.newm.di.android.androidModules
import io.newm.di.android.viewModule
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.initKoin
import io.newm.sharedfeatures.screens.auth.login.RecaptchaClientProvider
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.qualifier.named

open class NewmApplication :
    Application(),
    SingletonImageLoader.Factory {
    private val analyticsTracker: NewmAppEventLogger by inject()
    private val config: NewmSharedBuildConfig by inject()
    private val featureFlagService: FeatureFlagService by inject()
    private val forceAppUpdateViewModel: ForceAppUpdateViewModel by inject()
    private val imageLoaderFactory by lazy { NewmImageLoaderFactory() }
    private val logger: NewmAppLogger by inject()
    private val logout: Logout by inject()
    private val recaptchaClientProvider: RecaptchaClientProvider by inject()

    // Application-level coroutine scope
    private val applicationScope: CoroutineScope by inject(named("mainScope"))

    override fun onCreate() {
        super.onCreate()
        initKoin()
        FirebaseApp.initializeApp(this)
        logout.register()
        bindClientImplementations()
        initializeRecaptchaClient()
        prefetchFeatureFlags()
    }

    private fun prefetchFeatureFlags() {
        applicationScope.launch {
            try {
                logger.breadcrumb("Application", "Prefetching feature flags on app launch")
                featureFlagService.prefetchAllFlags()
                logger.breadcrumb("Application", "Feature flags prefetched successfully")
            } catch (e: Exception) {
                logger.error(
                    tag = "Application",
                    message = "Failed to prefetch feature flags",
                    exception = e,
                )
            }
        }
    }

    private fun initializeRecaptchaClient() {
        forceAppUpdateViewModel.viewModelScope.launch {
            runCatching { Recaptcha.fetchClient(this@NewmApplication, config.recaptchaSiteKey) }
                .onSuccess { client ->
                    recaptchaClientProvider.setRecaptchaClient(client)
                    forceAppUpdateViewModel.checkForUpdates(currentVersion = VERSION_NAME)
                }.onFailure { exception ->
                    logger.error(
                        tag = "RecaptchaClient",
                        message = "Failed to initialize Recaptcha client.",
                        exception = exception,
                    )
                }
        }
    }

    private fun initKoin() {
        val enableLogs = DEBUG
        initKoin(enableNetworkLogs = enableLogs) {
            androidLogger(if (enableLogs) Level.INFO else Level.NONE)
            androidContext(this@NewmApplication)
            modules(androidModules, viewModule)
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

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoaderFactory.newImageLoader(context)
}
