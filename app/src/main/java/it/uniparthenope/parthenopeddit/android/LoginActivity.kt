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
import it.uniparthenope.parthenopeddit.android.PreferenceHelper.customPreference
import it.uniparthenope.parthenopeddit.android.PreferenceHelper.logged
import it.uniparthenope.parthenopeddit.android.PreferenceHelper.password
import it.uniparthenope.parthenopeddit.android.PreferenceHelper.userId
import kotlinx.android.synthetic.main.activity_login.*


private val sharedPrefFile = "kotlinsharedpreference"

class LoginActivity : BasicActivity(){

    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var username: String? = null
        var password: String? = null

        val prefs = customPreference(this, CUSTOM_PREF_NAME)
        username_edittext.setText(prefs.userId)
        password_edittext.setText(prefs.password)
        user_logged_checkbox.isChecked = prefs.logged

        login_button.setOnClickListener {

            if(username_edittext.text.isEmpty()){
                empty_username_textview.visibility = View.VISIBLE
            }
            else if(password_edittext.text.isEmpty()){
                empty_password_textview.visibility = View.VISIBLE
            } else{
                //TODO: auth with api

                //IF AUTH IS OK
                if(user_logged_checkbox.isChecked){
                    username = username_edittext.text.toString()
                    password = password_edittext.text.toString()

                    prefs.password = password
                    prefs.userId = username
                    prefs.logged = true
                } else{
                    username_edittext.text.clear()
                    password_edittext.text.clear()

                    prefs.password = ""
                    prefs.userId = ""
                    prefs.logged = false
                }


                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }


        }

        privacy_button.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }
    }
}

object PreferenceHelper {

    val USERNAME = "USERNAME"
    val USER_PASSWORD = "PASSWORD"
    val LOGGED = "LOGGED"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.userId
        get() = getString(USERNAME, "")
        set(value) {
            editMe {
                it.putString(USERNAME, value)
            }
        }

    var SharedPreferences.password
        get() = getString(USER_PASSWORD, "")
        set(value) {
            editMe {
                it.putString(USER_PASSWORD, value)
            }
        }

    var SharedPreferences.logged
        get() = getBoolean(LOGGED, false)
        set(value) {
            editMe {
                it.putBoolean(LOGGED, value)
            }
        }

}


