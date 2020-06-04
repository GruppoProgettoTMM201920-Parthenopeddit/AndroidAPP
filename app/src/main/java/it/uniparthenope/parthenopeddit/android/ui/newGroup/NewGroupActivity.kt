package it.uniparthenope.parthenopeddit.android.ui.newGroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.auth.Auth


class NewGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo gruppo"

        val create_button = findViewById<Button>(R.id.create_button)
        val group_name_edittext = findViewById<EditText>(R.id.group_name_edittext)
        val user_group : ArrayList<String> = ArrayList()

        create_button.setOnClickListener {
            //TODO: Add group through APi

            user_group.addAll(listOf("user1","user2"))      //TEST
            MockApiData().createGroup(group_name_edittext.text.toString(), user_group) { groupId: Int, error: String? ->
                val intent = Intent(this, GroupActivity::class.java)
                intent.putExtra("id_group", groupId)
                startActivity(intent)
            }

        }
    }
}
