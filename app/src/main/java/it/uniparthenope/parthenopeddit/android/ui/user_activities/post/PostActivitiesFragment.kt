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
import it.uniparthenope.parthenopeddit.App
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CommentActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.post.PostActivitiesViewModel
import it.uniparthenope.parthenopeddit.android.view.CardviewPost
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post

class PostActivitiesFragment : Fragment(), CardviewPost.PostItemClickListeners {

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

        val auth: AuthManager = (requireContext().applicationContext as App).auth

        MockApiData().getAllPost( auth.token!! ) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiungiPost( postItemList.filter{ it.author_id == "user1"} )
            }
        }



        return root
    }

    override fun onClickLike(id_post: Int, upvoteTextView: TextView, downvoteTextView: TextView) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(
        id_post: Int,
        upvoteTextView: TextView,
        downvoteTextView: TextView
    ) {
        //TODO("Not yet implemented")
    }

    override fun onClickComments(post: Post) {
        //TODO("Not yet implemented")
    }

    override fun onBoardClick(board_id: Int?, board: Board?) {
        TODO("Not yet implemented")
    }

    override fun onPostClick(post: Post) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", post.id)
        startActivity(intent)
    }
}
