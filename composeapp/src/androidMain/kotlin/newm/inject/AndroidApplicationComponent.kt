package newm.inject

import android.app.Application
import io.newm.shared.di.dagger.CommonApplicationComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import io.newm.sharedfeatures.ApplicationScope
import newm.AndroidComponent

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : CommonApplicationComponent, AndroidComponent
