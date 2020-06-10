package it.uniparthenope.parthenopeddit.auth

import android.content.Context

abstract class AbstractAuthManager(
    protected var ctx: Context,
    protected val listener: logEventListener
) : AuthManager {
    interface logEventListener {
        fun onLogin(username: String)
        fun onLogout()
    }
}