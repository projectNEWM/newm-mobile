package newm.inject

import io.newm.sharedfeatures.CommonActivityComponent
import io.newm.shared.di.dagger.ActivityScope
import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WindowComponent(
    @Component val parent: DesktopApplicationComponent,
) : CommonActivityComponent