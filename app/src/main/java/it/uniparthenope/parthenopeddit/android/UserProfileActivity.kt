package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.model.User

class UserProfileActivity : BasicActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val user_activity_button = findViewById<Button>(R.id.user_activity_button)
        val user_chat_button = findViewById<Button>(R.id.user_chat_button)
        val username_textview = findViewById<TextView>(R.id.username_textview)
        val username_shown_textview = findViewById<TextView>(R.id.username_shown_textview)
        val registered_on_textview = findViewById<TextView>(R.id.registered_on_textview)

        val extras = intent.extras
        val id_user = extras?.getString("id_user")


        UserRequests(this, app.auth).getUserByID(id_user!!,{it: User ->
            user = it
            username_textview.text = user.id
            username_shown_textview.text = user.display_name?:user.id
            registered_on_textview.text = user.registered_on
        },{
            Toast.makeText(this,"Errore : $it", Toast.LENGTH_LONG).show()
        })


        user_activity_button.setOnClickListener {
            val intent = Intent(this, UserActivity()::class.java)
            intent.putExtra("user_id", user.id)
            startActivity(intent)

        }

        user_chat_button.setOnClickListener {
            val intent = Intent(this, ChatActivity()::class.java)
            intent.putExtra("user", user.toJSON())
            startActivity(intent)
        }
    }
}