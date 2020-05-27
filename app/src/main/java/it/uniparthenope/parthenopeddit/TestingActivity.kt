package it.uniparthenope.parthenopeddit

import android.os.Bundle
import it.uniparthenope.parthenopeddit.api.requests.AuthRequests
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import kotlinx.android.synthetic.main.activity_testing.*

class TestingActivity : BasicActivity() {

    private lateinit var auth: SharedPreferencesAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)

        auth = SharedPreferencesAuth(this)
        auth.setEventListener({ username: String ->
            logged_user.text = username
        }, {
            logged_user.text = "no user logged"
        })

        if(auth.isUserLogged()) {
            logged_user.text = auth.username
        }

        login.setOnClickListener {
            val token: String = auth.getToken(username.text.toString(), password.text.toString())
            AuthRequests(this, auth).login(token, {
                output.text = "User ${it.id} logged in"
            }, {
                output.text = "User ${it.id} logged in for the first time"
            }, {
                output.text = it
            })
        }

        next_test.setOnClickListener {
            goToActivity(TestingActivity2::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.setEventListener(null, null)
    }
}
