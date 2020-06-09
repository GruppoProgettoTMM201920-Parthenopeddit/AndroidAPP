package it.uniparthenope.parthenopeddit.android.ui.search.posts

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
import it.uniparthenope.parthenopeddit.android.*
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.newGroup.NewGroupActivity
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.android.ui.user_activities.post.PostActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson

class SearchPostsFragment : Fragment(), PostAdapter.PostItemClickListeners{

    private lateinit var auth: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var searchPostsViewModel: SearchPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchPostsViewModel =
            ViewModelProviders.of(this).get(SearchPostsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search_posts, container, false)
        val no_posts_textview = root.findViewById<TextView>(R.id.no_posts_textview)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (activity as BasicActivity).app.auth

        val activity = activity as SearchActivity
        val query = activity.searchedQuery

        PostsRequests(requireContext(), auth).searchPost(query,{it: ArrayList<Post> ->
            if(it.isNotEmpty()){
                no_posts_textview.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE
                postAdapter.aggiungiPost(it)
            }
        },{it: String ->
            Toast.makeText(requireContext(), "Errore ${it}", Toast.LENGTH_LONG).show()
        })

        return root
    }


    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
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
        if (board_id == null || board_id == 0) {
            (activity as BasicActivity).goToActivity(HomeActivity::class.java) //HOME
        } else {
            when (board?.type) {
                "course" -> {
                    val intent = Intent(requireContext(), CourseActivity::class.java)  //CORSO
                    intent.putExtra("id_group", board_id)
                    startActivity(intent)
                }
                "group" -> {
                    val intent = Intent(requireContext(), GroupActivity::class.java)
                    intent.putExtra("id_group", board_id)
                    startActivity(intent)
                }
                else -> {  }
            }
        }
    }

}