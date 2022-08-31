package io.projectnewm.shared.users

import io.projectnewm.shared.models.User
import io.projectnewm.shared.repository.db.NewmDb

//interface UserDAO {
//    fun insert(user: User)
//    fun get(id: Int): User?
//}
//
//class UserDAOImpl(
//    db: NewmDb
//) : UserDAO {
////    private var queries: NewmDbQueries = db.newmDbQueries
////    var currentUser: User? = nil
//
//    override fun insert(newUser: User) {
//        currentUser = newUser
//    }
//
//    override fun get(id: Int): User? {
//        return User(userName = "marty@martyulrich.com", id = id.toString())
//    }
//}