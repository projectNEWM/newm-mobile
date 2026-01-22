package newm.inject

import io.newm.shared.di.dagger.ActivityScope
import io.newm.sharedfeatures.di.CommonActivityComponent
import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WasmActivityComponent(
    @Component val parent: WasmApplicationComponent,
) : CommonActivityComponent
