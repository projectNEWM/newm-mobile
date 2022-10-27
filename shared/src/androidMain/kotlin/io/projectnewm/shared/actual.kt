package io.projectnewm.shared

import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.*
import io.newm.common.db.NewmDatabase
import io.projectnewm.shared.db.NewmDatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
}