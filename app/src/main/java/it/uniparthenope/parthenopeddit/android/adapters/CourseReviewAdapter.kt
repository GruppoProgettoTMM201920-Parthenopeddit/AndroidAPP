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
        holder.title_textview_r.text = currentItem.title
        holder.timestamp_textview.text = currentItem.timestamp
        holder.posttext_textview.text = currentItem.body
        holder.upvote_textview.text = "0"
        holder.downvote_textview.text = "0"
        setEnjoymentStars(holder, currentItem.score_liking)
        setDifficultyStars(holder, currentItem.score_difficulty)

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

    fun setEnjoymentStars(holder: CourseReviewViewHolder, score_liking: Int){
        if(score_liking.toInt()==1){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
            }
        else if(score_liking >1 && score_liking <2){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >2 && score_liking <3){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >3 && score_liking <4){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >4 && score_liking <5){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_liking == 5){
            holder.star_1r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4r.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5r.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }
    fun setDifficultyStars(holder: CourseReviewViewHolder, score_difficulty: Int){
        if(score_difficulty.toInt()==1){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >1 && score_difficulty <2){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 2){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >2 && score_difficulty <3){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 3){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >3 && score_difficulty <4){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 4){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >4 && score_difficulty <5){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_difficulty == 5){
            holder.star_1d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_2d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_3d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_4d.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_5d.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }

    class CourseReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val imageView: ImageView = itemView.image_view
        val username_textview: TextView = itemView.username_textview
        val title_textview_r: TextView = itemView.title_textview_r
        val timestamp_textview: TextView = itemView.timestamp_textview
        val posttext_textview: TextView = itemView.posttext_textview
        val upvote_btn: ImageButton = itemView.upvote_btn
        val downvote_btn: ImageButton = itemView.downvote_btn
        val upvote_textview: TextView = itemView.upvote_textview
        val downvote_textview: TextView = itemView.downvote_textview
        val star_1r: ImageView = itemView.star_1r
        val star_2r: ImageView = itemView.star_2r
        val star_3r: ImageView = itemView.star_3r
        val star_4r: ImageView = itemView.star_4r
        val star_5r: ImageView = itemView.star_5r
        val star_1d: ImageView = itemView.star_1d
        val star_2d: ImageView = itemView.star_2d
        val star_3d: ImageView = itemView.star_3d
        val star_4d: ImageView = itemView.star_4d
        val star_5d: ImageView = itemView.star_5d
    }
}