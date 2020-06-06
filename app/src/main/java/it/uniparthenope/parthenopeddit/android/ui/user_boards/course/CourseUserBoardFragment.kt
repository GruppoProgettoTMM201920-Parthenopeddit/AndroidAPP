package it.uniparthenope.parthenopeddit.android.ui.user_boards.course

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
import it.uniparthenope.parthenopeddit.android.adapters.UserCourseAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Course

class CourseUserBoardFragment : Fragment(), UserCourseAdapter.UserCourseItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var courseUserBoardViewModel: CourseUserBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        courseUserBoardViewModel =
            ViewModelProviders.of(this).get(CourseUserBoardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_userboard_course, container, false)
        val no_courses_textview = root.findViewById<TextView>(R.id.no_courses_textview)
        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val userCourseAdapter = UserCourseAdapter()
        userCourseAdapter.setItemClickListener(this)
        recycler_view.adapter = userCourseAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        CoursesRequests(requireContext(), authManager).getFollowedCourses({it: ArrayList<Course> ->
            no_courses_textview.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            userCourseAdapter.addCourse(it)
            },{ it: String ->
            Toast.makeText(requireContext(), "Errore ${it}", Toast.LENGTH_LONG).show()
            }
        )


        return root
    }

    override fun onBoardClick(id_course: Int?) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_group", id_course)
        startActivity(intent)
    }
}