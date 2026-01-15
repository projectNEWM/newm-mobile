package newm.inject

import io.newm.sharedfeatures.CommonActivityComponent
import me.tatarka.inject.annotations.Component
import io.newm.shared.di.dagger.ActivityScope

@Component
@ActivityScope
abstract class AndroidActivityComponent(
    @Component val parent: AndroidApplicationComponent,
) : CommonActivityComponent
