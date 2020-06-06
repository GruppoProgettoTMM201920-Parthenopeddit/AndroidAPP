package it.uniparthenope.parthenopeddit.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.UserActivityAdapter
import it.uniparthenope.parthenopeddit.android.adapters.UserGroupInviteAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.GroupMember
import kotlinx.android.synthetic.main.activity_user_group_invite.*
import java.util.*
import kotlin.collections.ArrayList

class UserGroupInviteActivity : BasicActivity(), UserGroupInviteAdapter.UserGroupInviteItemClickListeners {

    private lateinit var invite_recyclerview: RecyclerView
    private lateinit var adapter: UserGroupInviteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_group_invite)
        supportActionBar!!.title = "Inviti ai gruppi"

        invite_recyclerview = findViewById<RecyclerView>(R.id.invite_recyclerview)
        adapter = UserGroupInviteAdapter()

        adapter.setItemClickListener(this)
        invite_recyclerview.adapter = adapter
        invite_recyclerview.layoutManager = LinearLayoutManager(this)
        invite_recyclerview.setHasFixedSize(true)


        MockApiData().getUserInvitesToGroup("user5") { userGroupInvite: ArrayList<GroupInvite>?, error: String? ->
            userGroupInvite!!
            if(userGroupInvite.isEmpty()){
                invite_recyclerview.visibility = View.GONE
                no_invites_textview.visibility = View.VISIBLE
            } else{
                no_invites_textview.visibility = View.GONE
                invite_recyclerview.visibility = View.VISIBLE
                adapter.addInvite(userGroupInvite)
            }
        }
    }


    //TODO: change arguments to GroupInvite
    override fun onInviteClick(groupInvite: GroupInvite) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vuoi entrare nel gruppo?")
        builder.setMessage("Sei stato invitato nel gruppo ${groupInvite.group?.name}. Cosa preferisci fare?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("ENTRA") { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()

            //TODO: User joins in group through API
            //MockApiData().answerGroupInvite(group_id!!, true)


            var u1 = MockDatabase.instance.users_table.filter{ it.id == "user1"}.single()
            var u2 = MockDatabase.instance.users_table.filter{ it.id == "user2"}.single()

            val c: Calendar = Calendar.getInstance()
            val currentDate: String =
                c.get(Calendar.DATE).toString() + "/" + c.get(Calendar.MONTH).toString() + "/" + c.get(
                    Calendar.YEAR).toString()

            var group = MockDatabase.instance.group_table.filter { it.id == groupInvite.group?.id}.single()
            var newGroupMember : GroupMember = GroupMember(groupInvite.invited_id, groupInvite.group!!.id, currentDate, null, false, groupInvite.invited, group)
            groupInvite.group!!.members!!.add(newGroupMember)
            groupInvite.invited?.groups!!.add(groupInvite.group!!)

            groupInvite.invited?.group_invites!!.removeIf { it.group_id == groupInvite.group_id }
            groupInvite.group!!.invites!!.removeIf { it.invited_id == u2.id }

            adapter.removeInvite(groupInvite.group_id)
        }

        builder.setNegativeButton("RIFIUTA") { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()

            //TODO: Delete invite through API
            //MockApiData().answerGroupInvite(group_id!!, false)

            var u2 = MockDatabase.instance.users_table.filter{ it.id == "user2"}.single()
            var group = MockDatabase.instance.group_table.filter { it.id == groupInvite.group_id }.single()

            groupInvite.invited?.group_invites!!.removeIf { it.group_id == groupInvite.group_id }
            groupInvite.group!!.invites!!.removeIf { it.invited_id == groupInvite.invited_id }

            adapter.removeInvite(groupInvite.group_id)

        }

        builder.setNeutralButton("CI PENSERÒ") { dialog, which ->
            Toast.makeText(applicationContext,
                "Maybe", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}
