package it.uniparthenope.parthenopeddit.auth

interface AuthManager {
    fun isUserLogged(): Boolean
    fun login(token:String, username:String)
    fun logout()
}