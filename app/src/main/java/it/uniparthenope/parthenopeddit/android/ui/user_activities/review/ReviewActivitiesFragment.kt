package it.uniparthenope.parthenopeddit.android.ui.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.CourseReviewAdapter
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.post.PostActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.auth.Auth
import kotlinx.android.synthetic.main.fragment_messages.*

class ReviewActivitiesFragment : Fragment(), CourseReviewAdapter.CourseReviewItemClickListeners {

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

        val reviewAdapter = CourseReviewAdapter()
        reviewAdapter.setItemClickListener(this)
        recycler_view.adapter = reviewAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        MockApiData().getAllReview( Auth().token ) { reviewItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                reviewItemList!!

                reviewAdapter.aggiungiReview( reviewItemList.filter{ it.author.id == "user1"} )
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
}
