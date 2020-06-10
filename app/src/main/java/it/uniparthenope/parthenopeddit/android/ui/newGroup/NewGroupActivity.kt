package it.uniparthenope.parthenopeddit.android.ui.newGroup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.util.toGson


class NewGroupActivity : LoginRequiredActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo gruppo"

        val create_button = findViewById<Button>(R.id.create_button)
        val group_name_edittext = findViewById<EditText>(R.id.group_name_edittext)
        val user_group : ArrayList<String> = ArrayList()

        create_button.setOnClickListener {
            GroupsRequests(this, app.auth).createGroup( group_name_edittext.text.toString(), listOf(),
            {
                val intent = Intent(this, GroupActivity::class.java)
                intent.putExtra("id_group", it.id)
                intent.putExtra("group", it.toGson())
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
