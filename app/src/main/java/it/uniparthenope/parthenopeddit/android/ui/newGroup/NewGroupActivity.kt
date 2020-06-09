package it.uniparthenope.parthenopeddit.android.ui.newGroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.model.Course
import it.uniparthenope.parthenopeddit.model.Group


class NewGroupActivity : BasicActivity() {

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

            GroupsRequests(this, app.auth).createGroup( group_name_edittext.text.toString(), listOf(),
            {
                val intent = Intent(this, GroupActivity::class.java)
                intent.putExtra("id_group", it.id)
                startActivity(intent)
                finish()
            },{
            },{it: String ->
                    Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}
