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
import it.uniparthenope.parthenopeddit.android.*
import it.uniparthenope.parthenopeddit.android.adapters.InfiniteScroller
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson

class PostActivitiesFragment(private val user_id: String) : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var auth : AuthManager

    private lateinit var recycler_view: RecyclerView
    private lateinit var postAdapter: PostAdapter
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
        val root = inflater.inflate(R.layout.fragment_post_activities, container, false)
        val no_posts_textview = root.findViewById<TextView>(R.id.no_posts_textview)

        auth = (activity as BasicActivity).app.auth

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter

        layoutManager = LinearLayoutManager(requireContext())
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(pageToLoad: Int, pageSize: Int) {
                UserRequests(requireContext(), auth).getUserPublishedPosts(
                    page = pageToLoad,
                    perPage = pageSize,
                    user_id = user_id,
                    transactionStartDateTime = transactionStartDateTime,
                    onSuccess = {
                        postAdapter.aggiungiPost(it)
                    },
                    onEndOfContent = {
                        infiniteScroller.theresMore = false
                    },
                    onFail = {
                        Toast.makeText(requireContext(), "ERROR IN FETCHING NEW POSTS", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        infiniteScroller = InfiniteScroller(
            layoutManager, updater, per_page
        )

        UserRequests(requireContext(), auth).getUserPublishedPosts(
            page = 1,
            perPage = per_page,
            user_id = user_id,
            onSuccess = {
                if(it.isNotEmpty()) {
                    postAdapter.aggiungiPost(it)
                    transactionStartDateTime = it[0].timestamp
                    recycler_view.addOnScrollListener(infiniteScroller)

                    no_posts_textview.visibility = View.GONE
                    recycler_view.visibility = View.VISIBLE
                }
            },
            onEndOfContent = {
                //nothing. list is empty.
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

    override fun onClickLike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).likePost(
            id_post,
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

    override fun onClickDislike(id_post: Int, upvote_textview: TextView, downvote_textview: TextView) {
        PostsRequests(requireContext(), auth).dislikePost(
            id_post,
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
