package it.uniparthenope.parthenopeddit

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import it.uniparthenope.parthenopeddit.api.requests.AuthRequests
import it.uniparthenope.parthenopeddit.auth.AbstractAuthManager
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.auth.SharedPreferencesAuth
import it.uniparthenope.parthenopeddit.util.TAG

/*
MEME INFORMATICO 1:

Ragazza: Vieni a casa da me?

Dijkstra: Ma ci sono così tante strade, non saprei quale sarebbe più veloce

Ragazza: i miei non sono a casa

Dijkstra : https://it.wikipedia.org/wiki/Algoritmo_di_Dijkstra
 */


class App : Application(), AbstractAuthManager.logEventListener {
    var currentActivity: BasicActivity? = null

    val auth: AuthManager = SharedPreferencesAuth.getInstance(this, this)

    override fun onLogin(username: String) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)

                    //TODO TRY AGAIN NEXT TIME
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                //TODO HANDLE FAILURE
                AuthRequests(this, auth).registerDeviceToken(token!!, {}, {}, {})
            }
        )
    }

    override fun onLogout() {

    }
}