package newm.inject

import android.app.Application
import io.newm.shared.config.NewmSharedBuildConfig
import io.newm.shared.di.dagger.ApplicationScope
import io.newm.shared.di.dagger.CommonApplicationComponent
import io.newm.sharedfeatures.login.RecaptchaClientProvider
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import newm.AndroidComponent

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : CommonApplicationComponent, AndroidComponent {
    abstract val recaptchaClientProvider: RecaptchaClientProvider
    abstract val config: NewmSharedBuildConfig
    @ApplicationScope
    @Provides
    override fun provideCoroutineScope(): CoroutineScope = super.provideCoroutineScope()
}
