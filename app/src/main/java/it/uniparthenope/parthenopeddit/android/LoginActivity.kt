package it.uniparthenope.parthenopeddit.android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.preference.PreferenceManager
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.api.requests.AuthRequests
import it.uniparthenope.parthenopeddit.util.animateView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.progress_overlay.*


private val sharedPrefFile = "kotlinsharedpreference"

class LoginActivity : BasicActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()

        if(app.auth.autoLogin == true) {
            wrong_credentials_textview.visibility = View.GONE

            showProgressOverlay()

            AuthRequests(this, app.auth).login(
                app.auth.token!!,
                {
                    goToActivity(HomeActivity::class.java)
                    finish()
                }, {
                    goToActivity(HomeActivity::class.java)
                    finish()
                }, {
                    hideProgressOverlay()
                    app.auth.logout()

                    wrong_credentials_textview.visibility = View.VISIBLE
                }
            )
        }

        username_edittext.setText(app.auth.username?:"")
        user_logged_checkbox.isChecked = app.auth.autoLogin?:false

        login_button.setOnClickListener {

            if(username_edittext.text.isEmpty()){
                empty_username_textview.visibility = View.VISIBLE
            }
            else if(password_edittext.text.isEmpty()){
                empty_password_textview.visibility = View.VISIBLE
            } else{
                val username: String = username_edittext.text.toString()
                val password: String = password_edittext.text.toString()

                val token = app.auth.getToken(username, password)

                //TODO auth with api

                showProgressOverlay()

                AuthRequests(this, app.auth).login(
                    token,
                    {
                        //login normale
                        wrong_credentials_textview.visibility = View.GONE

                        app.auth.login( token, username, user_logged_checkbox.isChecked )
                        goToActivity(HomeActivity::class.java)
                        finish()
                    }, {
                        //primo login dell utente
                        wrong_credentials_textview.visibility = View.GONE

                        app.auth.login( token, username, user_logged_checkbox.isChecked )
                        goToActivity(HomeActivity::class.java)
                        finish()
                    }, {
                        //fallito login
                        wrong_credentials_textview.visibility = View.VISIBLE
                        hideProgressOverlay()
                    }
                )
            }
        }

        privacy_button.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }
    }



    fun showProgressOverlay() {
        val progress_overlay = findViewById<FrameLayout>(R.id.progress_overlay)
        animateView(progress_overlay, View.VISIBLE, 0.4f, 200)
    }

    fun hideProgressOverlay() {
        val progress_overlay = findViewById<FrameLayout>(R.id.progress_overlay)
        animateView(progress_overlay, View.GONE, 0f, 200)
    }
}

