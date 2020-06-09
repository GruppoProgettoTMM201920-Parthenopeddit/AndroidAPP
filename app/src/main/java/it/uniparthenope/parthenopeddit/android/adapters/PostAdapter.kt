package it.uniparthenope.parthenopeddit.android.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.cardview_post.view.*
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview

class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val postList: ArrayList<Post> = ArrayList()
    private var listener:PostItemClickListeners? = null

    fun setItemClickListener( listener:PostItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiPost(postItemList: List<Post>) {
        this.postList.addAll(postItemList)
        notifyDataSetChanged()
    }

    interface PostItemClickListeners {
        fun onClickLike(id_post:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickDislike(id_post:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickComments(id_post:Int, post:Post)
        fun onBoardClick(board_id: Int?, board: Board?)
        fun onPostClick(id_post: Int, post:Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_post,
            parent, false)

        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = postList[position]

        holder.imageView.setImageResource(R.drawable.default_user_image)
        holder.username_textview.text = currentItem.author?.display_name?:currentItem.author_id
        holder.title_textview.text = currentItem.title
        holder.board_textview.text = currentItem.posted_to_board?.name?:"Generale"
        holder.timestamp_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = currentItem.likes_num.toString()
        holder.downvote_textview.text = currentItem.dislikes_num.toString()
        holder.comments_textview.text = currentItem.comments_num.toString()

        if( currentItem.posted_to_board_id == 0 || currentItem.posted_to_board_id == null ) {
            holder.board_textview.setBackgroundResource(R.drawable.general_textview_bubble)
            holder.board_textview.setTextColor(Color.BLACK)
        } else {
            when (currentItem.posted_to_board!!.type) {
                "course" ->  {
                    holder.board_textview.setBackgroundResource(R.drawable.fab_textview_bubble)
                    holder.board_textview.setTextColor(Color.WHITE)
                }
                "group" ->  {
                    holder.board_textview.setBackgroundResource(R.drawable.group_textview_bubble)
                    holder.board_textview.setTextColor(Color.WHITE)
                }
                else -> holder.board_textview.visibility = View.GONE
            }
        }

        if( listener != null ) {
            holder.upvote_btn.setOnClickListener {
                listener!!.onClickLike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
            }

            holder.downvote_btn.setOnClickListener {
                listener!!.onClickDislike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
            }

            holder.comment_btn.setOnClickListener {
                listener!!.onClickComments(currentItem.id, currentItem)
            }

            holder.board_textview.setOnClickListener {
                listener!!.onBoardClick(currentItem.posted_to_board_id, currentItem.posted_to_board)
            }

            holder.relativeLayout.setOnClickListener {
                listener!!.onPostClick(currentItem.id, currentItem)
            }
        }
    }

    override fun getItemCount() = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val imageView: ImageView = itemView.image_view
        val username_textview: TextView = itemView.username_textview
        val title_textview: TextView = itemView.title_textview
        val board_textview: TextView = itemView.group_textview
        val timestamp_textview: TextView = itemView.timestamp_textview
        val posttext_textview: TextView = itemView.posttext_textview
        val upvote_btn: ImageButton = itemView.upvote_btn
        val downvote_btn: ImageButton = itemView.downvote_btn
        val upvote_textview: TextView = itemView.upvote_textview
        val downvote_textview: TextView = itemView.downvote_textview
        val comment_btn: ImageButton = itemView.comments_btn
        val relativeLayout: RelativeLayout = itemView.post_relativelayout
        val comments_textview: TextView = itemView.comments_textview
    }
}