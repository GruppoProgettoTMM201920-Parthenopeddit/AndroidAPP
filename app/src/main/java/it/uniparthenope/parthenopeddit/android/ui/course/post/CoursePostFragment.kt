package it.uniparthenope.parthenopeddit.android.ui.course.post

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.PostCommentsActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
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

class CoursePostFragment(private var courseID: Int) : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var auth : AuthManager

    private lateinit var recycler_view: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var infiniteScroller: InfiniteScroller
    private lateinit var updater: InfiniteScroller.Updater

    private lateinit var transactionStartDateTime: String

    private val per_page = 20


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_course_post, container, false)

        auth = (activity as BasicActivity).app.auth

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter

        layoutManager = LinearLayoutManager(requireContext())
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(pageToLoad: Int, pageSize: Int) {
                CoursesRequests(requireContext(), auth).getCoursePosts(
                    page = pageToLoad,
                    perPage = pageSize,
                    course_id = courseID,
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

        CoursesRequests(requireContext(), auth).getCoursePosts(
            page = 1,
            perPage = per_page,
            course_id = courseID,
            onSuccess = {
                postAdapter.aggiungiPost(it)
                if(it.isNotEmpty()) transactionStartDateTime = it[0].timestamp
                recycler_view.addOnScrollListener(infiniteScroller)
            },
            onEndOfContent = {
                //nothing. list is empty.
            },
            onFail = {
                Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
            }
        )

        val itemsswipetorefresh = root.findViewById(R.id.itemsswipetorefresh) as SwipeRefreshLayout

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.white))
        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true

            CoursesRequests(requireContext(), auth).getCoursePosts(
                page = 1,
                perPage = per_page,
                course_id = courseID,
                onSuccess = {
                    if(it.isNotEmpty()) {
                        postAdapter.setPostList(it)
                        transactionStartDateTime = it[0].timestamp

                        infiniteScroller = InfiniteScroller(
                            layoutManager, updater, per_page
                        )

                        recycler_view.addOnScrollListener(infiniteScroller)
                    }

                    itemsswipetorefresh.isRefreshing = false
                },
                onEndOfContent = {
                    //nothing. list is empty.
                    itemsswipetorefresh.isRefreshing = false
                },
                onFail = {
                    Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                    itemsswipetorefresh.isRefreshing = false
                }
            )
        }

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