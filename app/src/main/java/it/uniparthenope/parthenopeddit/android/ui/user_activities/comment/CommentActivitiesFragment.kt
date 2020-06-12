package it.uniparthenope.parthenopeddit.android.ui.messages

//import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
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
import it.uniparthenope.parthenopeddit.android.CommentCommentsActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.android.adapters.CommentRecursiveAdapter
import it.uniparthenope.parthenopeddit.android.adapters.InfiniteScroller
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.util.toGson

class CommentActivitiesFragment(private val user_id: String) : Fragment(), CommentAdapter.CommentItemClickListeners {

    private lateinit var auth : AuthManager

    private lateinit var recycler_view: RecyclerView
    private lateinit var commentsAdapter: CommentAdapter
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
        val root = inflater.inflate(R.layout.fragment_comment_activities, container, false)
        val no_comments_textview = root.findViewById<TextView>(R.id.no_comments_textview)

        auth = (activity as BasicActivity).app.auth

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        commentsAdapter = CommentAdapter()
        commentsAdapter.setItemClickListener(this)
        recycler_view.adapter = commentsAdapter

        layoutManager = LinearLayoutManager(requireContext())
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(pageToLoad: Int, pageSize: Int) {
                UserRequests(requireContext(), auth).getUserPublishedComments(
                    page = 1,
                    perPage = per_page,
                    user_id = user_id,
                    transactionStartDateTime = transactionStartDateTime,
                    onSuccess = {
                        commentsAdapter.aggiungiCommenti(it)
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

        UserRequests(requireContext(), auth).getUserPublishedComments(
            page = 1,
            perPage = per_page,
            user_id = user_id,
            onSuccess = {
                if(it.isNotEmpty()) {
                    commentsAdapter.aggiungiCommenti(it)
                    transactionStartDateTime = it[0].timestamp
                    recycler_view.addOnScrollListener(infiniteScroller)

                    no_comments_textview.visibility = View.GONE
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


    override fun onClickComments(id_Commento: Int, comment: Comment) {
        val intent = Intent(requireContext(), CommentCommentsActivity::class.java)
        intent.putExtra("idComment", id_Commento)
        intent.putExtra("comment", comment.toGson())
        startActivity(intent)
    }

    override fun onClickLike(id_Commento: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            id_Commento,
            {
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

    override fun onClickDislike(id_Commento: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).dislikePost(
            id_Commento,
            {
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

    override fun onUserClick(id_user: String) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", id_user)
        startActivity(intent)
    }
}
