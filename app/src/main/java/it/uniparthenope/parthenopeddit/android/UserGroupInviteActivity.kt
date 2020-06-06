package it.uniparthenope.parthenopeddit.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.UserGroupInviteAdapter

class UserGroupInviteActivity : AppCompatActivity(), UserGroupInviteAdapter.UserGroupInviteItemClickListeners {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_group_invite)

        var invite_recyclerview = findViewById<RecyclerView>(R.id.invite_recyclerview)
        var adapter = UserGroupInviteAdapter()
        adapter.setItemClickListener(this)
        invite_recyclerview.adapter = adapter
        invite_recyclerview.layoutManager = LinearLayoutManager(this)
        invite_recyclerview.setHasFixedSize(true)

    }

    override fun onInviteClick(group_id: Int?) {
        //show dialog
    }
}
