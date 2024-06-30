package shared

import androidx.room.RoomDatabase
import android.accounts.AccountManager
import com.squareup.sqldelight.android.AndroidSqliteDriver
import database.getNewmRoomDatabaseBuilder
import io.ktor.client.engine.android.Android
import io.newm.shared.database.NewmAppDatabase
import io.newm.shared.internal.services.db.NewmDatabaseWrapper
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.TokenManager
import io.newm.shared.internal.db.PreferencesDataStore
import io.newm.shared.internal.implementations.PreferencesDataStoreImpl
import io.newm.shared.internal.implementations.TokenManagerImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
    single<PreferencesDataStore> { PreferencesDataStoreImpl(get()) }
    single<RoomDatabase.Builder<NewmAppDatabase>> { getNewmRoomDatabaseBuilder( get() ) }
    single { AccountManager.get(get()) }
    single<TokenManager> { TokenManagerImpl(get(), get()) }
}

actual fun getPlatformName(): String = "Android"