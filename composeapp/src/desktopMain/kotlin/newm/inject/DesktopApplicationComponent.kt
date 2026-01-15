package newm.inject

import io.newm.shared.di.dagger.ApplicationScope
import io.newm.shared.di.dagger.CommonApplicationComponent
import me.tatarka.inject.annotations.Component
import newm.DesktopComponent

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : CommonApplicationComponent, DesktopComponent
