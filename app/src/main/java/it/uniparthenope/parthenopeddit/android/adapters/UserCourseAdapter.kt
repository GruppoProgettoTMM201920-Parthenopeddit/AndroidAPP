package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.user_boards.course.CourseUserBoardFragment
import it.uniparthenope.parthenopeddit.model.Course
import kotlinx.android.synthetic.main.cardview_course.view.*

class UserCourseAdapter : RecyclerView.Adapter<UserCourseAdapter.UserCourseViewHolder>() {

    private val courseList: ArrayList<Course> = ArrayList()
    private var listener:UserCourseItemClickListeners? = null

    fun setItemClickListener(listener: UserCourseItemClickListeners) {
        this.listener = listener
    }

    interface UserCourseItemClickListeners {
        fun onBoardClick(board_id: Int?)
    }

    fun addCourse(courseItemList: List<Course>) {
        this.courseList.addAll(courseItemList)
        notifyDataSetChanged()
    }

    fun setCourseList(courseItemList: List<Course>) {
        this.courseList.clear()
        this.courseList.addAll(courseItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_course,
            parent, false)

        return UserCourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserCourseViewHolder, position: Int) {
        val currentItem = courseList[position]

        holder.course_name_textview.text = currentItem.name
        holder.course_enjoyment_rating.text = currentItem.average_liking_score.toString()+"/5"
        holder.course_difficulty_rating.text = currentItem.average_difficulty_score.toString()+"/5"


        //SET LIKING SCORE
        if (currentItem.average_liking_score == 1.0) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score!! > 1 && currentItem.average_liking_score!! < 2) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score == 2.0) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score!! > 2 && currentItem.average_liking_score!! < 3) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score == 3.0) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score!! > 3 && currentItem.average_liking_score!! < 4) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_half_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score == 4.0) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        } else if (currentItem.average_liking_score!! > 4 && currentItem.average_liking_score!! < 5) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_half_24dp)
        } else if (currentItem.average_liking_score == 5.0) {
            holder.star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            holder.star_enj_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

        //SET DIFFICULTY SCORE
        if (currentItem.average_difficulty_score == 1.0) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score!! > 1 && currentItem.average_difficulty_score!! < 2) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_half_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score == 2.0) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score!! > 2 && currentItem.average_difficulty_score!! < 3) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_half_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score == 3.0) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score!! > 3 && currentItem.average_difficulty_score!! < 4) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_half_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score == 4.0) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
            } else if (currentItem.average_difficulty_score!! > 4 && currentItem.average_difficulty_score!! < 5) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_half_24dp)
            } else if (currentItem.average_difficulty_score == 5.0) {
                holder.star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
                holder.star_dif_5.setImageResource(R.drawable.ic_star_full_24dp)
            }

        
        
        if( listener != null ) {

            holder.relativeLayout.setOnClickListener {
                listener!!.onBoardClick(currentItem.id)
            }
        }
    }

    override fun getItemCount() = courseList.size

    class UserCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val course_name_textview: TextView = itemView.course_name_textview
        val relativeLayout: RelativeLayout = itemView.course_cardview_relativelayout

        var course_enjoyment_rating: TextView = itemView.course_enjoyment_rating
        var course_difficulty_rating: TextView = itemView.course_difficulty_rating
        var star_enj_1: ImageView = itemView.star_enj_1
        var star_enj_2: ImageView = itemView.star_enj_2
        var star_enj_3: ImageView = itemView.star_enj_3
        var star_enj_4: ImageView = itemView.star_enj_4
        var star_enj_5: ImageView = itemView.star_enj_5

        var star_dif_1: ImageView = itemView.star_dif_1
        var star_dif_2: ImageView = itemView.star_dif_2
        var star_dif_3: ImageView = itemView.star_dif_3
        var star_dif_4: ImageView = itemView.star_dif_4
        var star_dif_5: ImageView = itemView.star_dif_5


    }
}