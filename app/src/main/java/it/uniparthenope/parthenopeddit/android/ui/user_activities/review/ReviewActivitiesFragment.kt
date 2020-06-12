package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.ReviewCommentsActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.InfiniteScroller
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.api.requests.ReviewsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Course
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Review
import it.uniparthenope.parthenopeddit.util.toGson

class ReviewActivitiesFragment(private val user_id: String) : Fragment(), ReviewAdapter.ReviewItemClickListeners {

    private lateinit var auth : AuthManager

    private lateinit var recycler_view: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var infiniteScroller: InfiniteScroller
    private lateinit var updater: InfiniteScroller.Updater

    private lateinit var transactionStartDateTime: String

    private val per_page = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_review_activities, container, false)
        val no_reviews_textview = root.findViewById<TextView>(R.id.no_reviews_textview)

        auth = (activity as BasicActivity).app.auth

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        reviewAdapter = ReviewAdapter()
        reviewAdapter.setItemClickListener(this)
        recycler_view.adapter = reviewAdapter

        layoutManager = LinearLayoutManager(requireContext())
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(pageToLoad: Int, pageSize: Int) {
                UserRequests(requireContext(), auth).getUserPublishedReviews(
                    page = pageToLoad,
                    perPage = pageSize,
                    user_id = user_id,
                    transactionStartDateTime = transactionStartDateTime,
                    onSuccess = {
                        reviewAdapter.aggiungiReview(it)
                    },
                    onEndOfContent = {
                        infiniteScroller.theresMore = false
                    },
                    onFail = {
                        Toast.makeText(requireContext(), "ERROR IN FETCHING NEW REVIEWS", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        infiniteScroller = InfiniteScroller(
            layoutManager, updater, per_page
        )

        UserRequests(requireContext(), auth).getUserPublishedReviews(
            page = 1,
            perPage = per_page,
            user_id = user_id,
            onSuccess = {
                if(it.isNotEmpty()) {
                    reviewAdapter.aggiungiReview(it)
                    transactionStartDateTime = it[0].timestamp
                    recycler_view.addOnScrollListener(infiniteScroller)

                    no_reviews_textview.visibility = View.GONE
                    recycler_view.visibility = View.VISIBLE
                }
            },
            onEndOfContent = {
                //nothing
            },
            onFail = {
                Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
            }
        )

        return root
    }

    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
    }

    override fun onClickLike(id_review: Int, upvote_textview: TextView, downvote_textview: TextView) {
        ReviewsRequests(requireContext(), auth).likeReview(
            id_review, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onClickDislike(
        id_review: Int,
        upvote_textview: TextView,
        downvote_textview: TextView
    ) {
        ReviewsRequests(requireContext(), auth).dislikeReview(
            id_review, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                updateLike(upvote_textview, downvote_textview, it)
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onClickCourse(id_course: Int, course: Course) {
        val intent = Intent(requireContext(), CourseActivity::class.java)
        intent.putExtra("id_course", id_course)
        intent.putExtra("course", course.toGson())
        startActivity(intent)
    }

    override fun onReviewClick(id_review: Int, review: Review) {
        val intent = Intent(requireContext(), ReviewCommentsActivity::class.java)
        intent.putExtra("idReview", id_review)
        intent.putExtra("review", review.toGson())
        startActivity(intent)
    }

    override fun onClickComments(id_review: Int, review: Review) {
        val intent = Intent(requireContext(), ReviewCommentsActivity::class.java)
        intent.putExtra("idReview", id_review)
        intent.putExtra("review", review.toGson())
        startActivity(intent)
    }

    override fun onUserClick(id_user: String) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", id_user)
        startActivity(intent)
    }
}
