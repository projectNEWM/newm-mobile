package newm.inject

import io.newm.shared.di.dagger.ActivityScope
import io.newm.sharedfeatures.CommonActivityComponent
import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class AndroidActivityComponent(
    @Component val parent: AndroidApplicationComponent,
) : CommonActivityComponent
