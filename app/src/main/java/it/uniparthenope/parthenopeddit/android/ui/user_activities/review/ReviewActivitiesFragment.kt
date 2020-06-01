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
import it.uniparthenope.parthenopeddit.App
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.AuthManager

class ReviewActivitiesFragment : Fragment(), ReviewAdapter.CourseReviewItemClickListeners {

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

        val auth: AuthManager = (requireContext().applicationContext as App).auth

        MockApiData().getAllReview( auth.token!! ) { reviewItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                reviewItemList!!

                reviewAdapter.aggiungiReview( reviewItemList.filter{ it.author?.id == "user1"} )
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
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_group", id_course)
        startActivity(intent)
    }
}
