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
import it.uniparthenope.parthenopeddit.android.PostCommentsActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.post.PostActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post

class PostActivitiesFragment : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var postViewModel: PostActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel =
            ViewModelProviders.of(this).get(PostActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_post_activities, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        UserRequests(requireContext(), authManager).getUserPublishedPosts( authManager.username!!, 1, 20, { it: ArrayList<Post> ->
            postAdapter.aggiungiPost(it)
        },{it: String ->
            Toast.makeText(requireContext(),"Errore : $it", Toast.LENGTH_LONG).show()
        })

        return root
    }

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        //TODO("Not yet implemented")
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
}
