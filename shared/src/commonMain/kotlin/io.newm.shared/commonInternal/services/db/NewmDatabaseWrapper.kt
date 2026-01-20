package io.newm.shared.commonInternal.services.db

import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.db.cache.NewmDatabase

class NewmDatabaseWrapper(
    private val instance: NewmDatabase?,
) {
    operator fun invoke(): NewmDatabase = instance ?: throw KMMException("Database not initialized")

    fun clear() {
        invoke().userQueries.deleteAll()
        invoke().nFTTrackQueries.deleteAll()
        invoke().walletConnectionQueries.deleteAll()
    }
}
