package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CourseActivity
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

        recycler_view = root.findViewById(R.id.recycler_view3) as RecyclerView

        val reviewAdapter = ReviewAdapter()
        reviewAdapter.setItemClickListener(this)
        recycler_view.adapter = reviewAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        UserRequests(requireContext(), authManager).getUserPublishedReviews( authManager.username!!, 1, 20, {it: ArrayList<Review> ->
            reviewAdapter.aggiungiReview(it)
        },{it: String ->
            Toast.makeText(requireContext(),"Errore : $it", Toast.LENGTH_LONG).show()
        })

        return root
    }

    override fun onClickLike(id_post: Int) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(id_post: Int) {
        //TODO("Not yet implemented")
    }

    override fun onReviewClick(id_course: Int) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_course", id_course)
        startActivity(intent)
    }
}
