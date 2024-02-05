package io.newm.shared.internal.repositories

import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DatabaseRepository(
    dbWrapper: NewmDatabaseWrapper,
    private val scope: CoroutineScope,
) : KoinComponent {

    private val database = dbWrapper.instance ?: throw KMMException("Database not initialized")

    fun clear() {
        scope.launch {
            database.userQueries.deleteAll()
            database.nFTTrackQueries.deleteAll()
        }
    }
}