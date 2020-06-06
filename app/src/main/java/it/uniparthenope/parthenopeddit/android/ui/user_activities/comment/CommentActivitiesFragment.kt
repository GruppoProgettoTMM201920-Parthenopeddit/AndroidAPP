package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CommentActivity
//import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_activities.comment.CommentActivitiesViewModel
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.api.requests.CommentsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Comment
import kotlinx.android.synthetic.main.fragment_messages.*

class CommentActivitiesFragment : Fragment(), CommentAdapter.CommentItemClickListeners {

    private lateinit var authManager: AuthManager
    private lateinit var recycler_view: RecyclerView
    private lateinit var commentViewModel: CommentActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        commentViewModel =
            ViewModelProviders.of(this).get(CommentActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_comment_activities, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        var commentItemsList: ArrayList<Comment> = arrayListOf<Comment>()
        val commentAdapter = CommentAdapter(requireContext(), commentItemsList, this)
        commentAdapter.setItemClickListener(this)
        recycler_view.adapter = commentAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        authManager = (activity as BasicActivity).app.auth

        //TODO: through API

        MockApiData().getUserComment(authManager.token!!, "user1") { commentsItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                commentsItemList!!

                commentAdapter.aggiungiCommento( commentsItemList )
            }
        }

        return root
    }

    override fun onClickLike(id_Commento: Int) {
        //TODO("Not yet implemented")
    }

    override fun onClickDislike(id_Commento: Int) {
        //TODO("Not yet implemented")
    }

    override fun onClickComments(id_Commento: Int) {
        //TODO("Not yet implemented")
    }

    override fun onCommentClick(id_post: Int) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }
}
