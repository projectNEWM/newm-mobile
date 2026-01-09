package newm.inject

import io.newm.shared.di.dagger.CommonApplicationComponent
import io.newm.sharedfeatures.ApplicationScope
import me.tatarka.inject.annotations.Component
import newm.DesktopComponent

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : CommonApplicationComponent, DesktopComponent
