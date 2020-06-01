package it.uniparthenope.parthenopeddit.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.App
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CommentActivity
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.GroupActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.newGroup.NewGroupActivity
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.android.ui.newReview.NewReviewActivity
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post


class HomeFragment : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var recycler_view: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    var isOpen = false

    lateinit var auth: AuthManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val fab = root.findViewById(R.id.fab) as FloatingActionButton
        val fab_new_post = root.findViewById(R.id.fab_new_post) as FloatingActionButton
        val fab_new_group = root.findViewById(R.id.fab_new_group) as FloatingActionButton
        val fab_new_review = root.findViewById(R.id.fab_new_review) as FloatingActionButton
        val fab_new_post_textview = root.findViewById(R.id.fab_new_post_textview) as TextView
        val fab_new_group_textview = root.findViewById(R.id.fab_new_group_textview) as TextView
        val fab_new_review_textview = root.findViewById(R.id.fab_new_review_textview) as TextView

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        auth = (requireContext().applicationContext as App).auth

        UserRequests(requireContext(), auth).getUserFeed(
            1,
            20,
            {   listaPost: ArrayList<Post> ->
                postAdapter.aggiungiPost( listaPost )
                postAdapter.notifyDataSetChanged()
            }, {
                Toast.makeText(requireContext(), "errore : $it", Toast.LENGTH_LONG).show()
            }
        )

        val rotateClockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anticlockwise)


        //TODO Ricerca
        //MATERIALSEARCHBAR
        val searchListView = root.findViewById(R.id.search_listview) as ListView
        val searchBar = root.findViewById(R.id.searchBar) as MaterialSearchBar

        searchBar.setCardViewElevation(10)
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
            }
        })
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
            }
            override fun onSearchConfirmed(text: CharSequence) {
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
                fab_new_review.animate().translationY(600F)
                fab_new_post_textview.animate().translationY(200F)
                fab_new_group_textview.animate().translationY(400F)
                fab_new_review_textview.animate().translationY(600F)
                fab_new_post_textview.animate().alpha(0F)
                fab_new_group_textview.animate().alpha(0F)
                fab_new_review_textview.animate().alpha(0F)
                fab_new_post_textview.visibility = View.GONE
                fab_new_group_textview.visibility = View.GONE
                fab_new_review_textview.visibility = View.GONE



                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_post.animate().translationY(-200F)
                fab_new_group.animate().translationY(-400F)
                fab_new_review.animate().translationY(-600F)

                fab_new_post_textview.animate().translationY(-200F)
                fab_new_group_textview.animate().translationY(-400F)
                fab_new_review_textview.animate().translationY(-600F)

                fab_new_post_textview.visibility = View.VISIBLE
                fab_new_group_textview.visibility = View.VISIBLE
                fab_new_review_textview.visibility = View.VISIBLE

                fab_new_post_textview.animate().alpha(1F)
                fab_new_group_textview.animate().alpha(1F)
                fab_new_review_textview.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post.setOnClickListener{ onClickNewPost() }
        fab_new_post_textview.setOnClickListener{ onClickNewPost() }
        fab_new_group.setOnClickListener{ onClickNewGroup() }
        fab_new_group_textview.setOnClickListener{ onClickNewGroup() }
        fab_new_review.setOnClickListener{ onClickNewReview() }
        fab_new_review_textview.setOnClickListener{ onClickNewReview() }

        return root
    }

    override fun onClickLike(
        id_post: Int,
        upvoteTextView: TextView,
        downvoteTextView: TextView
    ) {
        PostsRequests(requireContext(), auth).likePost(
            id_post,
            {
                /*Like piazzato */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
                /*Like rimosso */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
                /* dislike rimosso e piazzato like */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
            }
        )
    }

    override fun onClickDislike(
        id_post: Int,
        upvoteTextView: TextView,
        downvoteTextView: TextView
    ) {
        PostsRequests(requireContext(), auth).dislikePost(
            id_post,
            {
                /*disLike piazzato */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
                /*disLike rimosso */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
                /* like rimosso e piazzato disLike */
                upvoteTextView.text = it.likes_num.toString()
                downvoteTextView.text = it.dislikes_num.toString()
            }, {
            }
        )
    }

    override fun onClickComments(id_post: Int) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }

    override fun onPostClick(id_post: Int) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }

    override fun onBoardClick(board_id: Int?, board: Board?) {
        if (board == null) {
            (activity as BasicActivity).goToActivity(HomeActivity::class.java) //HOME
        } else {
            when (board.type) {
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

    fun onClickNewPost(){
        //crea dialogo
            //passi fuonzione da effettuare onSuccess
        //uploiad to api
        //notifidatasetchanged()
        val intent = Intent(requireContext(), NewPostActivity::class.java)
        startActivity(intent)
    }

    fun onClickNewGroup(){
        val intent = Intent(requireContext(), NewGroupActivity::class.java)
        startActivity(intent)
    }

    fun onClickNewReview(){
        val intent = Intent(requireContext(), NewReviewActivity::class.java)
        startActivity(intent)
    }
}
