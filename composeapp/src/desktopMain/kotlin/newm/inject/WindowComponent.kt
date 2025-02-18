package newm.inject

import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WindowComponent(
    @Component val parent: DesktopApplicationComponent,
) : CommonActivityComponent