package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.User

interface AuthNamespace {
    /**
     * lOGIN
     * Takes username and password from user, calculates token and attempts login.
     * on completion user data is returned
     */
    fun login(
        token:String,
        onLogin: (user: User) -> Unit,
        onFirstLogin: (user: User) -> Unit,
        onFail: (error: String) -> Unit
    )
}