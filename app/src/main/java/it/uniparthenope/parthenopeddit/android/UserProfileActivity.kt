package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import it.uniparthenope.parthenopeddit.R

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val user_activity_button = findViewById<Button>(R.id.user_activity_button)
        val user_chat_button = findViewById<Button>(R.id.user_chat_button)

        val extras = intent.extras
        val id_user = extras?.getString("id_user")

        //TODO: retrieve user from API

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