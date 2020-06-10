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
import kotlinx.android.synthetic.main.cardview_post.view.comments_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.image_view
import kotlinx.android.synthetic.main.cardview_post.view.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.view.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.username_textview

class CommentAdapter(private val context: Context) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var listener:CommentItemClickListeners? = null
    private var commentItemsList: ArrayList<Comment> = arrayListOf()

    constructor(context: Context, listener:CommentItemClickListeners?) : this(context) {
        this.listener = listener
    }

    constructor(context: Context, commentItemsList: ArrayList<Comment>, listener:CommentItemClickListeners?) : this(context, listener) {
        this.commentItemsList = commentItemsList
    }

    interface CommentItemClickListeners {
        fun onClickLike(id_Commento:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickDislike(id_Commento:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickComments(id_Commento:Int, comment: Comment)
    }

    fun setItemClickListener( listener:CommentItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiCommenti(commentsList: List<Comment>) {
        this.commentItemsList.addAll(commentsList)
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

        holder.imageView.setImageResource(R.drawable.default_user_image)
        holder.username_textview.text = currentItem.author?.display_name?:currentItem.author_id
        holder.timestamp_comment_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = currentItem.likes_num.toString()
        holder.downvote_textview.text = currentItem.dislikes_num.toString()
        holder.comments_textview.text = currentItem.comments_num.toString()

        holder.upvote_btn.setOnClickListener {
            listener?.onClickLike( currentItem.id, holder.upvote_textview, holder.downvote_textview )
        }

        holder.downvote_btn.setOnClickListener {
            listener?.onClickDislike( currentItem.id, holder.upvote_textview, holder.downvote_textview )
        }

        holder.comment_btn.setOnClickListener {
            listener?.onClickComments( currentItem.id, currentItem )
        }

        holder.comment_relativelayout.setOnLongClickListener {
            if(!holder.collapsed) {
                holder.posttext_textview.visibility = View.GONE
                holder.comments_textview.visibility = View.GONE
                holder.comment_btn.visibility = View.GONE
                holder.upvote_textview.visibility = View.GONE
                holder.downvote_textview.visibility = View.GONE
                holder.upvote_btn.visibility = View.GONE
                holder.downvote_btn.visibility = View.GONE

                holder.commentsLayoutContainer.visibility = View.GONE

                holder.collapsed = true
            } else {
                holder.posttext_textview.visibility = View.VISIBLE
                holder.comments_textview.visibility = View.VISIBLE
                holder.comment_btn.visibility = View.VISIBLE
                holder.upvote_textview.visibility = View.VISIBLE
                holder.downvote_textview.visibility = View.VISIBLE
                holder.upvote_btn.visibility = View.VISIBLE
                holder.downvote_btn.visibility = View.VISIBLE

                if(holder.comments_visible)
                    holder.commentsLayoutContainer.visibility = View.VISIBLE
                else
                    holder.commentsLayoutContainer.visibility = View.GONE

                holder.collapsed = false
            }
            return@setOnLongClickListener true
        }

        Log.d("DEBUG","settando la lista di commenti del commento ${currentItem.id}")
        Log.d("DEBUG","comments num : ${currentItem.comments_num?:0}")

        if( currentItem.comments?.size?:0 > 0 ) {
            Log.d("DEBUG","Creo l'adapter")
            val commentAdapter = CommentAdapter(context, currentItem.comments!!, listener)


            Log.d("DEBUG","Imposto la recyclerview")
            holder.commentsListContainer.adapter = commentAdapter
            holder.commentsListContainer.layoutManager = LinearLayoutManager(context)
            holder.commentsListContainer.setHasFixedSize(true)

            holder.commentsLayoutContainer.visibility = View.VISIBLE
            holder.comments_visible = true
        } else {

            Log.d("DEBUG","no comments to show")
            holder.commentsLayoutContainer.visibility = View.GONE
            holder.comments_visible = false
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
        val comment_relativelayout: RelativeLayout = itemView.comment_relativelayout
        val comments_textview: TextView = itemView.comment_comments_textview

        var collapsed: Boolean = false
        var comments_visible: Boolean = false

        val commentsLayoutContainer: LinearLayout = itemView.commentsLayoutContainer
        val commentsListContainer: RecyclerView = itemView.commentsListContainer
    }
}