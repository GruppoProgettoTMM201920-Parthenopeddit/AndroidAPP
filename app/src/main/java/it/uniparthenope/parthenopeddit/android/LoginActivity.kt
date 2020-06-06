package it.uniparthenope.parthenopeddit.android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceManager
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.activity_login.*


private val sharedPrefFile = "kotlinsharedpreference"

class LoginActivity : BasicActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()

        if(app.auth.autoLogin == true) {
            //IF AUTH IS OK
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
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
                //IF AUTH IS OK
                app.auth.login( token, username, user_logged_checkbox.isChecked )

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        privacy_button.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }
    }
}

