package it.uniparthenope.parthenopeddit.android.ui.search.courses

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
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.adapters.CourseAdapter
import it.uniparthenope.parthenopeddit.android.adapters.UserCourseAdapter
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager

class SearchCoursesFragment(private var searchQuery: String) : Fragment(), UserCourseAdapter.UserCourseItemClickListeners {

    private lateinit var authManager: AuthManager

    private lateinit var adapter: UserCourseAdapter
    private lateinit var recycler_view: RecyclerView
    private lateinit var no_courses_textview: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_courses, container, false)

        adapter = UserCourseAdapter()
        adapter.setItemClickListener(this)

        recycler_view = root.findViewById(R.id.recycler_view)
        no_courses_textview = root.findViewById(R.id.no_courses_textview)

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
            no_courses_textview.visibility = View.GONE
        } else {
            recycler_view.visibility = View.GONE
            no_courses_textview.visibility = View.VISIBLE
        }
    }

    fun search(searchQuery: String) {
        adapter.setCourseList(listOf())
        CoursesRequests(activity as Context, authManager).searchByName(
            searchQuery,
            {
                if(it.isNotEmpty()){
                    adapter.setCourseList(it)
                    showList(true)
                }
            },{
                showList(false)
            }
        )
    }

    override fun onBoardClick(board_id: Int?) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_course", board_id)
        startActivity(intent)
    }
}

