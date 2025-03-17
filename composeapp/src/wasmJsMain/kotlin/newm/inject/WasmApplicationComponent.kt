package newm.inject

import io.newm.sharedfeatures.ApplicationScope
import me.tatarka.inject.annotations.Component

@Component
@ApplicationScope
abstract class WasmApplicationComponent : CommonApplicationComponent