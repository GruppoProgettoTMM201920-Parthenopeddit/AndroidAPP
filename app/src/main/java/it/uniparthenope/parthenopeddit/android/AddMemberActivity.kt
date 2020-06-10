package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.UserListAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.activity_add_member.*
import java.util.*

class AddMemberActivity : BasicActivity(), UserListAdapter.UserListItemClickListeners {

    private var id_group:Int = 0
    private var name_group: String? = ""

    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)

        val extras = intent.extras
        id_group = extras?.getInt("id_group")?:0
        if(id_group == 0) finish()
        name_group = extras?.getString("name_group")?:""
        if(name_group == "") finish()

        group_name_textview.text = name_group

        searchBar.setSpeechMode(false)
        searchBar.setCardViewElevation(10)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                if(text.isNotBlank()){
                    GroupsRequests(this@AddMemberActivity, app.auth).searchInvitableUser(
                        id_group,
                        text.toString(),
                        {
                            adapter.setUserList(it)
                        }, {}
                    )
                }
            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_NAVIGATION -> {
                    }
                    MaterialSearchBar.BUTTON_SPEECH -> {
                    }
                    MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
                }
            }
        })

        adapter = UserListAdapter()
        adapter.setItemClickListener(this)
        user_list_recyclerview.adapter = adapter
        user_list_recyclerview.layoutManager = LinearLayoutManager(this)
        user_list_recyclerview.setHasFixedSize(true)
    }

    override fun onUserClick(user: User) {
        GroupsRequests(this, app.auth).inviteUsersToGroup(
            id_group,
            listOf(user.id),
            {invites: ArrayList<GroupInvite> ->
                if(invites.isNotEmpty()) {
                    if (invites.singleOrNull { invite: GroupInvite ->
                            invite.invited_id == user.id
                        } != null) {
                        adapter.removeUser(user)
                    }
                }
            }, {}
        )
    }
}
