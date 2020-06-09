package it.uniparthenope.parthenopeddit.android.ui.course.post

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
import it.uniparthenope.parthenopeddit.android.PostCommentsActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.post.PostActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post

class CoursePostFragment(courseID: Int) : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var auth: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var postViewModel: PostActivitiesViewModel
    var courseId = courseID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel =
            ViewModelProviders.of(this).get(PostActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_course_post, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (activity as BasicActivity).app.auth

        MockApiData().getAllPost( auth.token!! ) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiungiPost( postItemList.filter{ it.posted_to_board_id == courseId} )
            }
        }

        return root
    }

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            1, {
                upvote_textview.text = (upvote_textview.text.toString().toInt() + 1).toString()
            }, {
                upvote_textview.text = (upvote_textview.text.toString().toInt() - 1).toString()
            }, {
                downvote_textview.text = (downvote_textview.text.toString().toInt() - 1).toString()
                upvote_textview.text = (upvote_textview.text.toString().toInt() + 1).toString()
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onClickDislike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            1, {
                downvote_textview.text = (downvote_textview.text.toString().toInt() + 1).toString()
            }, {
                downvote_textview.text = (downvote_textview.text.toString().toInt() - 1).toString()
            }, {
                upvote_textview.text = (upvote_textview.text.toString().toInt() - 1).toString()
                downvote_textview.text = (downvote_textview.text.toString().toInt() + 1).toString()
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onClickComments(id_post: Int, post: Post) {
        //TODO("Not yet implemented")
    }

    override fun onBoardClick(board_id: Int?, board: Board?) {
        TODO("Not yet implemented")
    }

    override fun onPostClick(id_post: Int, post: Post) {
        val intent = Intent(requireContext(), PostCommentsActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }

    override fun onUserClick(id_user: String) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", id_user)
        startActivity(intent)
    }
}
