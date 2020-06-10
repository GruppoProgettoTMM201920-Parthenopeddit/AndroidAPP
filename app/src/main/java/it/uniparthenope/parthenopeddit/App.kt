package it.uniparthenope.parthenopeddit

import android.app.Application
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth

class App : Application() {
    var currentActivity: BasicActivity? = null

    val auth: AuthManager = SharedPreferencesAuth.getInstance(this)
}