package io.newm.shared.login

import io.newm.shared.db.NewmDatabaseWrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserSession {
    fun isUserLoggedIn(): Boolean
}

internal class UserSessionImpl : KoinComponent, UserSession {
    private val db: NewmDatabaseWrapper by inject()

    override fun isUserLoggedIn(): Boolean {
        val userSession = db.instance?.newmAuthQueries?.selectAll()?.executeAsList()
        return userSession?.isNotEmpty() == true
    }
}