package io.projectnewm.shared

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.newm.common.db.NewmDatabase
import io.projectnewm.shared.db.NewmDatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single {
        HttpClient(Darwin) {
            engine {
                configureRequest {
                    setAllowsCellularAccess(true)
                }
            }
        }
    }
}