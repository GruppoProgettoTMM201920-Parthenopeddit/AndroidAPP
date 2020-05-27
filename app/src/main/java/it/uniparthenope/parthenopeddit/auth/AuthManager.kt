package it.uniparthenope.parthenopeddit.auth

interface AuthManager {
    fun getToken(username: String, password: String): String
    fun isUserLogged(): Boolean
    fun login(token:String, username:String)
    fun logout()

    var username: String?
    var token: String?
}