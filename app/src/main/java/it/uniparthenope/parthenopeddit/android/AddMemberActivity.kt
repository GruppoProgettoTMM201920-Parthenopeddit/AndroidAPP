package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.AddMemberAdapter
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.activity_add_member.*
import java.util.*

class AddMemberActivity : AppCompatActivity(), AddMemberAdapter.UserListItemClickListeners {

    var id_group:Int = 0
    var name_group: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)

        val extras = intent.extras
        id_group = extras?.getInt("id_group")?:0
        name_group = extras?.getString("name_group")?:""

        var user_list_recyclerview = findViewById<RecyclerView>(R.id.user_list_recyclerview)
        group_name_textview.text = name_group

        val addMemberAdapter = AddMemberAdapter()
        addMemberAdapter.setItemClickListener(this)
        user_list_recyclerview.adapter = addMemberAdapter
        user_list_recyclerview.layoutManager = LinearLayoutManager(this)
        user_list_recyclerview.setHasFixedSize(true)

        addMemberAdapter.addUser(MockDatabase.instance.users_table)
    }

    override fun onUserClick(user: User) {
        var group = MockDatabase.instance.group_table.filter { it.id == id_group }.single()

        for(groupuser in group.members!!) {
            Log.d("DEBUG", "group has user with id ${user.id}")
        }

        for(groupuser in group.members!!) {

            Log.d("DEBUG", "checking user with id ${groupuser.user_id}")
            if (user.id == groupuser.user_id) {
                Toast.makeText(
                    this,
                    "L'utente è già presente nel gruppo ${name_group}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("DEBUG", "because user has id ${user.id}")
                Log.d("DEBUG", "and usergroup has id ${groupuser.user_id}")

                val intent = Intent(this, GroupActivity::class.java)
                intent.putExtra("id_group", id_group)
                startActivity(intent)
                return
            }
        }

        val c: Calendar = Calendar.getInstance()
        val currentDate: String =
            c.get(Calendar.DATE).toString() + "/" + c.get(Calendar.MONTH).toString() + "/" + c.get(
                Calendar.YEAR).toString()
        var newGroupMember : GroupMember = GroupMember(user.id, id_group, currentDate, null, false, user, group)
        group.members!!.add(newGroupMember)
        Toast.makeText(this,"Hai aggiunto l'utente ${user.id}", Toast.LENGTH_LONG).show()

        val intent = Intent(this, GroupActivity::class.java)
        intent.putExtra("id_group", id_group)
        startActivity(intent)


    }
}
