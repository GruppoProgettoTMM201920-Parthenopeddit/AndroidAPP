package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.UserGroupInviteAdapter
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.GroupMember
import kotlinx.android.synthetic.main.activity_user_group_invite.*

class UserGroupInviteActivity : LoginRequiredActivity(), UserGroupInviteAdapter.UserGroupInviteItemClickListeners {

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

        GroupsRequests(this, app.auth).getUserInvitesToGroup({it: ArrayList<GroupInvite> ->
            it
            if(it.isEmpty()){
                invite_recyclerview.visibility = View.GONE
                no_invites_textview.visibility = View.VISIBLE
            } else{
                no_invites_textview.visibility = View.GONE
                invite_recyclerview.visibility = View.VISIBLE
                adapter.addInvite(it)
            }
        },{
            Toast.makeText(this,"Errore : ${it}", Toast.LENGTH_LONG).show()
        })
    }

    override fun onInviteClick(groupInvite: GroupInvite) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vuoi entrare nel gruppo?")
        builder.setMessage("Sei stato invitato nel gruppo ${groupInvite.group?.name}. Cosa preferisci fare?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("ENTRA") { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()

            GroupsRequests(this, app.auth).answerGroupInvite(groupInvite.group_id, true, {
            }, {it: GroupMember ->
                Toast.makeText(this, "Sei entrato nel gruppo ${groupInvite.group?.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, GroupActivity::class.java)
                intent.putExtra("id_group", groupInvite.group_id)
                startActivity(intent)
                finish()
            },{it: String ->
                Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
            })
        }

        builder.setNegativeButton("RIFIUTA") { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()

            GroupsRequests(this, app.auth).answerGroupInvite(groupInvite.group_id, false, {
                Toast.makeText(this, "Hai rifiutato l'invito al gruppo ${groupInvite.group?.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserGroupInviteActivity::class.java)
                startActivity(intent)
                finish()
            }, {it: GroupMember ->
            },{it: String ->
                Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
            })
        }

        builder.setNeutralButton("CI PENSERÒ") { dialog, which ->
        }
        builder.show()
    }
}
