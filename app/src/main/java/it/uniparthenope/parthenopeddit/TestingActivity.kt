package it.uniparthenope.parthenopeddit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import it.uniparthenope.parthenopeddit.api.requests.AuthRequests
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import it.uniparthenope.parthenopeddit.util.TAG
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
                onLogin()
            }, {
                output.text = "User ${it.id} logged in for the first time"
                onLogin()
            }, {
                output.text = it
            })
        }

        next_test.setOnClickListener {
            goToActivity(TestingActivity2::class.java)
        }


    }

    fun onLogin() {
        //TESTING FIREBASE
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                //TODO HANDLE FAILURE
                AuthRequests(this, auth).registerDeviceToken(token!!, {}, {}, {})

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.setEventListener(null, null)
    }
}
