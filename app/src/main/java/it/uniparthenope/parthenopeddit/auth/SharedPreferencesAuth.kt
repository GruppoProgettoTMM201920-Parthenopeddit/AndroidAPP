package it.uniparthenope.parthenopeddit.auth

import android.content.Context
import android.util.Base64
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.util.SingletonHolder

class SharedPreferencesAuth(private var context: Context) : AuthManager {
    companion object : SingletonHolder<SharedPreferencesAuth, Context>(::SharedPreferencesAuth) {
        private const val TOKEN = "token"
        private const val USERNAME = "username"
    }

    private fun getValue(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    private fun setValue(key: String, value: String?) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    private var onLoginListener: ((username: String)->Unit)? = null
    private var onLogoutListener: (()->Unit)? = null

    fun setEventListener(onLogin: ((username: String) -> Unit)?, onLogout: (() -> Unit)?) {
        onLoginListener = onLogin
        onLogoutListener = onLogout
    }

    override var username:String?
        get() = getValue(USERNAME)
        set(newUsername) = setValue(USERNAME, newUsername)

    override var token:String?
        get() = getValue(TOKEN)
        set(newToken) = setValue(TOKEN, newToken)

    override fun login(token:String, username:String) {
        this.username = username
        this.token = token

        onLoginListener?.invoke(username)
    }

    override fun logout() {
        this.username = null
        this.token = null

        onLogoutListener?.invoke()
    }

    override fun getToken(username: String, password: String): String {
        return Base64.encodeToString("${username}:${password}".toByteArray(), Base64.NO_WRAP)
    }

    override fun isUserLogged(): Boolean {
        return try {
            val token:String = token!!
            val username:String = username!!
            true
        } catch (e:Exception) {
            false
        }
    }
}