package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.activity_course.view.*
import kotlinx.android.synthetic.main.cardview_post.view.*
import kotlinx.android.synthetic.main.cardview_post.view.downvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.downvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.image_view
import kotlinx.android.synthetic.main.cardview_post.view.posttext_textview
import kotlinx.android.synthetic.main.cardview_post.view.timestamp_textview
import kotlinx.android.synthetic.main.cardview_post.view.upvote_btn
import kotlinx.android.synthetic.main.cardview_post.view.upvote_textview
import kotlinx.android.synthetic.main.cardview_post.view.username_textview
import kotlinx.android.synthetic.main.cardview_review.view.*

class ReviewAdapter() : RecyclerView.Adapter<ReviewAdapter.CourseReviewViewHolder>() {

    private val reviewList: ArrayList<Review> = ArrayList()
    private var listener:CourseReviewItemClickListeners? = null

    fun setItemClickListener( listener:CourseReviewItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiPost(reviewItemList: List<Review>) {
        this.reviewList.addAll(reviewItemList)
        notifyDataSetChanged()
    }

    interface CourseReviewItemClickListeners {
        fun onClickLike(id_review:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickDislike(id_review:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onReviewClick(id_course: Int)
        fun onClickComments(id_review: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_review,
            parent, false)

        return CourseReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseReviewViewHolder, position: Int) {
        val currentItem = reviewList[position]

        holder.imageView.setImageResource(R.drawable.ic_home_black_24dp)
        holder.username_textview.text = currentItem.author?.display_name?:currentItem.author_id
        holder.timestamp_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = currentItem.likes_num.toString()
        holder.downvote_textview.text = currentItem.dislikes_num.toString()
        holder.comments_textview.text = currentItem.comments_num.toString()

        holder.liking_rating_bar.rating = currentItem.score_liking.toFloat()
        holder.difficulty_rating_bar.rating = currentItem.score_difficulty.toFloat()

        if( listener != null ) {
            holder.upvote_btn.setOnClickListener {
                listener!!.onClickLike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
            }

            holder.downvote_btn.setOnClickListener {
                listener!!.onClickLike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
            }

            holder.review_relativelayout.setOnClickListener {
                listener!!.onReviewClick( currentItem.reviewed_course_id )
            }

            holder.comments_btn.setOnClickListener {
                listener!!.onClickComments( currentItem.id )
            }
        }
    }

    override fun getItemCount() = reviewList.size

    fun aggiungiReview(reviewList: List<Review>) {
        this.reviewList.addAll(reviewList)
        notifyDataSetChanged()
    }

    class CourseReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val imageView: ImageView = itemView.image_view
        val username_textview: TextView = itemView.username_textview
        val timestamp_textview: TextView = itemView.timestamp_textview
        val posttext_textview: TextView = itemView.posttext_textview
        val upvote_btn: ImageButton = itemView.upvote_btn
        val downvote_btn: ImageButton = itemView.downvote_btn
        val comments_btn: ImageButton = itemView.comments_btn2
        val upvote_textview: TextView = itemView.upvote_textview
        val downvote_textview: TextView = itemView.downvote_textview
        val comments_textview: TextView = itemView.comments_textview2
        val review_relativelayout: RelativeLayout = itemView.review_relativelayout
        val liking_rating_bar: RatingBar = itemView.liking_rating_bar
        val difficulty_rating_bar: RatingBar = itemView.difficulty_rating_bar
    }
}