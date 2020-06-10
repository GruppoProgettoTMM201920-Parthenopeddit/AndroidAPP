package it.uniparthenope.parthenopeddit.android.ui.search.posts

import android.content.Context
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
import it.uniparthenope.parthenopeddit.android.*
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson


class SearchPostsFragment(private var searchQuery: String) : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var authManager: AuthManager

    private lateinit var adapter: PostAdapter
    private lateinit var recycler_view: RecyclerView
    private lateinit var no_posts_textview: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_posts, container, false)

        adapter = PostAdapter()
        adapter.setItemClickListener(this)

        recycler_view = root.findViewById(R.id.recycler_view)
        no_posts_textview = root.findViewById(R.id.no_posts_textview)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        search(searchQuery)

        return root
    }

    private fun showList( visible: Boolean ) {
        if( visible ) {
            recycler_view.visibility = View.VISIBLE
            no_posts_textview.visibility = View.GONE
        } else {
            recycler_view.visibility = View.GONE
            no_posts_textview.visibility = View.VISIBLE
        }
    }

    fun search(searchQuery: String) {
        adapter.setPostList(listOf())
        PostsRequests(activity as Context, authManager).searchPost(
            searchQuery,
            {
                if(it.isNotEmpty()){
                    adapter.setPostList(it)
                    showList(true)
                }
            },{
                showList(false)
            }
        )
    }

    private fun updateLike(upvote_textview: TextView, downvote_textview: TextView, scores: LikeDislikeScore) {
        upvote_textview.text = scores.likes_num.toString()
        downvote_textview.text = scores.dislikes_num.toString()
    }

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), authManager).likePost(
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
        PostsRequests(requireContext(), authManager).dislikePost(
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
        if (board_id == null || board_id == 0) {
            (activity as BasicActivity).goToActivity(HomeActivity::class.java)
        } else {
            when (board?.type) {
                "course" -> {
                    val intent = Intent(requireContext(), CourseActivity::class.java)
                    intent.putExtra("id_course", board_id)
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