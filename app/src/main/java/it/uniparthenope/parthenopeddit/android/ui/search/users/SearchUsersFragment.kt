package it.uniparthenope.parthenopeddit.android.ui.search.users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.UserListAdapter
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User


class SearchUsersFragment(private var searchQuery: String) : Fragment(), UserListAdapter.UserListItemClickListeners {

    private lateinit var authManager: AuthManager

    private lateinit var adapter: UserListAdapter
    private lateinit var recycler_view: RecyclerView
    private lateinit var no_users_textview: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_users, container, false)

        adapter = UserListAdapter()
        adapter.setItemClickListener(this)

        recycler_view = root.findViewById(R.id.recycler_view)
        no_users_textview = root.findViewById(R.id.no_users_textview)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        search(searchQuery)

        return root
    }

    private fun showList( visible: Boolean ) {
        if( visible ) {
            recycler_view.visibility = View.VISIBLE
            no_users_textview.visibility = View.GONE
        } else {
            recycler_view.visibility = View.GONE
            no_users_textview.visibility = View.VISIBLE
        }
    }

    fun search(searchQuery: String) {
        adapter.setUserList(listOf())
        UserRequests(activity as Context, authManager).searchUser(
            searchQuery,
            {
                if(it.isNotEmpty()){
                    adapter.setUserList(it)
                    showList(true)
                }
            },{
                showList(false)
            }
        )
    }

    override fun onUserClick(user: User) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", user.id)
        startActivity(intent)
    }
}