package it.uniparthenope.parthenopeddit.android.ui.user_boards.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.android.adapters.UserGroupAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.model.Post

class GroupUserBoardFragment : Fragment(), UserGroupAdapter.UserGroupItemClickListeners {

    private lateinit var auth: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var groupUserBoardViewModel: GroupUserBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupUserBoardViewModel =
            ViewModelProviders.of(this).get(GroupUserBoardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_userboard_group, container, false)
        val no_group_textview = root.findViewById<TextView>(R.id.no_groups_textview)
        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val userGroupAdapter = UserGroupAdapter()
        userGroupAdapter.setItemClickListener(this)
        recycler_view.adapter = userGroupAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (activity as BasicActivity).app.auth



        GroupsRequests(requireContext(), auth).getUserGroups({it: ArrayList<GroupMember> ->
            no_group_textview.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            userGroupAdapter.addGroup(it)
            }, { it: String ->
            Toast.makeText(requireContext(), "Errore ${it}", Toast.LENGTH_LONG).show()

        }
        )

        return root
    }

    override fun onBoardClick(id_course: Int?) {
        if(id_course==1){
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(requireContext(), GroupActivity::class.java)
            intent.putExtra("id_group", id_course)
            startActivity(intent)
        }
    }
}