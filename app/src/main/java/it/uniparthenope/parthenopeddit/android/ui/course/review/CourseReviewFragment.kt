package it.uniparthenope.parthenopeddit.android.ui.course.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth

class CourseReviewFragment(courseID: Int) : Fragment(), ReviewAdapter.CourseReviewItemClickListeners{

    private lateinit var recycler_view: RecyclerView
    private lateinit var courseReviewViewModel: CourseReviewViewModel
    var courseId = courseID

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_course_review, container, false)
        //val fab = root.findViewById(R.id.fab) as FloatingActionButton
        recycler_view = root.findViewById(R.id.recycler_view2) as RecyclerView

        val courseReviewAdapter = ReviewAdapter()
        courseReviewAdapter.setItemClickListener(this)
        recycler_view.adapter = courseReviewAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)


        MockApiData().getCourseReviews( Auth().token, courseId ) { reviewItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                reviewItemList!!

                courseReviewAdapter.aggiungiReview( reviewItemList )
            }
        }

        return root

    }

    override fun onClickLike(id_post: Int) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(id_post: Int) {
        //TODO("Not yet implemented")
    }

    override fun onReviewClick(id_course: Int) {
        TODO("Not yet implemented")
    }
}