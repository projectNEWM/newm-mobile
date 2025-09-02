package io.newm.shared.commonInternal.services.db

import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.commonPublic.models.error.KMMException

class NewmDatabaseWrapper(private val instance: NewmDatabase?) {
    operator fun invoke(): NewmDatabase {
        return instance ?: throw KMMException("Database not initialized")
    }

    fun clear() {
        invoke().userQueries.deleteAll()
        invoke().nFTTrackQueries.deleteAll()
        invoke().walletConnectionQueries.deleteAll()
    }
}
