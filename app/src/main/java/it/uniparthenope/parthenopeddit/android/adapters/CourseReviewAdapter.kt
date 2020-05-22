package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.activity_course.view.*
import kotlinx.android.synthetic.main.activity_course.view.star_1
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

class CourseReviewAdapter() : RecyclerView.Adapter<CourseReviewAdapter.CourseReviewViewHolder>() {

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
        fun onClickLike(id_post:Int)
        fun onClickDislike(id_post:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_review,
            parent, false)

        return CourseReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseReviewViewHolder, position: Int) {
        val currentItem = reviewList[position]

        holder.imageView.setImageResource(R.drawable.ic_home_black_24dp)
        holder.username_textview.text = currentItem.author_id
        holder.timestamp_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = "0"
        holder.downvote_textview.text = "0"
        setStars(holder, currentItem.score_liking)

        if( listener != null ) {
            holder.upvote_btn.setOnClickListener {
                listener!!.onClickLike(currentItem.id)
                holder.upvote_textview.text =
                    (holder.upvote_textview.text.toString().toInt() + 1).toString()
            }

            holder.downvote_btn.setOnClickListener {
                listener!!.onClickLike(currentItem.id)
                holder.downvote_textview.text =
                    (holder.downvote_textview.text.toString().toInt() + 1).toString()
            }
        }
    }

    override fun getItemCount() = reviewList.size

    fun aggiungiReview(reviewList: List<Review>) {
        this.reviewList.addAll(reviewList)
        notifyDataSetChanged()
    }

    fun setStars(holder: CourseReviewViewHolder, score_liking: Int){
        if(score_liking.toInt()==1){
                holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_2.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_3.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
            }
        else if(score_liking >1 && score_liking <2){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >2 && score_liking <3){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >3 && score_liking <4){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >4 && score_liking <5){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_liking == 5){
            holder.star_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }

    class CourseReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val imageView: ImageView = itemView.image_view
        val username_textview: TextView = itemView.username_textview
        val timestamp_textview: TextView = itemView.timestamp_textview
        val posttext_textview: TextView = itemView.posttext_textview
        val upvote_btn: ImageButton = itemView.upvote_btn
        val downvote_btn: ImageButton = itemView.downvote_btn
        val upvote_textview: TextView = itemView.upvote_textview
        val downvote_textview: TextView = itemView.downvote_textview
        val star_1: ImageView = itemView.star_1r
        val star_2: ImageView = itemView.star_2r
        val star_3: ImageView = itemView.star_3r
        val star_4: ImageView = itemView.star_4r
        val star_5: ImageView = itemView.star_5r
    }
}