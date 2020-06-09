package it.uniparthenope.parthenopeddit.android.ui.search.courses

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
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.SearchActivity
import it.uniparthenope.parthenopeddit.android.adapters.UserCourseAdapter
import it.uniparthenope.parthenopeddit.android.ui.search.posts.SearchPostsViewModel
import it.uniparthenope.parthenopeddit.android.ui.user_boards.course.CourseUserBoardFragment
import it.uniparthenope.parthenopeddit.android.ui.user_boards.course.CourseUserBoardViewModel
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Course

class SearchCoursesFragment : Fragment(), UserCourseAdapter.UserCourseItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var searchCoursesViewModel: SearchCoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchCoursesViewModel =
            ViewModelProviders.of(this).get(SearchCoursesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search_courses, container, false)
        val no_courses_textview = root.findViewById<TextView>(R.id.no_courses_textview)
        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val courseAdapter = UserCourseAdapter()
        courseAdapter.setItemClickListener(CourseUserBoardFragment())
        recycler_view.adapter = courseAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        no_courses_textview.visibility = View.VISIBLE

        authManager = (activity as BasicActivity).app.auth

        val activity = activity as SearchActivity
        val query = activity.searchedQuery

        CoursesRequests(requireContext(), authManager).searchByName(query,{ it: ArrayList<Course> ->

            if(it.isNotEmpty()){
                no_courses_textview.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
                courseAdapter.addCourse(it)
            }
        },{ it: String ->
            Toast.makeText(requireContext(), "Errore ${it}", Toast.LENGTH_LONG).show()
        }
        )


        return root
    }

    override fun onBoardClick(id_course: Int?) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_course", id_course)
        startActivity(intent)
    }
}

