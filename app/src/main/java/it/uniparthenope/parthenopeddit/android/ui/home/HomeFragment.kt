package it.uniparthenope.parthenopeddit.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.*
import it.uniparthenope.parthenopeddit.android.adapters.InfiniteScroller
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.newGroup.NewGroupActivity
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toGson


class HomeFragment : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var auth : AuthManager

    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var infiniteScroller: InfiniteScroller
    private lateinit var updater: InfiniteScroller.Updater

    private val per_page = 20

    var isOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val fab = root.findViewById(R.id.fab) as FloatingActionButton
        val fab_new_post = root.findViewById(R.id.fab_new_post) as FloatingActionButton
        val fab_new_group = root.findViewById(R.id.fab_new_group) as FloatingActionButton
        val fab_new_post_textview = root.findViewById(R.id.fab_new_post_textview) as TextView
        val fab_new_group_textview = root.findViewById(R.id.fab_new_group_textview) as TextView

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        adapter = PostAdapter()
        adapter.setItemClickListener(this)
        recycler_view.adapter = adapter

        layoutManager = LinearLayoutManager(requireContext())
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(totalItemCount: Int) {
                if( totalItemCount != 0 && (totalItemCount%per_page) != 0 ) {
                    infiniteScroller.theresMore = false
                    return
                }

                infiniteScroller.currentPage += 1

                UserRequests(requireContext(), auth).getUserFeed(
                    page = infiniteScroller.currentPage,
                    perPage = per_page,
                    onSuccess = {
                        adapter.aggiungiPost(it)
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
            layoutManager, updater
        )

        auth = (activity as BasicActivity).app.auth

        UserRequests(requireContext(), auth).getUserFeed(
            page = 1,
            perPage = per_page,
            onSuccess = {
                adapter.aggiungiPost( it )
                recycler_view.addOnScrollListener(infiniteScroller)
            },
            onEndOfContent = {
                //nothing. list is empty.
            },
            onFail = { it: String ->
                Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
            }
        )

        val rotateClockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anticlockwise)

        val searchBar = root.findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setSpeechMode(false)
        searchBar.setCardViewElevation(10)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                if(text.isNotBlank()){
                    val intent = Intent(requireContext(), SearchActivity::class.java)
                    intent.putExtra("query", text.toString())
                    startActivity(intent)
                }
            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_NAVIGATION -> {
                    }
                    MaterialSearchBar.BUTTON_SPEECH -> {
                    }
                    MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
                }
            }
        })


        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)
                fab_new_post.animate().translationY(200F)
                fab_new_group.animate().translationY(400F)
                fab_new_post_textview.animate().translationY(200F)
                fab_new_group_textview.animate().translationY(400F)
                fab_new_post_textview.animate().alpha(0F)
                fab_new_group_textview.animate().alpha(0F)
                fab_new_post_textview.visibility = View.GONE
                fab_new_group_textview.visibility = View.GONE
                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)
                fab_new_post.animate().translationY(-200F)
                fab_new_group.animate().translationY(-400F)
                fab_new_post_textview.animate().translationY(-200F)
                fab_new_group_textview.animate().translationY(-400F)
                fab_new_post_textview.visibility = View.VISIBLE
                fab_new_group_textview.visibility = View.VISIBLE
                fab_new_post_textview.animate().alpha(1F)
                fab_new_group_textview.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post.setOnClickListener{ onClickNewPost() }
        fab_new_post_textview.setOnClickListener{ onClickNewPost() }
        fab_new_group.setOnClickListener{ onClickNewGroup() }
        fab_new_group_textview.setOnClickListener{ onClickNewGroup() }

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

    fun onClickNewPost(){
        val intent = Intent(requireContext(), NewPostActivity::class.java)
        intent.putExtra("id_group",0)
        intent.putExtra("name_group","Generale")
        startActivity(intent)
    }

    fun onClickNewGroup(){
        val intent = Intent(requireContext(), NewGroupActivity::class.java)
        startActivity(intent)
    }
}
