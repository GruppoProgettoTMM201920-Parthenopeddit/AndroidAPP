package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Course
import it.uniparthenope.parthenopeddit.model.Review
import it.uniparthenope.parthenopeddit.util.DateParser
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
    private var listener: ReviewItemClickListeners? = null

    fun setItemClickListener( listener:ReviewItemClickListeners? ) {
        this.listener = listener
    }

    fun aggiungiReview(reviewItemList: List<Review>) {
        val initialSize = reviewList.size
        this.reviewList.addAll(reviewItemList)
        val updatedSize = reviewList.size
        notifyItemRangeInserted(initialSize, updatedSize)
    }

    fun setCommentList(reviewItemList: List<Review>) {
        this.reviewList.clear()
        this.reviewList.addAll(reviewItemList)
        notifyDataSetChanged()
    }

    interface ReviewItemClickListeners {
        fun onClickLike(id_review:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickDislike(id_review:Int, upvote_textview: TextView, downvote_textview: TextView)
        fun onClickCourse(id_course: Int, course: Course)
        fun onReviewClick(id_review: Int, review:Review)
        fun onClickComments(id_review: Int, review:Review)
        fun onUserClick(id_user: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_review,
            parent, false)

        return CourseReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseReviewViewHolder, position: Int) {
        val currentItem = reviewList[position]

        holder.imageView.setImageResource(R.drawable.default_user_image)
        holder.username_textview.text = currentItem.author?.display_name?:currentItem.author_id
        holder.timestamp_textview.text = DateParser.prettyParse(currentItem.timestamp)
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = currentItem.likes_num.toString()
        holder.downvote_textview.text = currentItem.dislikes_num.toString()
        holder.comments_textview.text = currentItem.comments_num.toString()

        holder.liking_rating_bar.rating = currentItem.score_liking.toFloat()
        holder.difficulty_rating_bar.rating = currentItem.score_difficulty.toFloat()

        holder.group_textview2.text = currentItem.reviewed_course?.name?:""

        holder.upvote_btn.setOnClickListener {
            listener?.onClickLike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
        }

        holder.downvote_btn.setOnClickListener {
            listener?.onClickLike(currentItem.id, holder.upvote_textview, holder.downvote_textview)
        }

        holder.review_relativelayout.setOnClickListener {
            listener?.onReviewClick( currentItem.id, currentItem )
        }

        holder.comments_btn.setOnClickListener {
            listener?.onClickComments( currentItem.id, currentItem )
        }

        holder.group_textview2.setOnClickListener {
            listener?.onClickCourse( currentItem.reviewed_course_id, currentItem.reviewed_course!! )
        }

        holder.username_textview.setOnClickListener {
            listener?.onUserClick(currentItem.author_id)
        }
    }

    override fun getItemCount() = reviewList.size

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
        val group_textview2 : TextView = itemView.group_textview2
    }
}