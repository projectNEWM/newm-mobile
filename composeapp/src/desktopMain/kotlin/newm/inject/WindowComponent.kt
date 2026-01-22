package newm.inject

import io.newm.shared.di.dagger.ActivityScope
import io.newm.sharedfeatures.di.CommonActivityComponent
import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WindowComponent(
    @Component val parent: DesktopApplicationComponent,
) : CommonActivityComponent
