package inject

import android.app.Application
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import newm.inject.ApplicationScope
import newm.inject.CommonApplicationComponent

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : CommonApplicationComponent
