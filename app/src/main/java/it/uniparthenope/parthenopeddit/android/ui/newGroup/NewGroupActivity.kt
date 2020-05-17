package it.uniparthenope.parthenopeddit.android.ui.newGroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.HomeActivity


class NewGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo gruppo"

        val create_button = findViewById<Button>(R.id.create_button)
        val group_name_edittext = findViewById<EditText>(R.id.group_name_edittext)
        val user_group_edittext = findViewById<EditText>(R.id.user_group_edittext)

        create_button.setOnClickListener {
            //TODO: Add group through APi

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
