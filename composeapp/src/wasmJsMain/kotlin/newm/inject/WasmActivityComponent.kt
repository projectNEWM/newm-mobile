package newm.inject

import me.tatarka.inject.annotations.Component

@Component
@ActivityScope
abstract class WasmActivityComponent(
    @Component val parent: WasmApplicationComponent,
) : CommonActivityComponent