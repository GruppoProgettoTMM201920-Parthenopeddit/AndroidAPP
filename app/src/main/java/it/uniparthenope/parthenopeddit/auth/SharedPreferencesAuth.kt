package it.uniparthenope.parthenopeddit.auth

import android.content.Context
import android.util.Base64
import it.uniparthenope.parthenopeddit.R

class SharedPreferencesAuth private constructor (ctx: Context, listener: logEventListener) : AbstractAuthManager(ctx, listener) {

    companion object {
        @Volatile private var instance: SharedPreferencesAuth? = null

        fun getInstance(ctx: Context, listener: logEventListener): SharedPreferencesAuth {
            val checkInstance = instance
            if (checkInstance != null) {
                return checkInstance
            }

            return synchronized(this) {
                val checkInstanceAgain = instance
                if (checkInstanceAgain != null) {
                    checkInstanceAgain
                } else {
                    val created = SharedPreferencesAuth(ctx, listener)
                    instance = created
                    created
                }
            }
        }

        private const val TOKEN = "token"
        private const val USERNAME = "username"
        private const val AUTOLOGIN = "autologin"
        private const val PRIVATE_KEY = "autologin"
        private const val PUBLIC_KEY = "autologin"
    }

    private fun getValue(key: String): String? {
        val sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    private fun setValue(key: String, value: String?) {
        val sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override var username:String?
        get() = getValue(USERNAME)
        set(newUsername) = setValue(USERNAME, newUsername)

    override var token:String?
        get() = getValue(TOKEN)
        set(newToken) = setValue(TOKEN, newToken)

    override var autoLogin: Boolean?
        get() = getValue(AUTOLOGIN)?.toBoolean()
        set(autoLogin) = setValue(AUTOLOGIN, autoLogin.toString())

    override var privateKey: String?
        get() = getValue(PRIVATE_KEY)
        set(key) = setValue(PRIVATE_KEY, key)

    override var publicKey: String?
        get() = getValue(PUBLIC_KEY)
        set(key) = setValue(PUBLIC_KEY, key)

    override fun login(token:String, username:String, autoLogin: Boolean) {
        this.username = username
        this.token = token
        this.autoLogin = autoLogin

        listener.onLogin(username)
    }

    override fun logout() {
        this.username = null
        this.token = null
        this.autoLogin = null

        listener.onLogout()
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