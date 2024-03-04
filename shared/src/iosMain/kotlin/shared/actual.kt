package shared

import com.liftric.kvault.KVault
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Darwin.create() }
    single { KVault() }
}

actual fun getPlatformName(): String = "iOS"