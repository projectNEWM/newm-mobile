package inject

import me.tatarka.inject.annotations.Component
import newm.inject.ActivityScope
import newm.inject.CommonActivityComponent

@Component
@ActivityScope
abstract class AndroidActivityComponent(
    @Component val parent: AndroidApplicationComponent,
) : CommonActivityComponent
