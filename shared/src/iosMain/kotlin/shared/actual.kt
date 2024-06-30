package shared

import androidx.room.RoomDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import database.getNewmAppDatabase
import io.ktor.client.engine.darwin.Darwin
import io.newm.shared.database.NewmAppDatabase
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.internal.db.PreferencesDataStore
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import org.koin.dsl.module

actual fun platformModule() = module {
	single {
		val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
		NewmDatabaseWrapper(NewmDatabase(driver))
	}
    single { Darwin.create() }
	single<PreferencesDataStore> { PreferencesDataStoreImpl() }
	single<RoomDatabase.Builder<NewmAppDatabase>> { getNewmAppDatabase() }
	single<TokenManager> { TokenManagerImpl(get(), get()) }
}

actual fun getPlatformName(): String = "iOS"
