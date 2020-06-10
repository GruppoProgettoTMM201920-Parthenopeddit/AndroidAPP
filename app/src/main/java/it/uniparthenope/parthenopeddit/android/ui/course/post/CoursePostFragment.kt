package it.uniparthenope.parthenopeddit.android.ui.course.post

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
import it.uniparthenope.parthenopeddit.android.PostCommentsActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson

class CoursePostFragment(private var courseID: Int) : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var auth : AuthManager
    private lateinit var recycler_view: RecyclerView

    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_course_post, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (activity as BasicActivity).app.auth

        CoursesRequests(requireContext(), auth).getCoursePosts(
            course_id = courseID,
            onSuccess = {
                postAdapter.aggiungiPost(it)
            },
            onFail = {}
        )

        return root
    }

    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
    }

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            id_post, {
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
            id_post, {
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

    override fun onClickComments(id_post: Int, post: Post) {
        val intent = Intent(requireContext(), PostCommentsActivity::class.java)
        intent.putExtra("idPost", id_post)
        intent.putExtra("post", post.toGson())
        startActivity(intent)
    }

    override fun onPostClick(id_post: Int, post: Post) {
        val intent = Intent(requireContext(), PostCommentsActivity::class.java)
        intent.putExtra("idPost", id_post)
        intent.putExtra("post", post.toGson())
        startActivity(intent)
    }

    override fun onUserClick(id_user: String) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", id_user)
        startActivity(intent)
    }

    override fun onBoardClick(board_id: Int?, board: Board?) {
        //nothing
    }
}