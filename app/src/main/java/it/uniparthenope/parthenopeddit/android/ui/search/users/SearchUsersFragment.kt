package it.uniparthenope.parthenopeddit.android.ui.search.users

import android.content.Intent
import android.os.Bundle
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
import it.uniparthenope.parthenopeddit.android.SearchActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.UserListAdapter
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User

class SearchUsersFragment : Fragment(), UserListAdapter.UserListItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var searchUsersViewModel: SearchUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchUsersViewModel =
            ViewModelProviders.of(this).get(SearchUsersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search_courses, container, false)
        val no_users_textview = root.findViewById<TextView>(R.id.no_courses_textview)
        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val userAdapter = UserListAdapter()
        userAdapter.setItemClickListener(this)
        recycler_view.adapter = userAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        no_users_textview.visibility = View.VISIBLE

        authManager = (activity as BasicActivity).app.auth

        val activity = activity as SearchActivity
        val query = activity.searchedQuery

        UserRequests(requireContext(), authManager).searchUser(query,{ it: ArrayList<User> ->

            if(it.isNotEmpty()){
                no_users_textview.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
                userAdapter.addUser(it)
            }
        },{ it: String ->
            Toast.makeText(requireContext(), "Errore ${it}", Toast.LENGTH_LONG).show()
        }
        )


        return root
    }

    override fun onUserClick(user: User) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", user.id)
        startActivity(intent)
    }

}