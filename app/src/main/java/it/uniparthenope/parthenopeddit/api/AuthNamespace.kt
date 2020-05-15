package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.User

interface AuthNamespace {
    /**
     * lOGIN
     * Takes username and password from user, calculates token and attempts login.
     * on completion user data is returned
     */
    fun login(username:String, password:String, completion: (user: User?, error: String?) -> Unit)
}