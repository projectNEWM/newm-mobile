package newm

import android.app.Application
import io.newm.shared.NewmAppLogger
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.commonPublic.featureflags.FeatureFlagDataSource
import me.tatarka.inject.annotations.Provides

interface AndroidComponent {

    @Provides
    fun providesFeatureFlagDataSource(
        application: Application,
        sharedBuildConfig: NewmSharedBuildConfig,
        log: NewmAppLogger
    ): FeatureFlagDataSource {
        return AndroidComposeAppFeatureFlagManager(application, sharedBuildConfig, log)
    }
}