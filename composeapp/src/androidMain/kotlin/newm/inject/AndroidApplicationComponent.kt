package newm.inject

import android.app.Application
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import io.newm.sharedfeatures.ApplicationScope
import newm.inject.CommonApplicationComponent

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : CommonApplicationComponent
