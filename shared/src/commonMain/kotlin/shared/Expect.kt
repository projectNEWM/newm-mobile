package shared

import org.koin.core.module.Module

expect fun platformModule(): Module

expect fun getPlatformName(): String
