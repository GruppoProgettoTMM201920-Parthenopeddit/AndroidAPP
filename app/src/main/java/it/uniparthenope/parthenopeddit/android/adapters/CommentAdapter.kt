package it.uniparthenope.parthenopeddit.android.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Comment
import kotlinx.android.synthetic.main.cardview_commento.view.*
import kotlinx.android.synthetic.main.cardview_post.view.*
import kotlinx.android.synthetic.main.cardview_post.view.comments_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.image_view
import kotlinx.android.synthetic.main.cardview_post.view.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.view.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.username_textview

class CommentAdapter(private val context: Context, private var commentItemsList: ArrayList<Comment>, private var listener:CommentItemClickListeners?) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    interface CommentItemClickListeners {
        fun onClickLike(id_Commento:Int)
        fun onClickDislike(id_Commento:Int)
        fun onClickComments(id_Commento:Int)
    }

    fun setItemClickListener( listener:CommentItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiCommento(postItemList: List<Comment>) {
        this.commentItemsList.addAll(postItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_commento,
            parent, false)

        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentItemsList[position]

        holder.imageView.setImageResource(R.drawable.ic_home_black_24dp)
        holder.username_textview.text = currentItem.author?.nome_visualizzato
        holder.timestamp_comment_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = "0"
        holder.downvote_textview.text = "0"

        holder.upvote_btn.setOnClickListener {
            listener?.onClickLike( currentItem.id )
            holder.upvote_textview.text = (holder.upvote_textview.text.toString().toInt() + 1).toString()
        }

        holder.downvote_btn.setOnClickListener {
            listener?.onClickLike( currentItem.id )
            holder.downvote_textview.text = (holder.downvote_textview.text.toString().toInt() + 1).toString()
        }

        holder.comment_btn.setOnClickListener {
            listener?.onClickComments( currentItem.id )
        }

        Log.d("DEBUG","settando la lista di commenti del commento ${currentItem.id}")
        Log.d("DEBUG","comments num : ${currentItem.comments_num?:0}")

        if( currentItem.comments_num?:0 > 0 ) {
            Log.d("DEBUG","Creo l'adapter")
            val commentAdapter = CommentAdapter(context, currentItem.comments!!, listener)


            Log.d("DEBUG","Imposto la recyclerview")
            holder.commentsListContainer.adapter = commentAdapter
            holder.commentsListContainer.layoutManager = LinearLayoutManager(context)
            holder.commentsListContainer.setHasFixedSize(true)

            holder.commentsLayoutContainer.visibility = View.VISIBLE
        } else {

            Log.d("DEBUG","no comments to show")
            holder.commentsLayoutContainer.visibility = View.GONE
        }
    }

    override fun getItemCount() = commentItemsList.size

    fun aggiornaLista(commentItemList: ArrayList<Comment>) {
        commentItemsList = commentItemList
        notifyDataSetChanged()
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val imageView: ImageView = itemView.image_view
        val username_textview: TextView = itemView.username_textview
        val timestamp_comment_textview: TextView = itemView.timestamp_comment_textview
        val posttext_textview: TextView = itemView.posttext_textview
        val upvote_btn: ImageButton = itemView.upvote_btn
        val downvote_btn: ImageButton = itemView.downvote_btn
        val upvote_textview: TextView = itemView.upvote_textview
        val downvote_textview: TextView = itemView.downvote_textview
        val comment_btn: ImageButton = itemView.comments_btn

        val commentsLayoutContainer: LinearLayout = itemView.commentsLayoutContainer
        val commentsListContainer: RecyclerView = itemView.commentsListContainer
    }
}