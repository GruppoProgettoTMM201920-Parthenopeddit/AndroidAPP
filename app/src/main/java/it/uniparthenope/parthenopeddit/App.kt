package it.uniparthenope.parthenopeddit

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import java.lang.RuntimeException
import java.net.URISyntaxException

class App : Application() {
    var currentActivity: BasicActivity? = null

    val auth: AuthManager = SharedPreferencesAuth.getInstance(this)

    override fun onCreate() {
        super.onCreate()

        /**
         * Fake login
         */
        auth.login( auth.getToken("user1", "123"), "user1" )
    }
}