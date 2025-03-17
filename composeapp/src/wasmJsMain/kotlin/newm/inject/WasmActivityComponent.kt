package newm.inject

import io.newm.sharedfeatures.CommonActivityComponent
import io.newm.sharedfeatures.ActivityScope
import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WasmActivityComponent(
    @Component val parent: WasmApplicationComponent,
) : CommonActivityComponent