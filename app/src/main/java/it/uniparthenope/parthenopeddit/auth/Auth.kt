package it.uniparthenope.parthenopeddit.auth

class Auth : AuthManager {
    override fun isUserLogged(): Boolean {
        return true
    }

    override fun login(token: String, username: String) {
        return
    }

    override fun logout() {
        return
    }

    val username = "user"
    val token = "token"
}