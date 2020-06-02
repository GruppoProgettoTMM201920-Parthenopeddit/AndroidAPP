package it.uniparthenope.parthenopeddit.android.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.android.view.CardviewPost
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview

class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val postList: ArrayList<Post> = ArrayList()
    private var listener: CardviewPost.PostItemClickListeners? = null

    fun setItemClickListener( listener: CardviewPost.PostItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiPost(postItemList: List<Post>) {
        this.postList.addAll(postItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val newView = CardviewPost(parent.context)
        return PostViewHolder(newView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = postList[position]
        val cardView = holder.itemView as CardviewPost
        cardView.setPost( currentItem )
        cardView.setListeners( listener )

    }

    override fun getItemCount() = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}     //SINGOLO ELEMENTO DELLA LISTA
}