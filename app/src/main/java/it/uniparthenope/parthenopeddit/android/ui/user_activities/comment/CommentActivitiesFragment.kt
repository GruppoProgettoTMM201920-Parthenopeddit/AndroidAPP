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
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.android.PostCommentsActivity
//import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.comment.CommentActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson

class CommentActivitiesFragment : Fragment(), CommentAdapter.CommentItemClickListeners {

    private lateinit var auth: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var commentViewModel: CommentActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        commentViewModel =
            ViewModelProviders.of(this).get(CommentActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_comment_activities, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        var commentItemsList: ArrayList<Comment> = arrayListOf<Comment>()
        val commentAdapter = CommentAdapter(requireContext(), commentItemsList, this)
        commentAdapter.setItemClickListener(this)
        recycler_view.adapter = commentAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (activity as BasicActivity).app.auth

        UserRequests(requireContext(), auth).getUserPublishedComments( auth.username!!, 1, 20, { it: ArrayList<Comment> ->
            commentAdapter.aggiungiCommenti(it)
        },{it: String ->
            Toast.makeText(requireContext(),"Errore : $it", Toast.LENGTH_LONG).show()
        })

        return root
    }


    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
    }


    override fun onClickComments(id_Commento: Int, comment: Comment) {
        TODO("Not yet implemented")
    }

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            1, {
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

    override fun onClickDislike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).dislikePost(
            1, {
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
}
