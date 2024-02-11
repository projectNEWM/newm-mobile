package io.newm.shared.internal.db

import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.public.models.error.KMMException

class NewmDatabaseWrapper(private val instance: NewmDatabase?) {
    operator fun invoke(): NewmDatabase {
        return instance ?: throw KMMException("Database not initialized")
    }

    fun clear() {
        invoke().userQueries.deleteAll()
        invoke().nFTTrackQueries.deleteAll()
    }
}
