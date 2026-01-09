package newm.inject

import io.newm.shared.di.dagger.CommonApplicationComponent
import io.newm.sharedfeatures.ApplicationScope
import me.tatarka.inject.annotations.Component
import newm.WasmComponent

@Component
@ApplicationScope
abstract class WasmApplicationComponent : CommonApplicationComponent, WasmComponent