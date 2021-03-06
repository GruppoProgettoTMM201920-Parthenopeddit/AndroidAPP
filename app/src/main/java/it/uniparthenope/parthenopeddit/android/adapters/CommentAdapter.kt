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
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.DateParser
import kotlinx.android.synthetic.main.cardview_commento.view.*
import kotlinx.android.synthetic.main.cardview_post.view.comments_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.image_view
import kotlinx.android.synthetic.main.cardview_post.view.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.view.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.username_textview

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val commentList: ArrayList<Comment> = ArrayList()
    private var listener: CommentItemClickListeners? = null

    fun setItemClickListener( listener:CommentItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiCommenti(commentItemList: List<Comment>) {
        val initialSize = commentList.size
        this.commentList.addAll(commentItemList)
        val updatedSize = commentList.size
        notifyItemRangeInserted(initialSize, updatedSize)
    }

    fun setCommentList(commentItemList: List<Comment>) {
        this.commentList.clear()
        this.commentList.addAll(commentItemList)
        notifyDataSetChanged()
    }

    interface CommentItemClickListeners {
        fun onClickLike(id_Commento:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickDislike(id_Commento:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickComments(id_Commento:Int, comment: Comment)
        fun onUserClick(id_user: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_commento,
            parent, false)

        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentList[position]

        holder.imageView.setImageResource(R.drawable.default_user_image)
        holder.username_textview.text = currentItem.author?.display_name?:currentItem.author_id
        holder.timestamp_comment_textview.text = DateParser.prettyParse(currentItem.timestamp)
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

        holder.username_textview.setOnClickListener {
            listener?.onUserClick(currentItem.author_id)
        }

        holder.commentsLayoutContainer.visibility = View.GONE
        holder.comments_visible = false
    }

    override fun getItemCount() = commentList.size

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