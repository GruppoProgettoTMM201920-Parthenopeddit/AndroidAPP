package it.uniparthenope.parthenopeddit

import android.annotation.SuppressLint
import android.os.Bundle
import it.uniparthenope.parthenopeddit.android.LoginActivity

@SuppressLint("Registered")
open class LoginRequiredActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!app.auth.isUserLogged()) {
            goToActivity(LoginActivity::class.java)
        }
    }
}