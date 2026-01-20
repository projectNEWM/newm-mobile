package newm

import android.app.Application
import android.content.Context
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import io.newm.shared.config.NewmSharedBuildConfig
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Provides

interface AndroidComponent {
    @Provides
    fun providesFeatureFlagDataSource(
        application: Application,
        sharedBuildConfig: NewmSharedBuildConfig,
        log: NewmAppLogger,
        scope: CoroutineScope,
    ): FeatureFlagDataSource = AndroidComposeAppFeatureFlagManager(application, sharedBuildConfig, log, scope)

    @Provides fun provideContext(application: Application): Context = application
}
