package it.uniparthenope.parthenopeddit.android.ui.messages

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
import it.uniparthenope.parthenopeddit.android.UserActivity
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Review

class ReviewActivitiesFragment : Fragment(), ReviewAdapter.CourseReviewItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var reviewViewModel: ReviewActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewViewModel =
            ViewModelProviders.of(this).get(ReviewActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_review_activities, container, false)
        val no_reviews_textview = root.findViewById<TextView>(R.id.no_reviews_textview)

        recycler_view = root.findViewById(R.id.recycler_view3) as RecyclerView

        val reviewAdapter = ReviewAdapter()
        reviewAdapter.setItemClickListener(this)
        recycler_view.adapter = reviewAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth
        val user_id = (activity as UserActivity).user_id

        UserRequests(requireContext(), authManager).getUserPublishedReviews( user_id, 1, 20, {it: ArrayList<Review> ->
            if(it.isNotEmpty()){
                no_reviews_textview.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
                reviewAdapter.aggiungiReview(it)
            }
        },{it: String ->
        })

        return root
    }

    override fun onClickLike(id_review: Int, upvote_textview: TextView, downvote_textview: TextView) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(
        id_review: Int,
        upvote_textview: TextView,
        downvote_textview: TextView
    ) {
        //TODO("Not yet implemented")
    }

    override fun onReviewClick(id_course: Int) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_course", id_course)
        startActivity(intent)
    }

    override fun onClickComments(id_review: Int) {
        TODO("Not yet implemented")
    }
}
