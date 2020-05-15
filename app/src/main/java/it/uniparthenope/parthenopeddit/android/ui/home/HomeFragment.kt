package it.uniparthenope.parthenopeddit.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CommentActivity
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.cardview_post.*

class HomeFragment : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var recycler_view: RecyclerView
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter(ArrayList<Post>(), this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        MockApiData().getAllPost( Auth().token ) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiornaLista( postItemList )
            }
        }

        return root
    }

    override fun onClickLike(id_post: Int) {
        Toast.makeText(requireContext(),"you liked post id[$id_post]", Toast.LENGTH_LONG).show()
        //TODO Send upvote through API
    }

    override fun onClickDislike(id_post: Int) {
        downvote_textview.text = (downvote_textview.text.toString().toInt() + 1).toString()
        //TODO: Send downvote through API
    }

    override fun onClickComments(id_post: Int) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }
}
